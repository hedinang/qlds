package vn.byt.qlds.endpoint;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.model.UnitParam;
import vn.byt.qlds.service.UnitParamsServices;

import javax.ws.rs.*;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("unit-sys-params")
@Api("PersonHistory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class UnitParamEndpoint {

    UnitParamsServices unitParamsServices;

    @GET
    @Path("/{id}")
    public UnitParam findByID(@HeaderParam("session") String session, @PathParam("id") Integer id) {
        return unitParamsServices.read(session, id);
    }

    @POST
    public UnitParam create(@HeaderParam("session") String session, @RequestBody UnitParam unitParam) {
        return unitParamsServices.create(session, unitParam);
    }

    @PUT
    @Path("/{id}")
    public UnitParam update(
            @HeaderParam("session") String session,
            @PathParam("id") Integer id,
            @RequestBody UnitParam unitParam){
        return unitParamsServices.update(session, unitParam);
    }

    @DELETE
    @Path("/{id}")
    public boolean delete(
            @HeaderParam("session") String session,
            @PathParam("id") Integer id){
        return unitParamsServices.delete(session, id);
    }

    @GET
    @Path("/all")
    public List<UnitParam> findAll(@HeaderParam("session") String session){
        return unitParamsServices.getAll(session);
    }

}
