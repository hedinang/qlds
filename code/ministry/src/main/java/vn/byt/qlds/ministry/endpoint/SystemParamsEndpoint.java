package vn.byt.qlds.ministry.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import vn.byt.qlds.ministry.model.SystemParams;
import vn.byt.qlds.ministry.service.SystemParamsServices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.List;

@Path("system-params")
public class SystemParamsEndpoint {
    @Autowired
    SystemParamsServices systemParamsServices;

    @GET
    @Path("/all")
    public List<SystemParams> findAll() throws IOException {
        return systemParamsServices.getAll(null);
    }

}
