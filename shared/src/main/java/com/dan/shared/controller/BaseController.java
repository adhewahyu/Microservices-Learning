package com.dan.shared.controller;

import com.dan.shared.model.request.SearchRequest;
import com.dan.shared.model.response.RestResponse;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.utility.CommonConstants;
import com.dan.shared.enums.MessageCode;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BaseController {

    protected Specification getCommonSpecs(String[] rootFields, Boolean isDeleted, SearchRequest searchRequest){
        return Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(ObjectUtils.isNotEmpty(isDeleted)) {
                predicates.add(criteriaBuilder.equal(root.get(CommonConstants.FLD_IS_DELETED), isDeleted));
            }
            if (StringUtils.isNotEmpty(searchRequest.getTextSearch())) {
                List<Predicate> predicateTexts = new ArrayList<>();
                for (String rootField : rootFields) {
                    predicateTexts.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(rootField)), "%" + searchRequest.getTextSearch().toLowerCase().trim() + "%"));
                }
                predicates.add(criteriaBuilder.or(predicateTexts.toArray(new Predicate[] {})));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
        });
    }

    protected Pageable getCommonPageable(SearchRequest baseSearchRequest){
        int page = ObjectUtils.isEmpty(baseSearchRequest.getStart())? 0 : baseSearchRequest.getStart();
        int size = ObjectUtils.isEmpty(baseSearchRequest.getLimit()) ? Integer.MAX_VALUE : baseSearchRequest.getLimit();
        Boolean isAscending = StringUtils.isNotEmpty(baseSearchRequest.getSort()) && baseSearchRequest.getSort().equals(CommonConstants.SORT_ORDER_ASC);
        Sort.Direction sort = isAscending ? Sort.Direction.ASC :
                Sort.Direction.DESC;
        String sortBy = StringUtils.isEmpty(baseSearchRequest.getSortBy()) ? CommonConstants.FLD_ID : baseSearchRequest.getSortBy();
        return PageRequest.of(page, size, sort, sortBy);
    }

    protected ResponseEntity<RestResponse> getPageResponse(Page dataPage){
        if(ObjectUtils.isEmpty(dataPage)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
        }
        return new ResponseEntity<>(new RestResponse(dataPage, CommonConstants.SUCCESS_MSG_DATA_FOUND, MessageCode.OK.getValue(), true), HttpStatus.OK);
    }

    protected ResponseEntity<RestResponse> getResponseEntityFromList(List list){
        if (ObjectUtils.isNotEmpty(list)&& !list.isEmpty()){
            return new ResponseEntity<>(new RestResponse(list, CommonConstants.SUCCESS_MSG_DATA_FOUND, MessageCode.OK.getValue(), true), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
    }

    protected ResponseEntity<RestResponse> getBooleanResponse(ValidationResponse validationResponse){
        if(!validationResponse.getResult()){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, CommonConstants.ERR_MSG_EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(RestResponse.builder()
                .data(null)
                .message(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED)
                .messageCode(MessageCode.OK.getValue())
                .result(validationResponse.getResult())
                .build(), HttpStatus.OK);
    }

}
