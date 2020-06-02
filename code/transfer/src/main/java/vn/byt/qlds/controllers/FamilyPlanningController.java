package vn.byt.qlds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.byt.qlds.config.Request;
import vn.byt.qlds.config.YamlConfig;
import vn.byt.qlds.entity_from.FamilyplanningFrom;
import vn.byt.qlds.entity_to.FamilyPlanningTo;
import vn.byt.qlds.services.TransferServices;
import vn.byt.qlds.services.familyplanning.FamilyPlanningFromServices;
import vn.byt.qlds.services.familyplanning.FamilyPlanningToServices;
import vn.byt.qlds.services.personal.PersonalToServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/family-planning")
public class FamilyPlanningController {
    @Autowired
    FamilyPlanningFromServices familyPlanningFromServices;
    @Autowired
    FamilyPlanningToServices familyPlanningToServices;
    @Autowired
    PersonalToServices personalToServices;
    @Autowired
    TransferServices transferServices;
    @Autowired
    YamlConfig yamlConfig;
    int SIZE_THREAD = 15;
    int LIMIT = 30000;

    @PostMapping
    public boolean transfer(@RequestBody Request request) {
        long startTime = System.currentTimeMillis();
        if (yamlConfig.getFrom().contains(request.dbFrom) && yamlConfig.getDatabases().contains(request.dbTo)) {
            List<String> regionIds = familyPlanningFromServices.getListRegionOfFamilyPlanning(request.dbFrom);
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
                List<FamilyplanningFrom> familyPlanningFroms = familyPlanningFromServices.getAllByListRegionId(request.dbFrom, subRegionIds);
                ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(SIZE_THREAD);
                int total = familyPlanningFroms.size();
                int pageSize = total % SIZE_THREAD == 0 ? total / SIZE_THREAD : total / SIZE_THREAD + 1;

                for (int j = 0; j < SIZE_THREAD; j++) {
                    int from = j * pageSize;
                    int to;
                    if (j == (SIZE_THREAD - 1)) {
                        to = total;
                    } else {
                        to = from + pageSize;
                    }
                    List<FamilyplanningFrom> subFamilyPlanningFroms = familyPlanningFroms.subList(from, to);
                    FamilyPlanningController.RequestHandle requestHandler = new FamilyPlanningController.RequestHandle(subFamilyPlanningFroms, personalToIds, from, to);
                    requestHandler.dbTo = request.dbTo;
                    requestHandler.regionIds = subRegionIds;
                    requestHandler.page = j;
                    executor.execute(requestHandler);
                }
                executor.shutdown();
                while (!executor.isTerminated()) {
                    // Chờ xử lý hết các request còn chờ trong Queue ...
                }
            }

            System.out.println(System.currentTimeMillis() - startTime);
            System.out.println("Family planning transfer finished!");
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
        List<FamilyplanningFrom> familyPlanningFroms;
        Map<String, Integer> personalToIds;

        public RequestHandle(List<FamilyplanningFrom> familyPlanningFroms, Map<String, Integer> personalToIds, int from, int to) {
            this.personalToIds = personalToIds;
            this.from = from;
            this.to = to;
            this.familyPlanningFroms = familyPlanningFroms;
        }

        @Override
        public void run() {
            System.out.println("Start transfer Family planning of region  " + regionIds);
            System.out.println(String.format("Start Family planning synchronization progress from %d to %d", from, to));
            int size = to - from;

            try {
                final List<String> failedList = new ArrayList<>();
                int totalDone = 0;
                for (FamilyplanningFrom familyplanningFrom : familyPlanningFroms) {
                    FamilyPlanningTo familyPlanningTo = transferServices.transfer(familyplanningFrom, personalToIds);
                    String regionIdFieldId = familyPlanningTo.getRegionId() + "_" + familyPlanningTo.getPersonalId();
                    FamilyPlanningTo result = familyPlanningToServices.create(dbTo, familyPlanningTo);
                    if (result == null) {
                        failedList.add(regionIdFieldId);
                    } else totalDone++;
                }
                System.out.flush();
                System.out.printf("\rFamily planning  of region %s completed: %d, size = %d, from = %d", regionIds, totalDone, size, from);
                System.out.println(failedList);

            } catch (Exception e) {
                System.out.println("Failed");
            }
        }
    }

}
