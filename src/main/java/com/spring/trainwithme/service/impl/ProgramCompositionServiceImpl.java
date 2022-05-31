package com.spring.trainwithme.service.impl;

import com.spring.trainwithme.domain.Program;
import com.spring.trainwithme.domain.ProgramComposition;
import com.spring.trainwithme.repos.ProgramCompositionRepo;
import com.spring.trainwithme.service.ProgramCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramCompositionServiceImpl implements ProgramCompositionService {

    @Autowired
    ProgramCompositionRepo programCompositionRepo;

    @Override
    public void save(ProgramComposition programComposition) {
        programCompositionRepo.save(programComposition);
    }

    @Override
    public List<ProgramComposition> getProgramsCompositionOfThisProgram(Program program) {
        return programCompositionRepo.findProgramCompositionsByProgramOrderById(program);
    }
}
