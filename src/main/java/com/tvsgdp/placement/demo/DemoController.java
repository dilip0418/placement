package com.tvsgdp.placement.demo;

import com.tvsgdp.placement.config.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/demo-controller")
public class DemoController {

    @GetMapping
    public ResponseEntity<Object> demo() {

        return ResponseHandler.generateResponse(
                "Checked the JWT token based access to the api",
                HttpStatus.OK,
                "Hey there!, this is an api that caters the placement application"
        );
    }

}
