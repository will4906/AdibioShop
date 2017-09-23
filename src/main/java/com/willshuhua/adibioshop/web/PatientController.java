package com.willshuhua.adibioshop.web;

import com.willshuhua.adibioshop.dto.common.Result;
import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.PatientInfo;
import com.willshuhua.adibioshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
public class PatientController {

    @Autowired
    private CustomerService customerService;


    @RequestMapping(value = "/patient_infos", method = RequestMethod.GET)
    @ResponseBody
    public Object patientInfos(HttpSession httpSession){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        List<PatientInfo> patientInfoList = customerService.queryAllCustomerPatientInfos(customer.getCustomer_id());
        return new Result(Result.OK, patientInfoList);
    }

    @RequestMapping(value = "/update_patient_info", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePatientInfo(HttpSession httpSession, @RequestBody PatientInfo patientInfo){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        PatientInfo targetPatientInfo = customerService.hasPatientInfoId(patientInfo.getPatient_infoid());
        if (targetPatientInfo != null){
            patientInfo.setCustomer_id(customer.getCustomer_id());
            patientInfo.setCountry("CHINA");
        }else {
            return new Result(Result.ERR, "Can't find patient info");
        }
        customerService.updatePatientInfo(patientInfo);
        return new Result(Result.OK, patientInfo);
    }

    @RequestMapping(value = "/delete_patient_info", method = RequestMethod.POST)
    @ResponseBody
    public Object deletePatientInfo(HttpSession httpSession, @RequestParam("patient_infoid")String patient_infoid){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        customerService.deletePatientInfo(patient_infoid);
        return new Result(Result.OK);
    }

    @RequestMapping(value = "/add_patient_info", method = RequestMethod.POST)
    @ResponseBody
    public Object addPatientInfo(HttpSession httpSession, @RequestBody PatientInfo patientInfo){
        Customer customer = (Customer)httpSession.getAttribute("customer");
        patientInfo.setCountry("CHINA");
        PatientInfo patientInfo1 = customerService.hasPatientInfo(patientInfo);
        if (patientInfo1 == null){
            patientInfo.setCustomer_id(customer.getCustomer_id());
            patientInfo.setPatient_infoid(UUID.randomUUID().toString());
            customerService.createPatientInfo(patientInfo);
            return new Result(Result.OK, patientInfo);
        }
        return new Result(Result.ERR, "repeat", patientInfo1);
    }

    @RequestMapping(value = "/patient_infos_page", method = RequestMethod.GET)
    public ModelAndView patientInfosPage(){
        return new ModelAndView("/info/patient_infos");
    }
}
