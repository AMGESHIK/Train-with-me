package com.spring.trainwithme.repos;

import com.spring.trainwithme.domain.Program;
import com.spring.trainwithme.domain.ProgramComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramCompositionRepo extends JpaRepository<ProgramComposition, Long> {
    List<ProgramComposition> findProgramCompositionsByProgramOrderById(Program program);
}
