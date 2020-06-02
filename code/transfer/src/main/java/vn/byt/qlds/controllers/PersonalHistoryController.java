package vn.byt.qlds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.byt.qlds.config.Request;
import vn.byt.qlds.config.YamlConfig;
import vn.byt.qlds.entity_from.ChangeFrom;
import vn.byt.qlds.entity_to.PersonalHistoryTo;
import vn.byt.qlds.services.TransferServices;
import vn.byt.qlds.services.change.ChangeServices;
import vn.byt.qlds.services.change.PersonHistoryServices;
import vn.byt.qlds.services.personal.PersonalToServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/person-history")
public class PersonalHistoryController {
    @Autowired
    ChangeServices changeServices;
    @Autowired
    PersonHistoryServices personHistoryServices;
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
            List<String> regionIds = changeServices.getListRegionOfChange(request.dbFrom);
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
                    List<ChangeFrom> personalFroms = changeServices.getAllByListRegionId(request.dbFrom, subRegionIds);
                    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(SIZE_THREAD);
                    int total = personalFroms.size();
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
                        List<ChangeFrom> subChangeFrom = personalFroms.subList(from, to);
                        mapDone.put(String.valueOf(from), 0);
                        PersonalHistoryController.RequestHandle requestHandler = new PersonalHistoryController.RequestHandle(subChangeFrom, personalToIds, from, to);
                        requestHandler.dbTo = request.dbTo;
                        requestHandler.regionIds = subRegionIds;
                        requestHandler.page = j;
                        requestHandler.mapDone = mapDone;
                        executor.execute(requestHandler);
                    }
                    executor.shutdown();
                    while (!executor.isTerminated()) {
                        // Chờ xử lý hết các request còn chờ trong Queue ...
                    }
            }


            System.out.println(System.currentTimeMillis() - startTime);
            System.out.println("Person history transfer finished!");
            return true;
        }
        return false;

    }

    private class RequestHandle implements Runnable {
        Integer from;
        Integer to;
        Integer page;
        String dbTo;
        List<String> regionIds;
        List<ChangeFrom> changeFroms;
        Map<String, Integer> personalToIds;
        Map<String, Integer> mapDone;
        public RequestHandle(List<ChangeFrom> changeFroms, Map<String, Integer> personalToIds, Integer from, Integer to) {
            this.personalToIds = personalToIds;
            this.from = from;
            this.to = to;
            this.changeFroms = changeFroms;
        }

        @Override
        public void run() {
            String currentString = null;
            try {
                System.out.println("Start transfer personal history of region  " + regionIds);
                System.out.println(String.format("Start Person History synchronization progress from %d to %d", from, to));
                int size = changeFroms.size();
                final List<String> failedList = new ArrayList<>();
                int totalDone = 0;

                for (ChangeFrom changeFrom: changeFroms) {
                    PersonalHistoryTo personalHistoryTo = transferServices.transfer(changeFrom, personalToIds);
                    String regionIdFieldId = personalHistoryTo.getRegionId() + "_" + personalHistoryTo.getPersonalId();
                    currentString = regionIdFieldId;
                    PersonalHistoryTo result = personHistoryServices.create(dbTo, personalHistoryTo);
                    if (result == null) {
                        failedList.add(regionIdFieldId);
                    }else{
                        totalDone++;
                    }
                    mapDone.put(String.valueOf(from), totalDone);

                }
                System.out.printf("\rPersonal History  of region %s completed: %d, size = %d, from = %d", regionIds, totalDone, size, from);
                System.out.println("\n"+failedList);
                System.out.println("done "+ mapDone);
                Thread.sleep(100);

            } catch (Exception e) {
                System.out.println("Lỗi "+ currentString);
                System.out.println(e.getLocalizedMessage());
                System.out.println("Failed");
            }
        }
    }

}
