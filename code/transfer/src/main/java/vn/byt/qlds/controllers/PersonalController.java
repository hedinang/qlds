package vn.byt.qlds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.byt.qlds.config.Request;
import vn.byt.qlds.config.YamlConfig;
import vn.byt.qlds.entity_from.PersonalFrom;
import vn.byt.qlds.entity_to.PersonalTo;
import vn.byt.qlds.services.TransferServices;
import vn.byt.qlds.services.household.HouseHoldToService;
import vn.byt.qlds.services.personal.PersonalFromServices;
import vn.byt.qlds.services.personal.PersonalToServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/personal")
public class PersonalController {
    @Autowired
    PersonalFromServices personalFromServices;
    @Autowired
    PersonalToServices personalToServices;
    @Autowired
    HouseHoldToService houseHoldToService;
    @Autowired
    TransferServices transferServices;
    @Autowired
    YamlConfig yamlConfig;

    int SIZE_THREAD = 15;

    @PostMapping
    public boolean transfer(@RequestBody Request request) {
        long startTime = System.currentTimeMillis();
        if (yamlConfig.getFrom().contains(request.dbFrom) && yamlConfig.getDatabases().contains(request.dbTo)) {
            List<String> regionIds = personalFromServices.getAllRegionIdOfPerson(request.dbFrom);
            int throw_size = 8;
            int counter = regionIds.size();
            int totalPage = counter % throw_size == 0 ? counter / throw_size : counter / throw_size + 1;

            for (int page = 0; page < totalPage; page++) {
                int _from = page * throw_size;
                int _to;
                if (page == (totalPage - 1)) _to = counter;
                else _to = _from + throw_size;
                List<String> subRegionIds = regionIds.subList(_from, _to);
                Map<String, Integer> houseHoldToID = houseHoldToService.getMappingToIDByRegionId(request.dbTo, subRegionIds);
                List<PersonalFrom> personalFroms = personalFromServices.getPersonByRegionId(request.dbFrom, subRegionIds);
                Map<String, Integer> pageToPercent = new HashMap<>();
                ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(SIZE_THREAD);
                int total = personalFroms.size();
                int pageSize = total % SIZE_THREAD == 0 ? total / SIZE_THREAD : total / SIZE_THREAD + 1;

                for (int j = 0; j < SIZE_THREAD; j++) {
                    int from = j * pageSize;
                    int to;
                    if (j == (SIZE_THREAD - 1)) {
                        to = total;
                    } else {
                        to = from + pageSize;
                    }
                    List<PersonalFrom> subPersonalFroms = personalFroms.subList(from, to);
                    PersonalController.RequestHandle requestHandler = new PersonalController.RequestHandle(subPersonalFroms, houseHoldToID, from, to);
                    requestHandler.dbTo = request.dbTo;
                    requestHandler.regionIds = subRegionIds;
                    requestHandler.pageToPercent = pageToPercent;
                    pageToPercent.put(String.valueOf(from), 0);
                    requestHandler.page = j;
                    executor.execute(requestHandler);
                }
                executor.shutdown();
                while (!executor.isTerminated()) {
                    // Chờ xử lý hết các request còn chờ trong Queue ...
                }
            }

            System.out.println(System.currentTimeMillis() - startTime);
            System.out.println("Personal transfer finished!");
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
        List<PersonalFrom> personalFroms;
        Map<String, Integer> houseHoldToID;
        Map<String, Integer> pageToPercent;

        public RequestHandle(List<PersonalFrom> personalFroms, Map<String, Integer> houseHoldToID, int from, int to) {
            this.houseHoldToID = houseHoldToID;
            this.from = from;
            this.to = to;
            this.personalFroms = personalFroms;
        }

        @Override
        public void run() {
            System.out.println("Start transfer personal of region  " + regionIds);
            System.out.println(String.format("Start Person synchronization progress from %d to %d", from, to));
            int size = personalFroms.size();
            try {
                final List<String> failedList = new ArrayList<>();
                int totalDone = 0;
                for (PersonalFrom personalFrom : personalFroms) {
                    PersonalTo personalTo = transferServices.transfer(personalFrom, houseHoldToID);
                    String regionIdFieldId = personalTo.getRegionId() + "_" + personalTo.getPersonalId();
                    PersonalTo result = personalToServices.create(dbTo, personalTo);
                    if (result == null) {
                        failedList.add(regionIdFieldId);
                    } else totalDone++;
                    pageToPercent.put(String.valueOf(from), totalDone);
                }
                System.out.flush();
                System.out.printf("\rPersonal of region %s completed:", regionIds);
                System.out.print("\rPersonal of region completed: " + pageToPercent);
                System.out.println(failedList);

            } catch (Exception e) {
                System.out.println("Failed");
            }
        }
    }

}
