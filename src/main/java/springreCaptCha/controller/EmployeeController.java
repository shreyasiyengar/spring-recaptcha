package springreCaptCha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springreCaptCha.Repository.EmployeeRepository;
import springreCaptCha.Service.ReCaptchaValidationService;
import springreCaptCha.model.EmployeeEntity;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private ReCaptchaValidationService validator;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/register")
    public String showRegister(Model model){
        model.addAttribute("employee",new EmployeeEntity());
        return "EmployeeRegister";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") EmployeeEntity employee,
                               @RequestParam(name="g-recaptcha-response")
                               String captcha, Model model){
        if(validator.validationCaptcha(captcha)){
            employeeRepository.save(employee);
            model.addAttribute("employee",new EmployeeEntity());
            model.addAttribute("message","Employed added!!");
        }else{
            model.addAttribute("message","Please verify Captcha");
        }
            return "EmployeeRegister";
    }

    @GetMapping("/all")
    public String getAllEmployees(Model model){
        model.addAttribute("list",employeeRepository.findAll());
        return "EmployeeData";
    }
}
