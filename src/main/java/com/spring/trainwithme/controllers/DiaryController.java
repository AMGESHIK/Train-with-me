package com.spring.trainwithme.controllers;

import com.spring.trainwithme.domain.Exercise;
import com.spring.trainwithme.domain.Program;
import com.spring.trainwithme.domain.ProgramComposition;
import com.spring.trainwithme.domain.User;
import com.spring.trainwithme.repos.ExerciseRepo;
import com.spring.trainwithme.service.ProgramService;
import com.spring.trainwithme.service.impl.ProgramCompositionServiceImpl;
import com.spring.trainwithme.service.impl.ProgramServiceImpl;
import com.spring.trainwithme.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private ProgramServiceImpl programService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProgramCompositionServiceImpl programCompositionService;

    @Autowired
    private ExerciseRepo exerciseRepo;

    Long globProgramId;

    @GetMapping
    public String training(@RequestParam(required = false) Long programId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User userFromDb = userService.loadUserByUsername(auth.getName());
        if (userFromDb != null) {
            List<Program> program = programService.getByUserId(userFromDb.getId());
            model.addAttribute("program", program);
        }
        model.addAttribute("username", auth.getName());
        if(programId!=null){
            globProgramId=programId;
            return "redirect:/diary/programComposition";
        }
        return "diaryMain";
    }

    @GetMapping("/addProgram")
    public String programConstructor(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());

        return "addProgram";
    }

    @PostMapping("/addProgram")
    public String addProgram(@ModelAttribute Program program) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromDb = userService.loadUserByUsername(auth.getName());

        program.setPhotoURL("/img/card-photo-1.png");
        program.setUser(userFromDb);
        programService.save(program);

        return "redirect:/diary/addExercises";
    }

    @GetMapping("/addExercises")
    public String programConstructorExercises(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromDb = userService.loadUserByUsername(auth.getName());
        Program lastAddedProgram = programService.getLastAddedProgram(userFromDb.getId());
        model.addAttribute("username", auth.getName());
        Set<String> muscles = new LinkedHashSet<>();
        for (Exercise exercise : exerciseRepo.findAll()) {
            muscles.add(exercise.getMuscles());
        }

        model.addAttribute("exercises", exerciseRepo.findAll());
        model.addAttribute("muscles", muscles);
        model.addAttribute("programCompositions",
                programCompositionService.getProgramsCompositionOfThisProgram(lastAddedProgram));

        return "addExercises";
    }

    @PostMapping("/addExercises")
    public String addExercises(@ModelAttribute ProgramComposition programComposition) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromDb = userService.loadUserByUsername(auth.getName());
        Program lastAddedProgram = programService.getLastAddedProgram(userFromDb.getId());

        programComposition.setNumberTraining(1);
        programComposition.setProgram(lastAddedProgram);
        programCompositionService.save(programComposition);

        return "redirect:/diary/addExercises";
    }

    @GetMapping("/programComposition")
    public String programComposition(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Program program = programService.getById(globProgramId);
        model.addAttribute("username", auth.getName());
        model.addAttribute("programCompositions",
                programCompositionService.getProgramsCompositionOfThisProgram(program));
        model.addAttribute("program", program);

        return "programComposition";
    }


}
