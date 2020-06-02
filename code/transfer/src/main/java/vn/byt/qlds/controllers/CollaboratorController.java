package vn.byt.qlds.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.byt.qlds.config.Request;
import vn.byt.qlds.config.YamlConfig;
import vn.byt.qlds.entity_from.FieldworkerFrom;
import vn.byt.qlds.entity_to.CollaboratorTo;
import vn.byt.qlds.services.collaborator.CollaboratorService;
import vn.byt.qlds.services.collaborator.FieldWorkerService;
import vn.byt.qlds.services.TransferServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/collaborator")
public class CollaboratorController {
    @Autowired
    FieldWorkerService fieldWorkerService;
    @Autowired
    CollaboratorService collaboratorService;
    @Autowired
    TransferServices transferServices;
    @Autowired
    YamlConfig yamlConfig;

    @PostMapping
    public boolean transfer(@RequestBody Request request) {
        if (yamlConfig.getFrom().contains(request.dbFrom) && yamlConfig.getDatabases().contains(request.dbTo)) {
            List<FieldworkerFrom> fieldworkeres = fieldWorkerService.getAll(request.dbFrom);
            int size = fieldworkeres.size();
            AtomicInteger index = new AtomicInteger();
            List<String> failedList = new ArrayList<>();

            fieldworkeres.forEach(fieldworkerFrom -> {
                System.out.println();
                index.incrementAndGet();
                System.out.println();
                CollaboratorTo collaborator = transferServices.transfer(fieldworkerFrom);
                collaborator.setId(index.get());
                String regionIdFieldId = collaborator.getRegionId() + "_" + collaborator.getCollaboratorId();
                CollaboratorTo result = collaboratorService.create(request.dbTo, collaborator);
                if (result == null) {
                    failedList.add(regionIdFieldId);
                } else {
                    System.out.println("Collaborator  transfered ==> " + regionIdFieldId);
                }
                System.out.println("Collaborator processing : " + (index.get() * 100 / size) + "%");
            });
            System.out.println(fieldworkeres.size());
            System.out.println("Collaborator transfer failed : " + failedList);
            System.out.println("Collaborator transfer finished!");
            return true;
        } else return false;
    }
}
