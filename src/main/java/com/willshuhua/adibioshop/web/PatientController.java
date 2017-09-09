package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private CustomerService customerService;


    @RequestMapping(value = "/patient_infos", method = RequestMethod.GET)
    public Object patientInfos(HttpSession httpSession){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "can't find the customer");
        }
        List<PatientInfo> patientInfoList = customerService.queryAllCustomerPatientInfos(customer.getCustomer_id());
        return new Result(Result.OK, patientInfoList);
    }

    @RequestMapping(value = "/update_patient_info", method = RequestMethod.POST)
    public Object updatePatientInfo(HttpSession httpSession, @ModelAttribute("patientInfo")PatientInfo patientInfo){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "can't find the customer");
        }
        PatientInfo targetPatientInfo = customerService.hasPatientInfoId(patientInfo.getPatient_infoid());
        if (targetPatientInfo != null){
            patientInfo.setCustomer_id(customer.getCustomer_id());
            patientInfo.setCountry("CHINA");
        }else {
            return new Result(Result.ERR, "Can't find patient info");
        }
        return new Result(Result.OK, patientInfo);
    }

    @RequestMapping(value = "/delete_patient_info", method = RequestMethod.POST)
    public Object deletePatientInfo(HttpSession httpSession, @RequestParam("patient_infoid")String patient_infoid){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        if (customer == null){
            return new Result(Result.ERR, "can't find the customer");
        }
        customerService.deletePatientInfo(patient_infoid);
        return new Result(Result.OK);
    }
}
