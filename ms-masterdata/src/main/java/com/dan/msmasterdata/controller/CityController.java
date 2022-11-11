package com.dan.msmasterdata.controller;

import com.dan.shared.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Microservice Master Data - City APIs")
@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController extends BaseController {



}
