package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Topic;

import java.io.Serializable;

public class TopicWithCourseDto implements Serializable {
    private Integer id;
    private String name;
    private Integer courseId;

    public TopicWithCourseDto(Topic topic) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.courseId = topic.getCourse().getId();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return "TopicWithCourseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courseId=" + courseId +
                '}';
    }
}
