package com.lahssini.Tp3MVCThylemeaf.web;

import com.lahssini.Tp3MVCThylemeaf.entities.Patient;
import com.lahssini.Tp3MVCThylemeaf.repositories.PatientRepository;
//import jakarta.validation.Valid;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("/user/index")
    public  String index(Model model,
    @RequestParam(name = "page",defaultValue = "0") int page ,
    @RequestParam(name = "size",defaultValue = "4") int size,
    @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listespatients",pagePatients.getContent());
     model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
     model.addAttribute("currentPage",page);
     model.addAttribute("keyword",kw);
        return "patients";
    }
    @GetMapping("/admin/deletePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePatient(@RequestParam(name = "id") Long id, 
    @RequestParam(name = "keyword",defaultValue = "") String keyword, 
    @RequestParam(name = "page",defaultValue = "0") int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatient(Model model ){
        model.addAttribute("patient",new Patient());
        return "formPatient";
    }
    @PostMapping("/admin/savePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatient(Model model,@Valid Patient patient, BindingResult bindingResult,
                              @RequestParam(name = "keyword",defaultValue = "") String keyword,
                              @RequestParam(name = "page",defaultValue = "0") int page)
            {
        if (bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(@RequestParam(name = "id") Long id, Model model,
    @RequestParam(name = "keyword",defaultValue = "") String keyword,
    @RequestParam(name = "page",defaultValue = "0") int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null)throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatient";
    }
}
