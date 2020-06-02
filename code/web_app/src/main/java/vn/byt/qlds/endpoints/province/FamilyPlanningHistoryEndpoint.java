package vn.byt.qlds.endpoints.province;

import vn.byt.qlds.client.FamilyPlanningHistoryClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model._province.family_plan.FamilyPlanningHistory;
import vn.byt.qlds.model._province.family_plan.FamilyPlanningHistoryRequest;
import vn.byt.qlds.model._province.family_plan.FamilyPlanningHistoryResponse;
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
@RequestMapping("/family-planning-history")
public class FamilyPlanningHistoryEndpoint {
    @Autowired
    FamilyPlanningHistoryClient familyPlanningHistoryClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_FP_HISTORY')")
    public FamilyPlanningHistoryResponse createFamilyPlanningHistory(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody FamilyPlanningHistoryRequest request) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return familyPlanningHistoryClient.createFamilyPlanningHistory(
                db,
                userId,
                request)
                .orElseThrow(() -> new RuntimeException("Tạo kế hoạch hóa gia đình thất bại! vui lòng thử lại"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_FP_HISTORY')")
    public FamilyPlanningHistoryResponse findOneFamilyPlanningHistory(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return familyPlanningHistoryClient
                .getFamilyPlanningHistory(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy kế hoạch hóa giâ đình id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_FP_HISTORY')")
    public FamilyPlanningHistoryResponse updateFamilyPlanningHistory(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody FamilyPlanningHistoryRequest request) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        FamilyPlanningHistory familyPlanningHistory = familyPlanningHistoryClient
                .findFamilyPlanById(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy kế hoạch hóa giâ đình id = " + id));
        return familyPlanningHistoryClient
                .updateFamilyPlanningHistory(db, userId, familyPlanningHistory, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật thất bại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_FP_HISTORY')")
    public boolean deleteFamilyPlanningHistory(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        FamilyPlanningHistory familyPlanningHistory = familyPlanningHistoryClient
                .findFamilyPlanById(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy kế hoạch hóa giâ đình id = " + id));
        return familyPlanningHistoryClient.deleteFamilyPlanningHistory(db, userId, familyPlanningHistory);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_FP_HISTORY')")
    public List<FamilyPlanningHistoryResponse> getAllFamilyPlanningHistory(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @RequestBody(required = false) Map<String, Object> query) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return familyPlanningHistoryClient.getAllFamilyPlanningHistory(db, query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_FP_HISTORY')")
    public PageResponse<FamilyPlanningHistoryResponse> getPageFamilyPlanningHistory(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @RequestBody(required = false) Map<String, Object> query) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return familyPlanningHistoryClient.getPage(db, query != null ? query : new HashMap<>());
    }
}
