package com.checkcondition.service;


import com.checkcondition.model.Eligible;
import com.checkcondition.model.Input;
import com.checkcondition.model.ResponseModel;

import com.checkcondition.repository.PpPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebService {

    @Autowired
    private PpPlanRepository ppPlanRepository;

    public ResponseModel<Eligible> check(Input input){
        ResponseModel<Eligible> result = new ResponseModel<Eligible>();
        try {
            Eligible data = this.ppPlanRepository.check(input);
            result.setStatus("OK");
            result.setMessage("Get Result success");
            result.setData(data);
        }catch (Exception e){
            result.setStatus("ERROR");
            result.setMessage("data base error" + e.getMessage());

        }
        return result;
    }
}
