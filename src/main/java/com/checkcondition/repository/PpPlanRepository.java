package com.checkcondition.repository;

import com.checkcondition.entity.PpPlan;
import com.checkcondition.model.Eligible;
import com.checkcondition.model.Input;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;


@Repository
public class PpPlanRepository {

    public Eligible check(Input input){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        PpPlan p = new PpPlan();

        Eligible eligible = new Eligible();

        Date dob = new Date();
        Date currentDay = new Date();
        //start service
        try {
            p.setServiceStart(formatter.parse("01/09/2020")) ;
            p.getServiceStart().getDate();
        }catch (Exception e){
            e.getMessage();
        }

        //end service
        try {
            p.setServiceEnd(formatter.parse("30/09/2021"));
        }catch (Exception e){
            e.getMessage();
        }
        p.setSubItemNo("c19");

        //user BOD
        try {
            dob = formatter.parse(input.getDob());

        }catch (Exception e){
            e.getMessage();
        }

        LocalDate toDay = LocalDate.now();
        LocalDate start = p.getServiceStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = p.getServiceEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate uDob = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate uDobP65 = uDob.plusYears(65);
        LocalDate uDobP6M = uDob.plusMonths(6);
        LocalDate uDobP2 = uDob.plusYears(2);



        if (input.getGender().equals("F") || input.getGender().equals("M")) {
            if (toDay.isAfter(start.minusDays(1)) && toDay.isBefore(end.plusDays(1))) {
                if (input.getType().toLowerCase(Locale.ROOT).equals("gt")) {
                    return gT65(uDobP65,start,end);
                }else if (input.getType().toLowerCase(Locale.ROOT).equals("lt")){
                    return lT65(uDobP65,start,end);
                }else if (input.getType().toLowerCase(Locale.ROOT).equals("bw")){
                    return bW6M2Y(uDobP6M,uDobP2,uDob,start,end);
                }else {
                    eligible.setEligibleFlag("N");
                    eligible.setEligibleStart(null);
                    eligible.setEligibleEnd(null);
                    System.out.println(2);
                }

            }else {
                eligible.setEligibleFlag("N");
                eligible.setEligibleStart(null);
                eligible.setEligibleEnd(null);
//                System.out.println(3);
            }

        }else {
            eligible.setEligibleFlag("N");
            eligible.setEligibleStart(null);
            eligible.setEligibleEnd(null);
//            System.out.println(4);
        }
        return eligible;
    }

    public Eligible gT65(LocalDate uDob,LocalDate start, LocalDate end){
        Eligible eligible = new Eligible();
        if ((uDob.isAfter(start) && uDob.isBefore(end)) ||
                (uDob.isEqual(start) && uDob.isBefore(end))){
            eligible.setEligibleFlag("Y");
            eligible.setEligibleStart(uDob.plusDays(1).toString());
            eligible.setEligibleEnd(end.toString());
//                        System.out.println(1);
        }else if (uDob.isBefore(end)){
            eligible.setEligibleFlag("Y");
            eligible.setEligibleStart(start.toString());
            eligible.setEligibleEnd(end.toString());
//            System.out.println(2);
        }else {
            eligible.setEligibleFlag("N");
            eligible.setEligibleStart(null);
            eligible.setEligibleEnd(null);
//                        System.out.println(3);
        }
        return eligible;

    }
    public Eligible lT65(LocalDate uDob,LocalDate start, LocalDate end){
        Eligible eligible = new Eligible();
        if ((uDob.isAfter(start)) && (uDob.isBefore(end.plusDays(1)))){
            eligible.setEligibleFlag("Y");
            eligible.setEligibleStart(start.toString());
            eligible.setEligibleEnd(uDob.minusDays(1).toString());
            System.out.println(1);
        }else if (uDob.isAfter(end)){
            eligible.setEligibleFlag("Y");
            eligible.setEligibleStart(start.toString());
            eligible.setEligibleEnd(end.toString());
            System.out.println(2);
        }else {
            eligible.setEligibleFlag("N");
            eligible.setEligibleStart(null);
            eligible.setEligibleEnd(null);
            System.out.println(3);
        }
        return eligible;

    }
    public Eligible bW6M2Y(LocalDate uDobP6M,LocalDate uDob2,LocalDate uDob,LocalDate start, LocalDate end){
        Eligible eligible = new Eligible();
        if ((uDobP6M.isAfter(start) && uDobP6M.isBefore(end))  || (uDobP6M.isEqual(start) && uDobP6M.isBefore(end)) ||
                (uDobP6M.isAfter(start) && uDobP6M.isEqual(end))){
            eligible.setEligibleFlag("Y");
            eligible.setEligibleStart(uDobP6M.toString());
            eligible.setEligibleEnd(end.toString());
            System.out.println(1);
        }else if (uDob.isBefore(start) && uDob2.isAfter(end)){
            eligible.setEligibleFlag("Y");
            eligible.setEligibleStart(start.toString());
            eligible.setEligibleEnd(end.toString());
            System.out.println(2);
        }else if ((uDob2.isAfter(start) && uDob2.isBefore(end)) || (uDob2.isEqual(start) && uDob2.isBefore(end)) ||
                (uDob2.isAfter(start) && uDob2.isEqual(end))){
            eligible.setEligibleFlag("Y");
            eligible.setEligibleStart(start.toString());
            eligible.setEligibleEnd(uDob2.toString());
            System.out.println(3);
        }else {
            eligible.setEligibleFlag("N");
            eligible.setEligibleStart(null);
            eligible.setEligibleEnd(null);
            System.out.println(4);
        }
        return eligible;
    }




}
