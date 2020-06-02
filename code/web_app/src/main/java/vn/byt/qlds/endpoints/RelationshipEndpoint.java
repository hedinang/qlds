package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.RelationshipClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.relationship.Relationship;
import vn.byt.qlds.model.relationship.RelationshipRequest;
import vn.byt.qlds.model.relationship.RelationshipResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/relationship")
public class RelationshipEndpoint {
    @Autowired
    RelationshipClient relationshipClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_RELATIONSHIP')")
    public RelationshipResponse createRelationship(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody RelationshipRequest relationshipRequest) {
        long userId = userDetails.getAccount().getId();
        return relationshipClient
                .createRelationship(userId, relationshipRequest)
                .orElseThrow(() -> new RuntimeException("Tạo mối quan hệ thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_RELATIONSHIP')")
    public RelationshipResponse findRelationshipById(@PathVariable("id") int id) {
        return relationshipClient
                .getRelationship(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy mối quan hệ id = " + id));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_RELATIONSHIP')")
    public RelationshipResponse updateRelationship(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody RelationshipRequest relationshipRequest) {
        long userId = userDetails.getAccount().getId();
        Relationship relationship = relationshipClient
                .getRelationship(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy mối quan hệ id = " + id))
                .relationship;
        return relationshipClient
                .updateRelationship(userId, relationship, relationshipRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật mối quan hệ thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_RELATIONSHIP')")
    public boolean deleteRelationship(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        Relationship relationship = relationshipClient
                .getRelationship(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy mối quan hệ id = " + id))
                .relationship;
        return relationshipClient.deleteRelationship(userId, relationship);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_RELATIONSHIP')")
    public List<RelationshipResponse> getAllRelationship(@RequestBody(required = false) Map<String, Object> query) {
        return relationshipClient.getAllRelationship(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_RELATIONSHIP')")
    public PageResponse<RelationshipResponse> getPageRelationship(@RequestBody(required = false) Map<String, Object> query) {
        return relationshipClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_RELATIONSHIP')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<Relationship> relationships = relationshipClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportRelationship(relationships);
    }
}
