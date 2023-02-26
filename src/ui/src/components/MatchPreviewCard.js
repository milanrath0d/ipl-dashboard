import {React} from 'react';
import {Link} from "react-router-dom";
import './MatchPreviewCard.scss';

export const MatchPreviewCard = ({teamName, match}) => {
    const otherTeam = match.team1 === teamName ? match.team2 : match.team1;
    const otherTeamRoute = `/teams/${otherTeam}`;
    const isMatchWon = teamName === match.matchWinner;
    return (
        <div className={isMatchWon ? 'MatchPreviewCard won-card' : 'MatchPreviewCard lost-card'}>
            <span className="vs">vs</span>
            <h1><Link to={otherTeamRoute}>{otherTeam}</Link></h1>
            <p className="match-result">{match.matchWinner} won by {match.resultMargin} {match.result} </p>
        </div>
    );
}
