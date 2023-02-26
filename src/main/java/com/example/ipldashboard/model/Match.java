package com.example.ipldashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author Milan Rathod
 */
@Entity
@Data
@NoArgsConstructor
public class Match {
    @Id
    private Long id;

    private String city;

    private LocalDate date;

    private String playerOfMatch;

    private String venue;

    private String team1;

    private String team2;

    private String tossWinner;

    private String tossDecision;

    private String winner;

    private String result;

    private String resultMargin;

    private String method;

    private String umpire1;

    private String umpire2;
}
