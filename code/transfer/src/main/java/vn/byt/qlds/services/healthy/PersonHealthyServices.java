package vn.byt.qlds.services.healthy;

import org.springframework.stereotype.Service;
import vn.byt.qlds.config.db.CrudService;
import vn.byt.qlds.entity_to.GenerateHealthyTo;


@Service
public class PersonHealthyServices extends CrudService<GenerateHealthyTo, Integer> {
}
