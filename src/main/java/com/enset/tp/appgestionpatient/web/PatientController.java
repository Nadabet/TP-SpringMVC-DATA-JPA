package com.enset.tp.appgestionpatient.web;

import com.enset.tp.appgestionpatient.entities.Patient;
import com.enset.tp.appgestionpatient.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;

    // En fait un mappage du chemin "/index" à cette méthode

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "p", defaultValue = "0") int page,
                        @RequestParam(name = "s", defaultValue = "4") int size,
                        @RequestParam(name="keyword", defaultValue="") String keyword
                        ) {
        Page<Patient> patients = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listPatients", patients.getContent());
        model.addAttribute("pages", new int[patients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        return "patients";
    }


    @GetMapping("/admin/delete")
    public String delete(Long id, String keyword, Integer page, Integer size){
        patientRepository.deleteById(id);
        if(size == null){size = 4;}
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword + "&s=" + size;
    }

    @GetMapping("/admin/createPatient")
    public String createPatient(Model model){
        model.addAttribute("patient", new Patient());
        return "createPatient";
    }

    @PostMapping("/admin/savePatient")
    public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ return "createPatient"; }
        patientRepository.save(patient);
        return "redirect:/admin/createPatient";
    }
}
