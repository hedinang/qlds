package vn.byt.qlds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.byt.qlds.config.Request;
import vn.byt.qlds.config.YamlConfig;
import vn.byt.qlds.entity_from.FamilyplanninghistoryFrom;
import vn.byt.qlds.entity_to.FamilyPlanningHistoryTo;
import vn.byt.qlds.services.TransferServices;
import vn.byt.qlds.services.familyplanninghistory.FamilyPlanningHistoryFromService;
import vn.byt.qlds.services.familyplanninghistory.FamilyPlanningHistoryToService;
import vn.byt.qlds.services.personal.PersonalToServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/family-planning-history")
public class FamilyPlanningHistoryController {
    @Autowired
    FamilyPlanningHistoryFromService familyPlanningHistoryFromService;
    @Autowired
    FamilyPlanningHistoryToService familyPlanningHistoryToService;
    @Autowired
    PersonalToServices personalToServices;
    @Autowired
    TransferServices transferServices;
    @Autowired
    YamlConfig yamlConfig;
    int SIZE_THREAD = 15;

    @PostMapping
    public boolean transfer(@RequestBody Request request) {
        long startTime = System.currentTimeMillis();
        if (yamlConfig.getFrom().contains(request.dbFrom) && yamlConfig.getDatabases().contains(request.dbTo)) {
            List<String> regionIds = familyPlanningHistoryFromService.getListRegionOfFamilyPlanningHistory(request.dbFrom);
            int throw_size = 8;
            int counter = regionIds.size();
            int totalPage = counter % throw_size == 0 ? counter / throw_size : counter / throw_size + 1;
            for (int page = 0; page < totalPage; page++) {
                int _from = page * throw_size;
                int _to;
                if (page == (totalPage - 1)) _to = counter;
                else _to = _from + throw_size;
                List<String> subRegionIds = regionIds.subList(_from, _to);
                Map<String, Integer> personalToIds = personalToServices.getMappingToIdByRegionId(request.dbTo, subRegionIds);
                List<FamilyplanninghistoryFrom> familyPlanningHistoryFroms = familyPlanningHistoryFromService.getAllByListRegionId(request.dbFrom, subRegionIds);
                Map<Integer, String> pageToPercent = new HashMap<>();
                ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(SIZE_THREAD);
                int total = familyPlanningHistoryFroms.size();
                int pageSize = total % SIZE_THREAD == 0 ? total / SIZE_THREAD : total / SIZE_THREAD + 1;
                Map<String, Integer> mapDone = new HashMap<>();

                for (int j = 0; j < SIZE_THREAD; j++) {
                    int from = j * pageSize;
                    int to;
                    if (j == (SIZE_THREAD - 1)) {
                        to = total;
                    } else {
                        to = from + pageSize;
                    }

                    List<FamilyplanninghistoryFrom> subFamilyPlanningHistoryFroms =  familyPlanningHistoryFroms.subList(from, to);
                    FamilyPlanningHistoryController.RequestHandle requestHandler = new FamilyPlanningHistoryController.RequestHandle(subFamilyPlanningHistoryFroms, personalToIds, from, to);
                    requestHandler.dbTo = request.dbTo;
                    requestHandler.regionIds = subRegionIds;
                    requestHandler.mapDone = mapDone;
                    requestHandler.page = j;
                    executor.execute(requestHandler);
                    pageToPercent.put(from, String.format("page %d  = %.2f %%", from / pageSize, 0f));
                }
                executor.shutdown();
                while (!executor.isTerminated()) {
                    // Chờ xử lý hết các request còn chờ trong Queue ...
                }
            }

            System.out.println(System.currentTimeMillis() - startTime);
            System.out.println("Family Planning History transfer finished!");
            return true;
        }
        return false;

    }

    private class RequestHandle implements Runnable {
        int from;
        int to;
        int page;
        String dbTo;
        List<String> regionIds;
        List<FamilyplanninghistoryFrom> familyPlanningHistoryFroms;
        Map<String, Integer> personalToIds;
        Map<String, Integer> mapDone;

        public RequestHandle(List<FamilyplanninghistoryFrom> familyPlanningHistoryFroms, Map<String, Integer> personalToIds, int from, int to) {
            this.personalToIds = personalToIds;
            this.from = from;
            this.to = to;
            this.familyPlanningHistoryFroms = familyPlanningHistoryFroms;
        }

        @Override
        public void run() {
            System.out.println("Start transfer Family Planning History of region  " + regionIds);
            System.out.println(String.format("Start Family Planning History synchronization progress from %d to %d", from, to));
            int size = familyPlanningHistoryFroms.size();
            try {
                final List<String> failedList = new ArrayList<>();
                int totalDone = 0;
                for (FamilyplanninghistoryFrom familyplanninghistoryFrom: familyPlanningHistoryFroms) {
                    FamilyPlanningHistoryTo familyPlanningHistoryTo = transferServices.transfer(familyplanninghistoryFrom, personalToIds);
                    String regionIdFieldId = familyPlanningHistoryTo.getRegionId() + "_" + familyPlanningHistoryTo.getPersonalId();
                    FamilyPlanningHistoryTo result = familyPlanningHistoryToService.create(dbTo, familyPlanningHistoryTo);
                    if (result == null) {
                        failedList.add(regionIdFieldId);
                    }else totalDone ++;
                    mapDone.put(String.valueOf(from), totalDone);
                }
                System.out.printf("\rFamily Planning History of region %s completed: %d, size = %d, from = %d", regionIds, totalDone, size, from);
                System.out.println("done "+ mapDone);
                System.out.println(failedList);

            } catch (Exception e) {
                System.out.println("Failed");
            }
        }
    }

}
