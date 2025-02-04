package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.dtos.question.TopicDto;
import pt.ulisboa.tecnico.socialsoftware.dtos.tournament.TopicWithCourseDto;
import pt.ulisboa.tecnico.socialsoftware.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.domain.TopicConjunction;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.DomainEntity;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;

import javax.persistence.*;
import java.util.*;

import static pt.ulisboa.tecnico.socialsoftware.exceptions.ErrorMessage.INVALID_NAME_FOR_TOPIC;

@Entity
@Table(name = "topics")
public class Topic implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    private final Set<Question> questions = new HashSet<>();

    @ManyToMany
    private final List<TopicConjunction> topicConjunctions = new ArrayList<>();

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name = "course_id")
    private Course course;

    public Topic() {
    }

    public Topic(Course course, pt.ulisboa.tecnico.socialsoftware.dtos.question.TopicDto topicDto) {
        setName(topicDto.getName());
        setCourse(course);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitTopic(this);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new TutorException(INVALID_NAME_FOR_TOPIC);

        this.name = name;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public List<TopicConjunction> getTopicConjunctions() {
        return topicConjunctions;
    }

    public void addTopicConjunction(TopicConjunction topicConjunction) {
        this.topicConjunctions.add(topicConjunction);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        course.addTopic(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return name.equals(topic.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void remove() {
        course.getTopics().remove(this);
        course = null;

        questions.forEach(question -> question.getTopics().remove(this));
        questions.clear();

        topicConjunctions.forEach(topicConjunction -> topicConjunction.getTopics().remove(this));
        topicConjunctions.clear();
    }

    public TopicWithCourseDto getTopicWithCourseDto() {
        TopicWithCourseDto dto = new TopicWithCourseDto();
        dto.setId(getId());
        dto.setName(getName());
        dto.setCourseId(getCourse().getId());
        return dto;
    }

    public TopicDto getDto() {
        TopicDto dto = new TopicDto();
        dto.setId(getId());
        dto.setName(getName());
        dto.setNumberOfQuestions(getQuestions().size());
        return dto;
    }
}
