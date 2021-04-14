package pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament;

import java.io.Serializable;
import java.util.List;

public class FindTopicsDto implements Serializable {

    private List<TopicWithCourseDto> topicWithCourseDtoList;

    public FindTopicsDto(List<TopicWithCourseDto> topicWithCourseDtoList) {
        this.topicWithCourseDtoList = topicWithCourseDtoList;
    }

    public List<TopicWithCourseDto> getTopicWithCourseDtoList() {
        return topicWithCourseDtoList;
    }
}
