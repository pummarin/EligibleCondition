package com.checkcondition.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PpPlan {

    private String subItemNo;

    private Date serviceStart ;

    private Date serviceEnd;
}
