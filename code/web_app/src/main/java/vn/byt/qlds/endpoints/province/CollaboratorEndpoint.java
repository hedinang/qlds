package vn.byt.qlds.endpoints.province;

import vn.byt.qlds.client.CollaboratorClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.collaborator.Collaborator;
import vn.byt.qlds.model.collaborator.CollaboratorRequest;
import vn.byt.qlds.model.collaborator.CollaboratorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collaborator")
public class CollaboratorEndpoint {
    @Autowired
    CollaboratorClient collaboratorClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_COLLABORATOR')")
    public CollaboratorResponse createCollaborator(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody CollaboratorRequest collaborator) throws ParseException {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");

        long userId = userDetails.getAccount().getId();
        return collaboratorClient
                .createCollaborator(db, userId, collaborator)
                .orElseThrow(() -> new RuntimeException("Tạo cộng tác viên thất bại thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_COLLABORATOR')")
    public CollaboratorResponse findCollaboratorById(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") int id) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return collaboratorClient
                .getCollaborator(session, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cộng tác viên id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_COLLABORATOR')")
    public CollaboratorResponse updateCollaborator(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody CollaboratorRequest collaboratorRequest) throws ParseException {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");

        long userId = userDetails.getAccount().getId();
        Collaborator collaborator = collaboratorClient
                .getCollaborator(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cộng tác viên id = " + id))
                .collaborator;
        return collaboratorClient
                .updateCollaborator(db, userId, collaborator, collaboratorRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật cộng tác viên thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_COLLABORATOR')")
    public boolean deleteCollaborator(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");

        long userId = userDetails.getAccount().getId();
        Collaborator collaborator = collaboratorClient
                .getCollaborator(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cộng tác viên id = " + id))
                .collaborator;
        return collaboratorClient.deleteCollaborator(db, userId, collaborator);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_COLLABORATOR')")
    public List<CollaboratorResponse> getAllCollaborator(@RequestBody(required = false) Map<String, Object> query) {
        return collaboratorClient.getAll(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_COLLABORATOR')")
    public PageResponse<CollaboratorResponse> getPageCollaborator(@RequestBody(required = false) Map<String, Object> query) throws ParseException {
        return collaboratorClient.getPage(query != null ? query : new HashMap<>());

    }
}
