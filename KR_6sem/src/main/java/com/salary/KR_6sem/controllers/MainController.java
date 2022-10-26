package com.salary.KR_6sem.controllers;

import com.salary.KR_6sem.models.*;
import com.salary.KR_6sem.repo.*;
import com.salary.KR_6sem.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private WorkersRepository workersRepository;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private TabelRepository tabelRepository;
    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private CharityRepository charityRepository;
    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private MailSender mailSender;

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("reg", "Регистрация");
        return "reg";
    }

    @GetMapping("/")
    public String log(Model model) {
        model.addAttribute("login", "Вход");
        return "login";
    }

    @PostMapping("/")
    public String userLogin(@RequestParam String username, @RequestParam String password, Model model) {
        Iterable<Workers> workers = workersRepository.findAll();
        List<Workers> res = new ArrayList<>();
        workers.forEach(res::add);
        List<Workers> result = new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {
            if (username.equals("a") && password.equals("a")) {
                return "redirect:/info";
            }
            else if (username.equals(res.get(i).getUsername()) && password.equals(res.get(i).getPassword())){
//                id = res.get(i).getId();
                Optional<Workers> workers1 = workersRepository.findById(res.get(i).getId());
                result.add(res.get(i));
                model.addAttribute("workers", result);
                Iterable<Taxes> taxes = taxRepository.findAll();
                model.addAttribute("taxes", taxes);
                Optional<Tabel> tabel = tabelRepository.findById(res.get(i).getTabel());
                ArrayList<Tabel> res1 = new ArrayList<>();
                tabel.ifPresent(res1::add);
                model.addAttribute("tabels", res1);
                List<Salary> salaryList = salaryRepository.findAll();
                int g=0;
                int b = salaryList.size();
                while (g<b){
                    String s = salaryList.get(g).getWorker_id();
                    if (s.equals(workers1.get().getName())){
                        Optional<Salary> sal = salaryRepository.findById(salaryList.get(g).getSalary_id());
                        ArrayList<Salary> res2 = new ArrayList<>();
                        sal.ifPresent(res2::add);
                        model.addAttribute("salary", res2);
                        System.out.println(res.get(i).getUsername());
                        String message = String.format(
                                "Здравствуйте, \n" +
                                        "Ваша зарплата в текущем месяце - %s BYN",
//                                res.get(i).getName(),
                                salaryList.get(g).getSalary()
                        );
                        mailSender.send(res.get(i).getUsername(), "Расчётный лист", message);
                    }
                    g++;
                }

//                return "redirect:/{id}/info";
                return "user-inf";
            }
        }
        return "404";
    }

    @GetMapping("/inf/{id}")
    public String info (@PathVariable(value = "id") Long id, Model model) {
        if(!workersRepository.existsById(id)){
            return "redirect:/hrror";
        }
        Optional<Workers> workers = workersRepository.findById(id);
        ArrayList<Workers> res = new ArrayList<>();
        workers.ifPresent(res::add);
        model.addAttribute("workers", res);
        Iterable<Taxes> taxes = taxRepository.findAll();
        model.addAttribute("taxes", taxes);
        Optional<Tabel> tabel = tabelRepository.findById(workers.get().getTabel());
        ArrayList<Tabel> res1 = new ArrayList<>();
        tabel.ifPresent(res1::add);
        model.addAttribute("tabels", res1);
        List<Salary> salaryList = salaryRepository.findAll();
        int g=0;
        int b = salaryList.size();
        while (g<b){
            String s = salaryList.get(g).getWorker_id();
            if (s.equals(workers.get().getName())){
                Optional<Salary> sal = salaryRepository.findById(salaryList.get(g).getSalary_id());
                ArrayList<Salary> res2 = new ArrayList<>();
                sal.ifPresent(res2::add);
                model.addAttribute("salary", res2);
            }
            g++;
        }
        return "user-inf";
    }

    @GetMapping("/charity/{id}")
    public String charity (@PathVariable(value = "id") Long id,  Model model){
        Iterable<Charity> charities = charityRepository.findAll();
        model.addAttribute("charities", charities);
        Optional<Workers> workers = workersRepository.findById(id);
        ArrayList<Workers> res = new ArrayList<>();
        workers.ifPresent(res::add);
        model.addAttribute("workers", res);
        List<Salary> salaryList = salaryRepository.findAll();
        int g=0;
        int b = salaryList.size();
        while (g<b){
            String s = salaryList.get(g).getWorker_id();
            if (s.equals(workers.get().getName())){
                Optional<Salary> sal = salaryRepository.findById(salaryList.get(g).getSalary_id());
                ArrayList<Salary> res2 = new ArrayList<>();
                sal.ifPresent(res2::add);
                model.addAttribute("salaries", res2);
            }
            g++;
        }
        return "charity";
    }

    @PostMapping("/charity/{id}")
    public String getCharity (@PathVariable(value = "id") Long id, @RequestParam Long fond, @RequestParam double summa, Model model){
        Charity charity = charityRepository.findById(fond).orElseThrow();
        charity.setSum(summa);
        charityRepository.save(charity);
        Optional<Workers> workers = workersRepository.findById(id);
        List<Salary> salaryList = salaryRepository.findAll();
        int g=0;
        int b = salaryList.size();
        while (g<b){
            String s = salaryList.get(g).getWorker_id();
            if (s.equals(workers.get().getName())){
                Optional<Salary> sal = salaryRepository.findById(salaryList.get(g).getSalary_id());
                ArrayList<Salary> res2 = new ArrayList<>();
                sal.ifPresent(res2::add);
                model.addAttribute("salaries", res2);
                Salary salary = salaryRepository.findById(salaryList.get(g).getSalary_id()).orElseThrow();
                salary.setSalary(salary.getSalary()-summa);
                salaryRepository.save(salary);
            }
            g++;
        }
        return "redirect:/charity/{id}";
    }



    @GetMapping("/apply/{id}")
    public String apply (@PathVariable(value = "id") Long id,  Model model){
        Iterable<Vacation> vacations = vacationRepository.findAll();
        model.addAttribute("vacations", vacations);
        Optional<Workers> workers = workersRepository.findById(id);
        ArrayList<Workers> res = new ArrayList<>();
        workers.ifPresent(res::add);
        model.addAttribute("workers", res);

        return "apply";
    }

    @PostMapping("/apply/{id}")
    public String getApply (@PathVariable(value = "id") Long id, @RequestParam int month, Map<String, Object> model, Map<String, Object> model1){
        Optional<Workers> workers = workersRepository.findById(id);
        ArrayList<Workers> res = new ArrayList<>();
        workers.ifPresent(res::add);
        model.put("workers", res);
        List<Vacation> vacaList = vacationRepository.findAll();

        int i=0, g = 0;
        while (i<vacaList.size()){
            if (vacaList.get(i).getMonth()==month){
                g++;
            }
            if(g>=2){
                model1.put("workers", res);
                return "505";
            }
            i++;
        }
        i = 0;
        while (i<vacaList.size()) {
            Long s = vacaList.get(i).getWorker_id();
            if (s == id) {
                Vacation vacation1 = vacationRepository.findById(vacaList.get(i).getId_vac()).orElseThrow();
                vacationRepository.delete(vacation1);
            }
            i++;
        }
        Vacation vacation = new Vacation();
        vacation.setMonth(month);
        vacation.setWorker_id(workers.get());
        vacationRepository.save(vacation);
        model1.put("workers", res);
        return "sucess";
    }
}