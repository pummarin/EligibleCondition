package com.checkcondition.controller;

import com.checkcondition.model.Eligible;
import com.checkcondition.model.Input;
import com.checkcondition.model.ResponseModel;
import com.checkcondition.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class WebController {
    @Autowired
    private WebService webService;

    @CrossOrigin("*")
    @RequestMapping(value = "/checkService", method = RequestMethod.POST)
    public ResponseModel <Eligible> checkService(@RequestBody Input input){
        return webService.check(input);
    }
}
