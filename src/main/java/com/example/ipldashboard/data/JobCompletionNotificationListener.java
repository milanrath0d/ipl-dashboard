package com.example.ipldashboard.data;

import com.example.ipldashboard.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Milan Rathod
 */
@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final EntityManager entityManager;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            final Map<String, Team> teamMap = entityManager
                .createQuery("SELECT m.team1, count(*) from Match m group by m.team1", Object[].class)
                .getResultList()
                .stream()
                .map(object -> Team.builder()
                    .teamName((String) object[0])
                    .totalMatches((long) object[1]).build())
                .collect(Collectors.toMap(Team::getTeamName, team -> team));

            entityManager.createQuery("SELECT m.team2, count(*) from Match m group by m.team2", Object[].class)
                .getResultList()
                .forEach(object -> {
                    Team team = teamMap.get((String) object[0]);
                    team.setTotalMatches(team.getTotalMatches() + (long) object[1]);
                });

            entityManager.createQuery("SELECT m.winner, count(*) from Match m group by m.winner", Object[].class)
                .getResultList()
                .forEach(object -> {
                    Team team = teamMap.get((String) object[0]);
                    if (team != null) {
                        team.setTotalWins((Long) object[1]);
                    }
                });

            teamMap.values().forEach(entityManager::persist);
            teamMap.values().forEach(System.out::println);
        }
    }
}
