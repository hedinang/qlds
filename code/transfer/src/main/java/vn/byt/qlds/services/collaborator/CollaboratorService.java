package vn.byt.qlds.services.collaborator;

import org.springframework.stereotype.Component;
import vn.byt.qlds.config.db.CrudService;
import vn.byt.qlds.entity_to.CollaboratorTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CollaboratorService extends CrudService<CollaboratorTo, Integer> {
    public Map<String, Integer> getMappingToID(String idSession){
        Map<String, Integer> map = new HashMap<>();
        List<CollaboratorTo> collaboratorTos = getAll(idSession);
        collaboratorTos.forEach(collaboratorTo -> {
            String key = collaboratorTo.getRegionId() + collaboratorTo.getCollaboratorId();
            map.put(key, collaboratorTo.getId());
        });
        return map;
    }
}
