package com.salary.KR_6sem.controllers;

import com.salary.KR_6sem.models.*;
import com.salary.KR_6sem.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AdminController {
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
    private VacationRepository vacationRepository;

    @GetMapping("/info")
    public String info (Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        Iterable<Workers> workers = workersRepository.findAll();
        model.addAttribute("workers", workers);
        Iterable<Taxes> taxes = taxRepository.findAll();
        model.addAttribute("taxes", taxes);
        Iterable<Salary> salaries = salaryRepository.findAll();
        model.addAttribute("salaries", salaries);
        Iterable<Tabel> tabels = tabelRepository.findAll();
        model.addAttribute("tabels", tabels);
        Iterable<Vacation> vacations = vacationRepository.findAll();
        model.addAttribute("vacations", vacations);
        return "info";
    }

    @GetMapping("/info-work")
    public String winfo (Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        Iterable<Workers> workers = workersRepository.findAll();
        model.addAttribute("workers", workers);
        Iterable<Taxes> taxes = taxRepository.findAll();
        model.addAttribute("taxes", taxes);
        Iterable<Salary> salaries = salaryRepository.findAll();
        model.addAttribute("salaries", salaries);
        Iterable<Tabel> tabels = tabelRepository.findAll();
        model.addAttribute("tabels", tabels);
        return "inf-work";
    }

    @GetMapping("/sort")
    public String sort (Model model){
        Iterable<Workers> workers = workersRepository.findAll(Sort.by("name"));
        model.addAttribute("workers", workers);
        return "inf-work";
    }

    @PostMapping("find")
    public String find (@RequestParam String Wname, Map<String, Object> model){
        Iterable<Workers> workers;
        if(Wname != null && !Wname.isEmpty()){
            workers = workersRepository.findByName(Wname);
        } else{
            workers = workersRepository.findAll();
        }
       model.put("workers", workers);
       return "inf-work";
    }

    @PostMapping("filter")
    public String filter (@RequestParam(defaultValue = "false") boolean benefit, Map<String, Object> model){
        Iterable<Workers> workers;
//        if(sizeof(benefit)){
            workers = workersRepository.findByBenefit(benefit);
//        } else{
//            workers = workersRepository.findAll();
//        }
        model.put("workers", workers);
        return "inf-work";
    }

    @PostMapping("/reg")
    public String add(@RequestParam String name, @RequestParam String username, @RequestParam String password,
                      @RequestParam (defaultValue = "false") boolean benefit, @RequestParam Post job_id, Map<String, Object> model) {
        Workers worker = new Workers(name, username, password, benefit, job_id);
        if (!postRepository.existsById(job_id.getJob_id())){
            model.put("message", "Неверная должность");
            return "reg";
//            return "redirect:/error";
        }
        Workers workersDB = workersRepository.findByUsername(name);
        if (workersDB != null){
            model.put("workers", "User exists");
            return "reg";
        }
        workersRepository.save(worker);
        Iterable<Workers> workers = workersRepository.findAll();
        model.put("workers", workers);
        return "redirect:/info";
    }


    //    @GetMapping("/log/{login}")
//    public String ifUser(@PathVariable(value = "login") String login, Model model) {
//        if(!workersRepository.f(login)){
////            if ()
//        return "redirect:/log";
//        }
//            model.addAttribute("title", "Вход");
//        return "redirect:/info";
//    }



    @GetMapping("/edit/{id}")
    public String workerEdit(@PathVariable(value = "id") Long id, Model model) {
        if(!workersRepository.existsById(id)){
            return "redirect:/info";
        }
        Optional<Workers> workers = workersRepository.findById(id);
        ArrayList<Workers> res = new ArrayList<>();
        workers.ifPresent(res::add);
        model.addAttribute("workers", res);
        return "worker-edit";
    }

    @PostMapping("/edit/{id}")
    public String editWorker (@PathVariable (value = "id") Long id, @RequestParam String name, @RequestParam String username,
                              @RequestParam String password, @RequestParam (defaultValue = "false") boolean benefit, @RequestParam Post job_id, Model model) {
       Workers workers = workersRepository.findById(id).orElseThrow();
        if (!postRepository.existsById(job_id.getJob_id())){
            return "redirect:/hrror";
        }
        workers.setName(name);
        workers.setUsername(username);
        workers.setPassword(password);
        workers.setBenefit(benefit);
        workers.setJob(job_id);
        workersRepository.save(workers);
        return "redirect:/info";
    }

    @PostMapping("/delete/{id}")
    public String deleteWorker (@PathVariable (value = "id") Long id, Model model) {
        Workers workers = workersRepository.findById(id).orElseThrow();
        System.out.println(id);
        if (!workersRepository.existsById(id)){
            return "redirect:/hrror";
        }
        workersRepository.delete(workers);
        return "redirect:/info";
    }

    @GetMapping("/choose")
    public String choose(Model model, Model mpdel2) {
        Iterable<Workers> workers = workersRepository.findAll();
        model.addAttribute("workers", workers);
        Iterable<Salary> salaries = salaryRepository.findAll();
        model.addAttribute("salaries", salaries);
        return "choose";
    }

    @GetMapping("/calc/{id}")
    public String calculate(@PathVariable(value = "id") Long id, Model model, Model model2, Model model3, Model model4) {
        Optional<Workers> workers = workersRepository.findById(id);
        ArrayList<Workers> res2 = new ArrayList<>();
        workers.ifPresent(res2::add);
        String postJ = workers.get().getJob();
        Optional<Post> post = postRepository.findByJob(postJ);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        if (!tabelRepository.existsById(workers.get().getTabel())){
            return "redirect:/hrror";
        }
        Long tabelId = workers.get().getTabel();
        Optional<Tabel> tabel = tabelRepository.findById(tabelId);
        ArrayList<Tabel> res1 = new ArrayList<>();
        tabel.ifPresent(res1::add);
        model.addAttribute("post", res);
        model2.addAttribute("tabel", res1);
        model3.addAttribute("workers", res2);

        List<Salary> salaryList = salaryRepository.findAll();
        int i=0;
        int b = salaryList.size();

        while (i<b){
            String s = salaryList.get(i).getWorker_id();
            if (s.equals(workers.get().getName())){
                Salary sal = salaryRepository.findById(salaryList.get(i).getSalary_id()).orElseThrow();
                salaryRepository.delete(sal);
            }
            i++;
        }
            return "calc";
    }

    @PostMapping("/calc/{id}")
    public String calculateW (@PathVariable (value = "id") Long id, @RequestParam int chill, @RequestParam int work_days, @RequestParam int ill,  @RequestParam int overtime, @RequestParam double bonus, Model model){
        Optional<Workers> workers = workersRepository.findById(id);
        ArrayList<Workers> res2 = new ArrayList<>();
        workers.ifPresent(res2::add);
        String postJ = workers.get().getJob();
        Optional<Post> post = postRepository.findByJob(postJ);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        Long tabelId = workers.get().getTabel();
        Optional<Tabel> tabel = tabelRepository.findById(tabelId);
        ArrayList<Tabel> res1 = new ArrayList<>();
        tabel.ifPresent(res1::add);
        if(21-chill-ill<0){
            return "hrror";
        }
        else {
            Tabel tabel1 = tabelRepository.findById(tabelId).orElseThrow();
            tabel1.setChill(chill);
            tabel1.setIll(ill);
            tabel1.setWork_days(work_days);
        }
        int gross1 = post.get().getMoney();
        int day_prise = gross1/21;//в апреле 21 рабочий день
        double worked = day_prise * tabel.get().getWork_days();

        double seaked = 0;
        if(tabel.get().getIll()<12||tabel.get().getIll()>0){
            seaked = day_prise * tabel.get().getIll()*0.8;
        }
        else if(tabel.get().getIll()>12)
        {
            int i = tabel.get().getIll()-12;
            seaked = (day_prise * 12*0.8)+(day_prise * i);
        }
        int over = overtime*2*day_prise;

        double gross = worked+seaked+over+bonus;

        double salar = gross;
        if(!workers.get().isBenefit()){
            double tax = gross * 0.13;
            double pension = gross * 0.01;
            salar = gross - tax - pension;
        }
        if(salar< 457)
            salar = 457;

        double result = Math.round(salar * 100.0) / 100.0;
        Salary salary = new Salary();
        salary.setWorker_id(workers.get());
        salary.setSalary(result);
        salaryRepository.save(salary);
        return "redirect:/choose";
    }

    @GetMapping("/urgent/{id}")
    public String asap(@PathVariable (value = "id") Long id, Model model2, Model model){
        Optional<Workers> workers = workersRepository.findById(id);
        ArrayList<Workers> res = new ArrayList<>();
        workers.ifPresent(res::add);
        Long tabelId = workers.get().getTabel();
        Optional<Tabel> tabel = tabelRepository.findById(tabelId);
        ArrayList<Tabel> res1 = new ArrayList<>();
        tabel.ifPresent(res1::add);
        model.addAttribute("workers", res);
        model2.addAttribute("tabel", res1);
        return "urgent";
    }

    @PostMapping("/urgent/{id}")
    public String asapWork (@PathVariable (value = "id") Long id, @RequestParam int minus, Model model) {
        Workers workers = workersRepository.findById(id).orElseThrow();
        Long tId = workers.getTabel();
        if (!tabelRepository.existsById(workers.getTabel())){
            return "redirect:/hrror";
        }
        Tabel tabel = tabelRepository.findById(tId).orElseThrow();
        int ch = tabel.getChill();
        int resCh = ch - minus;
        tabel.setChill(resCh);

        tabelRepository.save(tabel);
        return "redirect:/calc/{id}";
    }
}
