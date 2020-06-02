package vn.byt.qlds.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.byt.qlds.config.Request;
import vn.byt.qlds.config.YamlConfig;
import vn.byt.qlds.entity_from.HouseholdFrom;
import vn.byt.qlds.entity_to.HouseholdTo;
import vn.byt.qlds.services.TransferServices;
import vn.byt.qlds.services.address.AddressToService;
import vn.byt.qlds.services.household.HouseHoldFromService;
import vn.byt.qlds.services.household.HouseHoldToService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/house-hold")
public class HouseHoldController {
    @Autowired
    HouseHoldFromService houseHoldFromService;
    @Autowired
    HouseHoldToService houseHoldToService;
    @Autowired
    AddressToService addressToService;
    @Autowired
    TransferServices transferServices;
    @Autowired
    YamlConfig yamlConfig;
    int SIZE_THREAD = 10;
    int LIMIT = 30000;

    @PostMapping
    public boolean transfer(@RequestBody Request request) {
        long startTime = System.currentTimeMillis();
        if (yamlConfig.getFrom().contains(request.dbFrom) && yamlConfig.getDatabases().contains(request.dbTo)) {
            long counter = houseHoldFromService.count(request.dbFrom);
            Map<String, Integer> addressToIds = addressToService.getMappingToID(request.dbTo);
            int totalPage = (int) (counter % LIMIT == 0 ? counter / LIMIT : counter / LIMIT + 1);
            for (int i = 1; i <= totalPage; i++) {

                ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(SIZE_THREAD);
                Map<Integer, String> pageToPercent = new HashMap<>();
                List<HouseholdFrom> householdFroms = houseHoldFromService.getPage(request.dbFrom, i, LIMIT);
                int limitOfPage = householdFroms.size();
                System.out.println("Moving page :" + i + " " + limitOfPage);
                int pageSize = limitOfPage % SIZE_THREAD == 0 ? limitOfPage / SIZE_THREAD : limitOfPage / SIZE_THREAD + 1;
                for (int j = 0; j < SIZE_THREAD; j++) {
                    int from = j * pageSize;
                    int to;
                    if (j == (SIZE_THREAD - 1)) {
                        to = limitOfPage;
                    } else {
                        to = from + pageSize;
                    }

                    RequestHandle requestHandler = new RequestHandle(householdFroms, addressToIds, from, to);
                    requestHandler.dbTo = request.dbTo;
                    requestHandler.pageToPercent = pageToPercent;
                    requestHandler.page = i;
                    executor.execute(requestHandler);
                    pageToPercent.put(from, String.format("page %d  = %.2f %%", from / pageSize, 0f));
                }
                executor.shutdown();
                while (!executor.isTerminated()) {
                    // Chờ xử lý hết các request còn chờ trong Queue ...
                }
            }
            System.out.println(System.currentTimeMillis() - startTime);
            System.out.println("House hold transfer finished!");
            return true;
        } else return false;
    }

    private class RequestHandle implements Runnable {
        int from;
        int to;
        int page;
        String dbTo;
        List<HouseholdFrom> householdFroms;
        Map<String, Integer> addressToIds;
        Map<Integer, String> pageToPercent;

        public RequestHandle(List<HouseholdFrom> householdFroms, Map<String, Integer> addressToIds, int from, int to) {
            this.addressToIds = addressToIds;
            this.from = from;
            this.to = to;
            this.householdFroms = householdFroms;
        }

        @Override
        public void run() {
            System.out.println(String.format("Start House hold synchronization progress from %d to %d", from, to));
            int size = to - from;
            int subPage = from / size;

            try {
                final List<String> failedList = new ArrayList<>();
                for (int i = from; i < to; i++) {
                    HouseholdFrom houseHoldFrom = householdFroms.get(i);
                    HouseholdTo householdTo = transferServices.transfer(houseHoldFrom, addressToIds);
                    int id = (page - 1) * LIMIT + i + 1;
                    householdTo.setId(id);
                    String regionIdFieldId = householdTo.getRegionId() + "_" + householdTo.getHouseholdId();
                    HouseholdTo result = houseHoldToService.create(dbTo, householdTo);
                    if (result == null) {
                        failedList.add(regionIdFieldId);
                    }
                    pageToPercent.put(from, String.format("page %d.%d  = %.2f %%", page, subPage, (i + 1 - from) * 100f / size));
                    System.out.flush();
                    System.out.printf("\rHouse Hold completed: %3s%%", pageToPercent.values());
                }
                System.out.println(failedList);

            } catch (Exception e) {
                System.out.println("Failed");
            }
        }
    }


}
