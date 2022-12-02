package com.dan.msnotification.controller;

import com.dan.msnotification.model.request.SendBasicMailRequest;
import com.dan.msnotification.model.response.SendMailResponse;
import com.dan.msnotification.service.SendBasicMailService;
import com.dan.shared.controller.BaseController;
import com.dan.shared.enums.MessageCode;
import com.dan.shared.model.response.RestResponse;
import com.dan.shared.utility.CommonConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Api(tags = "Email APIs")
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController extends BaseController {

    private final SendBasicMailService sendBasicMailService;

    @PostMapping("/v1/send-basic")
    public Mono<ResponseEntity<RestResponse>> sendBasicEmail(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody SendBasicMailRequest request
    ){
        SendMailResponse response = sendBasicMailService.execute(request);
        return Mono.just(new ResponseEntity<>(
                new RestResponse(response,
                        response.getEmailResponse(),
                        response.getSuccessSent() ? MessageCode.OK.getValue() : MessageCode.NOK.getValue(),
                        response.getSuccessSent()),
                HttpStatus.OK));
    }

}
