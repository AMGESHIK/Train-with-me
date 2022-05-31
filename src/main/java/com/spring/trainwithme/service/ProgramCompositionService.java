package com.spring.trainwithme.service;

import com.spring.trainwithme.domain.Program;
import com.spring.trainwithme.domain.ProgramComposition;

import java.util.List;

public interface ProgramCompositionService {
    void save(ProgramComposition programComposition);
    List<ProgramComposition> getProgramsCompositionOfThisProgram(Program program);
}
