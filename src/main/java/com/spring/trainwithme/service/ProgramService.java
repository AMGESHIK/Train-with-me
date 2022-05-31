package com.spring.trainwithme.service;

import com.spring.trainwithme.domain.Program;

import java.util.List;

public interface ProgramService {
    List<Program> getByUserId(Long userId);
    Program getLastAddedProgram(Long userId);
    void save(Program program);
    Program getById(Long id);
}
