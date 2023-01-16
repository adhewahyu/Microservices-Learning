package com.dan.msmasterdata.service.city;

import com.dan.msmasterdata.model.entity.City;
import com.dan.msmasterdata.model.transformer.CityResponseTransformer;
import com.dan.msmasterdata.repository.CityRepository;
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
public class SearchCityService implements BaseService<SpecificationRequest, PageResponse> {

    private final CityRepository cityRepository;
    private final CityResponseTransformer cityResponseTransformer;

    @Override
    public PageResponse execute(SpecificationRequest input) {
        log.info("SearchTaskService - Called");
        Page<City> pageCity = this.cityRepository.findAll(input.getSpecification(), input.getPageable());
        return PageResponse.builder()
                .page(pageCity.isEmpty() ? null : pageCity.map(cityResponseTransformer::transform))
                .build();
    }

}
