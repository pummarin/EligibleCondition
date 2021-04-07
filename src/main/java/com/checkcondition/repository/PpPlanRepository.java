package com.checkcondition.repository;

import com.checkcondition.entity.PpPlan;
import com.checkcondition.model.Eligible;
import com.checkcondition.model.Input;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;


@Repository
public class PpPlanRepository {

    public Eligible check(Input input){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            Date date = formatter.parse(input.getBod());
//            date.setDate(date.getDate()-1);
//            String strDate= formatter.format(date);
//            System.out.println(strDate);
//
//        }catch (Exception e){
//            e.getMessage();
//        }

        PpPlan p = new PpPlan();
//        Dmy begin = new Dmy();
//        Dmy end = new Dmy();
//        Dmy bod = new Dmy();
        Eligible eligible = new Eligible();

//        String[] pStart = new String[2];
//        String[] pEnd = new String[2];
        Date dob = new Date();

        //start service
        try {
            p.setServiceStart(formatter.parse("01/09/2020")) ;
            p.getServiceStart().getDate();
        }catch (Exception e){
            e.getMessage();
        }
//        pStart = p.getServiceStart().split("/");
//        begin.setDd(Long.parseLong(pStart[0]));
//        begin.setMm(Long.parseLong(pStart[1]));
//        begin.setYyyy(Long.parseLong(pStart[2]));

        //end service
        try {
            p.setServiceEnd(formatter.parse("30/09/2021"));
        }catch (Exception e){
            e.getMessage();
        }

//        pEnd = p.getServiceEnd().split("/");
//        end.setDd(Long.parseLong(pEnd[0]));
//        end.setMm(Long.parseLong(pEnd[1]));
//        end.setYyyy(Long.parseLong(pEnd[2]));

        p.setSubItemNo("c19");

        //user BOD
        try {
            dob = formatter.parse(input.getDob());

        }catch (Exception e){
            e.getMessage();
        }

//        dob.setDd(Long.parseLong(uDate[0]));
//        dob.setMm(Long.parseLong(uDate[1]));
//        dob.setYyyy(Long.parseLong(uDate[2]));

        if (input.getGender().equals("F") || input.getGender().equals("M")) {
            if (p.getServiceStart().getYear() - dob.getYear() > 65){
                eligible.setEligibleFlag("Y");
                eligible.setEligibleStart(dateOfStart2(p.getServiceStart()));
                eligible.setEligibleEnd(serviceEnd(p.getServiceEnd()));
                System.out.println("1111");

            }else if ((p.getServiceStart().getYear() - dob.getYear()) == 65) {
                if (p.getServiceStart().getMonth() == dob.getMonth()) {
                    eligible.setEligibleFlag("Y");
                    eligible.setEligibleStart(dateOfStart(dob,p.getServiceStart()));
                    eligible.setEligibleEnd(serviceEnd(p.getServiceEnd()));
                    System.out.println("2222");
                }else if (dob.getMonth() > p.getServiceStart().getMonth()) {
                    eligible.setEligibleFlag("Y");
                    eligible.setEligibleStart(dateOfStart2(p.getServiceStart()));
                    eligible.setEligibleEnd(serviceEndBeforDob(dob,p.getServiceEnd()));
                    System.out.println("3333");
                }else {
                    eligible.setEligibleFlag("Y");
                    eligible.setEligibleStart(dateOfStart2(p.getServiceStart()));
                    eligible.setEligibleEnd(serviceEnd(p.getServiceEnd()));
                    System.out.println("4444");

                }
            }else if ((p.getServiceStart().getYear() - dob.getYear() >= 0) && (p.getServiceStart().getYear() - dob.getYear()) <= 2) {
                if (p.getServiceStart().getMonth() - dob.getMonth() >= 6){
                    eligible.setEligibleFlag("Y");
                    eligible.setEligibleStart(dateOfStart(dob,p.getServiceStart()));
                    eligible.setEligibleEnd(serviceEnd(p.getServiceEnd()));
                    System.out.println("5555");
                }else{
                    eligible.setEligibleFlag("N");
                    eligible.setEligibleStart(null);
                    eligible.setEligibleEnd(null);
                    System.out.println("6666");
                }

            }else if ((p.getServiceStart().getYear() - dob.getYear()) < 65 && (p.getServiceStart().getYear() - dob.getYear() > 2)) {
                System.out.println(p.getServiceStart().getYear());
                System.out.println(dob.getYear());
                eligible.setEligibleFlag("Y");
                eligible.setEligibleStart(dateOfStart2(p.getServiceStart()));
                eligible.setEligibleEnd(serviceEndBeforDob(dob,p.getServiceEnd()));
                System.out.println("7777");

            }else  {
                eligible.setEligibleFlag("N");
                eligible.setEligibleStart(null);
                eligible.setEligibleEnd(null);
                System.out.println("8888");
            }
        }else{
            eligible.setEligibleFlag("N");
            eligible.setEligibleStart(null);
            eligible.setEligibleEnd(null);
            System.out.println("9999");
        }
        return eligible;
    }
    public String dateOfStart(Date a,Date b){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        a.setDate(a.getDate()+1);
        a.setYear(b.getYear());
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

}
