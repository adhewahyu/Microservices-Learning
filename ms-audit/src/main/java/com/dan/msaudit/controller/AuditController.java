package com.dan.msaudit.controller;

import com.dan.msaudit.model.entity.Audit;
import com.dan.msaudit.model.request.AddAuditRequest;
import com.dan.msaudit.model.request.SearchByModuleRequest;
import com.dan.msaudit.service.AddAuditService;
import com.dan.msaudit.service.SearchAuditService;
import com.dan.msaudit.utility.Constants;
import com.dan.shared.controller.BaseController;
import com.dan.shared.model.request.SpecificationRequest;
import com.dan.shared.model.response.RestResponse;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.utility.CommonConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Microservice Audit APIs")
@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController extends BaseController {

    private final AddAuditService addAuditService;
    private final SearchAuditService searchAuditService;

    @PostMapping("/v1/add")
    public ResponseEntity<RestResponse> addAudit(
            @RequestHeader(value = CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody AddAuditRequest addAuditRequest){
        ValidationResponse validationResponse = addAuditService.execute(addAuditRequest);
        return super.getBooleanResponse(validationResponse);
    }

    @GetMapping("/v1/search")
    public ResponseEntity<RestResponse> getAudits(
            @RequestHeader(value = CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            SearchByModuleRequest searchByModuleRequest) {
        Specification<Audit> specs = Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNotEmpty(searchByModuleRequest.getModule())){
                predicates.add(criteriaBuilder.equal(root.get(Constants.FLD_MODULE), searchByModuleRequest.getModule()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
        });
        return this.getPageResponse(searchAuditService.execute(SpecificationRequest.builder()
                .pageable(getCommonPageable(searchByModuleRequest))
                .specification(specs).build()).getPage());

    }

}
