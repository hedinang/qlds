package vn.byt.qlds.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.byt.qlds.config.Request;
import vn.byt.qlds.config.YamlConfig;
import vn.byt.qlds.entity_from.AddressFrom;
import vn.byt.qlds.entity_to.AddressTo;
import vn.byt.qlds.services.*;
import vn.byt.qlds.services.address.AddressFromService;
import vn.byt.qlds.services.address.AddressToService;
import vn.byt.qlds.services.collaborator.CollaboratorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressFromService addressFromService;
    @Autowired
    AddressToService addressToService;
    @Autowired
    CollaboratorService collaboratorService;
    @Autowired
    TransferServices transferServices;
    @Autowired
    YamlConfig yamlConfig;

    @PostMapping
    public boolean transfer(@RequestBody Request request) {
        if (yamlConfig.getFrom().contains(request.dbFrom) && yamlConfig.getDatabases().contains(request.dbTo)) {
            Map<String, Integer> collaboratorToID = collaboratorService.getMappingToID(request.dbTo);
            List<AddressFrom> address = addressFromService.getAll(request.dbFrom);
            int size = address.size();
            AtomicInteger index = new AtomicInteger();
            List<String> failedList = new ArrayList<>();
            address.forEach(addressFrom -> {
                System.out.println();
                index.incrementAndGet();
                AddressTo addressTo = transferServices.transfer(addressFrom, collaboratorToID);
                addressTo.setId(index.get());
                String regionIdFieldId = addressTo.getRegionId() + "_" + addressTo.getCollaboratorId();
                AddressTo result = addressToService.create(request.dbTo, addressTo);
                if (result == null) {
                    failedList.add(regionIdFieldId);
                } else {
                    System.out.println("Address  transfered ==> " + regionIdFieldId);
                }
                System.out.println("Address processing : " + (index.get() * 100 / size) + "%");
            });
            System.out.println(address.size());
            System.out.println("Address transfer failed : " + failedList);
            System.out.println("Address transfer finished!");
            return true;
        } else return false;
    }
}
