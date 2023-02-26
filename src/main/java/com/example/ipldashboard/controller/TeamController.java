package com.example.ipldashboard.controller;

import com.example.ipldashboard.model.Match;
import com.example.ipldashboard.model.Team;
import com.example.ipldashboard.repository.MatchRepository;
import com.example.ipldashboard.repository.TeamRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Milan Rathod
 */
@RestController
@CrossOrigin
public class TeamController {

    private final TeamRepository teamRepository;

    private final MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/team")
    public Iterable<Team> getAllTeam() {
        return teamRepository.findAll();
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Pageable pageable = PageRequest.of(0, 4);

        final List<Match> matches = matchRepository
            .getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable);

        final Team team = teamRepository.findByTeamName(teamName);

        team.setMatches(matches);

        return team;
    }

    @GetMapping("/team/{teamName}/matches")
    public List<Match> getTeam(@PathVariable String teamName, @RequestParam int year) {
        return matchRepository.getByMatchBetweenDates(teamName, year);
    }
}
