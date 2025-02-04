package pt.ulisboa.tecnico.socialsoftware.tutor.api;

import pt.ulisboa.tecnico.socialsoftware.dtos.tournament.TopicWithCourseDto;

import java.util.List;
import java.util.Set;

public interface TopicInterface {
    List<TopicWithCourseDto> findTopics(Set<Integer> topicsList);
}
