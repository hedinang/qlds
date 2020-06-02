package vn.byt.qlds.endpoints.province;

import vn.byt.qlds.client.PersonHealthyClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model._province.person.PersonHealthy;
import vn.byt.qlds.model._province.person.PersonHealthyRequest;
import vn.byt.qlds.model._province.person.PersonHealthyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/person-healthy")
public class PersonHealthyEndpoint {
    @Autowired
    PersonHealthyClient personHealthyClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_PERSON_HEALTHY')")
    public PersonHealthyResponse createPersonHealthy(
            @RequestHeader("session") String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody PersonHealthyRequest request) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return personHealthyClient
                .createPersonHealthy(db, userId, request)
                .orElseThrow(() -> new RuntimeException("Tạo sức khỏe sinh sản thất bại! vui lòng thử lại"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERSON_HEALTHY')")
    public PersonHealthyResponse findOnePersonHealthy(
            @RequestHeader("session") String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return personHealthyClient
                .getPersonHealthy(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sức khỏe sinh sản id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_PERSON_HEALTHY')")
    public PersonHealthyResponse updatePersonHealthy(
            @RequestHeader("session") String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody PersonHealthyRequest request) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");

        PersonHealthy personHealthy = personHealthyClient
                .getPersonHealthy(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sức khỏe sinh sản id = " + id))
                .personHealthy;

        return personHealthyClient
                .updatePersonHealthy(db, userId, personHealthy, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật sức khỏe sinh sản thất bại! vui lòng thử lại"));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_PERSON_HEALTHY')")
    public boolean deletePersonHealthy(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        PersonHealthy personHealthy = personHealthyClient
                .getPersonHealthy(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sức khỏe sinh sản id = " + id))
                .personHealthy;
        return personHealthyClient.deletePersonHealthy(db, userId, personHealthy);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERSON_HEALTHY')")
    public List<PersonHealthyResponse> getAllPersonHealthy(@RequestBody(required = false) Map<String, Object> query) {
        return personHealthyClient.getAllPersonHealthy(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERSON_HEALTHY')")
    public PageResponse<PersonHealthyResponse> getPagePersonHealthy(@RequestBody(required = false) Map<String, Object> query) {
        return personHealthyClient.getPage(query != null ? query : new HashMap<>());
    }
}
