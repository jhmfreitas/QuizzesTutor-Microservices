package pt.ulisboa.tecnico.socialsoftware.tutor.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.socialsoftware.dtos.question.QuestionDto;
import pt.ulisboa.tecnico.socialsoftware.dtos.question.TopicDto;
import pt.ulisboa.tecnico.socialsoftware.dtos.tournament.TopicWithCourseDto;
import pt.ulisboa.tecnico.socialsoftware.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.CourseExecutionService;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.TopicsXmlExport;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.TopicsXmlImport;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Course;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Topic;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.repository.CourseRepository;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.repository.TopicRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pt.ulisboa.tecnico.socialsoftware.exceptions.ErrorMessage.*;

@Service
public class TopicService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CourseExecutionService courseExecutionService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Retryable(
      value = { SQLException.class },
      backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<TopicDto> findTopics(int courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new TutorException(COURSE_NOT_FOUND, courseId));
        return topicRepository.findTopics(course.getId()).stream().sorted(Comparator.comparing(Topic::getName)).map(Topic::getDto).collect(Collectors.toList());
    }

    @Retryable(
      value = { SQLException.class },
      backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TopicDto createTopic(int courseId, TopicDto topicDto) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new TutorException(COURSE_NOT_FOUND, courseId));

        if (topicRepository.findTopicByName(course.getId(), topicDto.getName()) != null) {
            throw new TutorException(DUPLICATE_TOPIC, topicDto.getName());
        }

        Topic topic = new Topic(course, topicDto);
        topicRepository.save(topic);
        return topic.getDto();
    }

    @Retryable(
            value = { SQLException.class },
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<QuestionDto> getTopicQuestions(Integer topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new TutorException(TOPIC_NOT_FOUND, topicId));

        return topic.getQuestions().stream().map(Question::getDto).collect(Collectors.toList());
    }

    @Retryable(
      value = { SQLException.class },
      backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TopicDto updateTopic(Integer topicId, TopicDto topicDto) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new TutorException(TOPIC_NOT_FOUND, topicId));

        topic.setName(topicDto.getName());
        return topic.getDto();
    }

    @Retryable(
      value = { SQLException.class },
      backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void removeTopic(Integer topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TutorException(TOPIC_NOT_FOUND, topicId));

        topic.remove();
        topicRepository.delete(topic);
    }

    @Retryable(
      value = { SQLException.class },
      backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String exportTopics() {
        TopicsXmlExport xmlExport = new TopicsXmlExport();

        return xmlExport.export(topicRepository.findAll());
    }

    @Retryable(
      value = { SQLException.class },
      backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void importTopics(String topicsXML) {
        TopicsXmlImport xmlImporter = new TopicsXmlImport();

        xmlImporter.importTopics(topicsXML, this, questionService, courseRepository);
    }

    @Retryable(
            value = { SQLException.class },
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void resetDemoTopics() {
        this.topicRepository.findTopics(courseExecutionService.getDemoCourse().getCourseId())
                .stream()
                .skip(5)
                .forEach(topic -> {
                    topic.remove();
                    this.topicRepository.delete(topic);
                });
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<TopicWithCourseDto> findTopicById(Set<Integer> topicsList) {
        List<TopicWithCourseDto> topicWithCourseDtoList = new ArrayList<>();
        for (Integer topicId : topicsList) {
            TopicWithCourseDto topicWithCourseDto = topicRepository.findById(topicId).map(Topic::getTopicWithCourseDto)
                    .orElseThrow(() -> new TutorException(TOPIC_NOT_FOUND, topicId));
            topicWithCourseDtoList.add(topicWithCourseDto);
        }
        return topicWithCourseDtoList;
    }
}

