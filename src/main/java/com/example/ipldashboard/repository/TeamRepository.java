package com.example.ipldashboard.repository;

import com.example.ipldashboard.model.Team;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Milan Rathod
 */
public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByTeamName(String teamName);
}
