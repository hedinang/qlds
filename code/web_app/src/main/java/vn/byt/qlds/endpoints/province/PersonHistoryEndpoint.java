package vn.byt.qlds.endpoints.province;

import vn.byt.qlds.client.PersonHistoryClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model._province.person.PersonHistory;
import vn.byt.qlds.model._province.person.PersonHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/person-history")
public class PersonHistoryEndpoint {
    @Autowired
    PersonHistoryClient personHistoryClient;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERSON_HISTORY')")
    public PersonHistory findOnePersonHistory(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return personHistoryClient.getPersonHistory(db, id);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERSON_HISTORY')")
    public List<PersonHistory> getAllPersonHistory(@RequestBody(required = false) Map<String, Object> query) {
        return personHistoryClient.getAllPersonHistory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERSON_HISTORY')")
    public PageResponse<PersonHistoryResponse> getPagePersonHistory(@RequestBody(required = false) Map<String, Object> query) {
        return personHistoryClient.getPage(query != null ? query : new HashMap<>());
    }
}
