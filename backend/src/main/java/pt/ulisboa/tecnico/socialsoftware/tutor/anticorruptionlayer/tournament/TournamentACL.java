package pt.ulisboa.tecnico.socialsoftware.tutor.anticorruptionlayer;

import org.springframework.stereotype.Component;
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.dto.CourseExecutionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.TopicDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.tournament.domain.TournamentCourseExecution;
import pt.ulisboa.tecnico.socialsoftware.tutor.tournament.domain.TournamentCreator;
import pt.ulisboa.tecnico.socialsoftware.tutor.tournament.domain.TournamentParticipant;
import pt.ulisboa.tecnico.socialsoftware.tutor.tournament.domain.TournamentTopic;
import pt.ulisboa.tecnico.socialsoftware.tutor.user.dto.UserDto;

@Component
public class TournamentACL {

    public TournamentCreator userToTournamentCreator(UserDto user) {
        return new TournamentCreator(user.getId(), user.getUsername(), user.getName());
    }

    public TournamentCourseExecution courseExecutionToTournamentCourseExecution(CourseExecutionDto courseExecution) {
        return new TournamentCourseExecution(courseExecution.getCourseExecutionId(),
                courseExecution.getCourseId(), CourseExecutionStatus.valueOf(courseExecution.getStatus().toString()), courseExecution.getAcronym());
    }

    public TournamentTopic topicToTournamentTopic(TopicDto topic) {
        return new TournamentTopic(topic.getId(), topic.getName(), topic.getCourse().getId());
    }

    public TournamentParticipant userToTournamentParticipant(UserDto user) {
        return new TournamentParticipant(user.getId(), user.getUsername(), user.getName());
    }
}
