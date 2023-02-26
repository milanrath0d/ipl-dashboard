package com.example.ipldashboard.data;

import com.example.ipldashboard.model.Match;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

/**
 * @author Milan Rathod
 */
public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    @Override
    public Match process(MatchInput matchInput) {
        Match match = new Match();

        match.setId(Long.valueOf(matchInput.getId()));
        match.setCity(matchInput.getCity());
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setVenue(matchInput.getVenue());

        String firstInningsTeam, secondInningsTeam;
        if ("bat".equals(matchInput.getToss_decision())) {
            firstInningsTeam = matchInput.getToss_winner();
            secondInningsTeam = firstInningsTeam.equals(matchInput.getTeam1()) ?
                matchInput.getTeam2() : matchInput.getTeam1();
        } else {
            secondInningsTeam = matchInput.getToss_winner();
            firstInningsTeam = secondInningsTeam.equals(matchInput.getTeam1()) ?
                matchInput.getTeam2() : matchInput.getTeam1();
        }

        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);
        match.setWinner(matchInput.getWinner());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire1(matchInput.getUmpire2());

        return match;
    }
}
