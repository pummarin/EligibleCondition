package com.checkcondition.repository;

import com.checkcondition.entity.PpPlan;
import com.checkcondition.model.Eligible;
import com.checkcondition.model.Input;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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


        if (input.getGender().equals("F") || input.getGender().equals("M")) {
            if (toDay.isAfter(start.minusDays(1)) && toDay.isBefore(end.plusDays(1))) {
                if (input.getType().toLowerCase(Locale.ROOT).equals("gt")) {
                    if (graterThan65(toDay, uDob, 65)) {
                        if (currentDay.getMonth() == p.getServiceEnd().getMonth() && currentDay.getDate() == p.getServiceEnd().getDate()){
                            return setEligible(dateOfStart2(currentDay),serviceEnd(p.getServiceEnd()),"Y");
                        }else{
                            return setEligible(dateOfStart2(p.getServiceStart()),serviceEnd(p.getServiceEnd()),"Y");
                        }

                    }else if (equal65(toDay, uDob, 65) && dob.getMonth() < p.getServiceEnd().getMonth() && dob.getDate() < p.getServiceEnd().getDate()){
                            return setEligible(dateOfStart(dob,currentDay),serviceEnd(p.getServiceEnd()),"Y");
                    }else {
                        return setEligible(null,null,"N");
                    }
                }else if (input.getType().equals("bw")){
                    if(isBw6m2y(toDay, uDob,6,2)){
                        if (uDob.plusMonths(6).isBefore(start)){
                            return setEligible(dateOfStart2(p.getServiceStart()),serviceEnd(p.getServiceEnd()),"Y");
                        }else  {
                            return setEligible(datePulsMonth(dob),serviceEnd(p.getServiceEnd()),"Y");
                        }
                    }else {
                        return setEligible(null, null, "N");
                    }
                }else if (input.getType().equals("lt")){
                    return setEligible(null,null,"N");
                }else {
                    return setEligible(null,null,"N");
                }

            }else {
                return setEligible(null,null,"N");
            }

        }else {
            return setEligible(null,null,"N");
        }

    }


    public String dateOfStart(Date a,Date b){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        a.setDate(a.getDate()+1);
        a.setYear(b.getYear());
        String strDate= formatter.format(a);
        return strDate;
    }
    public String datePulsMonth(Date a){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        a.setMonth(a.getMonth()+6);
        String strDate= formatter.format(a);
        return strDate;
    }
    public String dateOfStart2(Date a){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        a.setDate(a.getDate());
        String strDate= formatter.format(a);
        return strDate;
    }
    public String serviceEnd(Date a){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        a.setDate(a.getDate());
        String strDate= formatter.format(a);
        return strDate;
    }

    public String serviceEndBeforDob(Date a,Date b){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        a.setDate(a.getDate()-1);
        a.setYear(b.getYear());
        String strDate= formatter.format(a);
        return strDate;
    }
    public Eligible setEligible(String start,String end,String c){
        Eligible eligible = new Eligible();
        eligible.setEligibleFlag(c);
        eligible.setEligibleStart(start);
        eligible.setEligibleEnd(end);
        return eligible;
    }

    public boolean graterThan65(LocalDate begin,LocalDate dob, int number){
        boolean result = false;
        Period p = Period.between(dob,begin);
        System.out.println(p.getYears()+" year "+ p.getMonths()+" month "+p.getDays()+" day");
        if (p.getYears()>= 65 && p.getMonths() >= 0 && p.getDays() >0){
            result = true;
            System.out.println(result);
        }else {
            System.out.println(result);
        }
        return result;
    }
    public boolean equal65(LocalDate begin,LocalDate dob, int number){
        boolean result = false;
        Period p = Period.between(dob,begin);
        System.out.println(p.getYears()+" year "+ p.getMonths()+" month "+p.getDays()+" day");
        if (p.getYears() == 65 && p.getMonths() == 0 && p.getDays() ==0){
            result = true;
            System.out.println(result);
        }else {
            System.out.println(result);
        }
        return result;
    }

    public boolean isBw6m2y(LocalDate begin,LocalDate dob, int month, int year){
        boolean result = false;
        Period p = Period.between(dob,begin);
        int sum = p.getYears()*12+ p.getMonths();
        System.out.println(p.getYears()+" year "+ p.getMonths()+" month "+p.getDays()+" day");
        if (sum >= 6 && sum <24){
            result = true;
            System.out.println(result);
            System.out.println(1);
        }else if (sum == 24 && p.getDays() == 0){
            result = true;
            System.out.println(2);
        }else {
            System.out.println(3);

        }
        return result;
    }
}
