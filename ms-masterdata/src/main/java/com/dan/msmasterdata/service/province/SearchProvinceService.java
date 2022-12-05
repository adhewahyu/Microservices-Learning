package com.dan.msmasterdata.service.province;

import com.dan.msmasterdata.model.entity.Province;
import com.dan.msmasterdata.model.transformer.ProvinceResponseTransformer;
import com.dan.msmasterdata.repository.ProvinceRepository;
import com.dan.shared.model.request.SpecificationRequest;
import com.dan.shared.model.response.PageResponse;
import com.dan.shared.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchProvinceService implements BaseService<SpecificationRequest, PageResponse> {

    private final ProvinceRepository provinceRepository;
    private final ProvinceResponseTransformer provinceResponseTransformer;

    @Override
    public PageResponse execute(SpecificationRequest input) {
        log.info("SearchTaskService - Called");
        Page<Province> pageTask = this.provinceRepository.findAll(input.getSpecification(), input.getPageable());
        return PageResponse.builder()
                .page(pageTask.isEmpty() ? null : pageTask.map(provinceResponseTransformer::transform))
                .build();
    }

}
