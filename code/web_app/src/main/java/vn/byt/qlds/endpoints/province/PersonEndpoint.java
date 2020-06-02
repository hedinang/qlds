package vn.byt.qlds.endpoints.province;

import vn.byt.qlds.client.PersonClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model._province.person.Person;
import vn.byt.qlds.model._province.person.PersonRequest;
import vn.byt.qlds.model._province.person.PersonResponse;
import vn.byt.qlds.model.search.response.CPersonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/person")
public class PersonEndpoint {
    @Autowired
    PersonClient personClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_PERSON')")
    public PersonResponse createPerson(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody PersonRequest personRequest) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return personClient
                .createPerson(db, userId, personRequest)
                .orElseThrow(() -> new RuntimeException("Tạo nhân khẩu thất bại! vui lòng thử lại"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERSON')")
    public PersonResponse findPersonById(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return personClient
                .getPerson(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân khẩu id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_PERSON')")
    public PersonResponse updatePerson(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody PersonRequest personRequest) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        Person person = personClient
                .getPerson(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân khẩu id = " + id))
                .getPerson();

        return personClient
                .updatePerson(db, userId, person, personRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật cập nhật thất bại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_PERSON')")
    public boolean deletePerson(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        Person person = personClient
                .getPerson(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân khẩu id = " + id))
                .getPerson();
        return personClient.deletePerson(db, userId, person);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERSON')")
    public List<PersonResponse> getAllPerson(@RequestBody(required = false) Map<String, Object> query) {
        return personClient.getAllPerson(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERSON')")
    public PageResponse<PersonResponse> getPagePerson(@RequestBody(required = false) Map<String, Object> query) {
        return personClient.getPage(query != null ? query : new HashMap<>());
    }
    @GetMapping("/count-any-person")
    public CPersonResponse countAnyPersonByRegionId(@RequestParam("regionId") String regionId) throws ParseException, JsonProcessingException {
        return personClient.countTotalPerson(regionId);
    }
}
