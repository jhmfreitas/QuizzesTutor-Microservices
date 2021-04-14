package pt.ulisboa.tecnico.socialsoftware.tournament.internalapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pt.ulisboa.tecnico.socialsoftware.common.dtos.question.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.question.TopicDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizType
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.ExternalStatementCreationDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.AnswerController
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.CourseExecutionController
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.QuestionController
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.QuizController
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.UserController
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "pt.ulisboa.tecnico.socialsoftware.tutor:tutor:+:stubs:8080")
@DirtiesContext
@WithMockUser
@ActiveProfiles("test")
class ApiIntegrationTest extends AbstractTest {

    @Autowired
    UserController userController

    @Autowired
    AnswerController answerController

    @Autowired
    CourseExecutionController courseExecutionController

    @Autowired
    QuestionController questionController

    @Autowired
    QuizController quizController

    def 'stubs are installed'() {
        when:
        stubFinder.findStubUrl(TUTOR_GROUP_ID, 'tutor')
        then:
        noExceptionThrown()
        and:
        stubFinder.findAllRunningStubs().isPresent('tutor')
    }

    def 'find user operation succeeds'() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/users/find/1"))
                .andExpect(status().isOk())
                //.andExpect(content().json("{\"id\":1,\"username\":\"a@a.a\",\"email\":\"user1@mail.com\",\"name\":\"User 1 Name\",\"role\":\"STUDENT\",\"active\":true,\"creationDate\":\"2021-04-10T15:34:10.830873Z\",\"lastAccess\":\"2021-04-10T15:34:10.830873Z\"}"))
                .andReturn()
    }

    /*def 'generate quiz and get id succeeds'() {
        given:
        ExternalStatementCreationDto dto = new ExternalStatementCreationDto()
        dto.setId(1)
        dto.setStartTime(LocalDateTime.now())
        dto.setEndTime(LocalDateTime.now())
        dto.setNumberOfQuestions(2)
        dto.setTopics(new HashSet<TopicDto>())

        TopicDto topicDto1 = new TopicDto()
        topicDto1.setId(1)
        topicDto1.setName("Topic 1 Name")
        topicDto1.setNumberOfQuestions(1)

        TopicDto topicDto2 = new TopicDto()
        topicDto2.setId(2)
        topicDto2.setName("Topic 2 Name")
        topicDto2.setNumberOfQuestions(2)

        dto.getTopics().add(topicDto1)
        dto.getTopics().add(topicDto2)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/answer/generateQuiz/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.externalStatementCreationDtoJson.write(dto).getJson()))
                .andExpect(status().isOk())
        //.andExpect(content().json("{\"id\":1,\"username\":\"a@a.a\",\"email\":\"user1@mail.com\",\"name\":\"User 1 Name\",\"role\":\"STUDENT\",\"active\":true,\"creationDate\":\"2021-04-10T15:34:10.830873Z\",\"lastAccess\":\"2021-04-10T15:34:10.830873Z\"}"))
    }*/

    def 'start quiz succeeds'() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/answer/startQuiz/1/1"))
                .andExpect(status().isOk())
        //.andExpect(content().json("{\"id\":1,\"username\":\"a@a.a\",\"email\":\"user1@mail.com\",\"name\":\"User 1 Name\",\"role\":\"STUDENT\",\"active\":true,\"creationDate\":\"2021-04-10T15:34:10.830873Z\",\"lastAccess\":\"2021-04-10T15:34:10.830873Z\"}"))
                .andReturn()
    }

    def 'find course execution succeeds'() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/execution/find/1"))
                .andExpect(status().isOk())
        //.andExpect(content().json("{\"id\":1,\"username\":\"a@a.a\",\"email\":\"user1@mail.com\",\"name\":\"User 1 Name\",\"role\":\"STUDENT\",\"active\":true,\"creationDate\":\"2021-04-10T15:34:10.830873Z\",\"lastAccess\":\"2021-04-10T15:34:10.830873Z\"}"))
                .andReturn()
    }

    def 'find quiz succeeds'() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz/find/1"))
                .andExpect(status().isOk())
        //.andExpect(content().json("{\"id\":1,\"username\":\"a@a.a\",\"email\":\"user1@mail.com\",\"name\":\"User 1 Name\",\"role\":\"STUDENT\",\"active\":true,\"creationDate\":\"2021-04-10T15:34:10.830873Z\",\"lastAccess\":\"2021-04-10T15:34:10.830873Z\"}"))
                .andReturn()
    }

    /*def 'delete quiz succeeds'() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/quiz/delete/1"))
                .andExpect(status().isOk())
        //.andExpect(content().json("{\"id\":1,\"username\":\"a@a.a\",\"email\":\"user1@mail.com\",\"name\":\"User 1 Name\",\"role\":\"STUDENT\",\"active\":true,\"creationDate\":\"2021-04-10T15:34:10.830873Z\",\"lastAccess\":\"2021-04-10T15:34:10.830873Z\"}"))
                .andReturn()
    }*/

    /*def 'update quiz succeeds'() {
        given:
        QuizDto dto = new QuizDto()
        dto.setId(1);
        dto.setKey(1);
        dto.setScramble(true);
        dto.setQrCodeOnly(false);
        dto.setOneWay(true);
        dto.setTitle("title");
        dto.setTimed(true);
        dto.setType(QuizType.EXTERNAL_QUIZ.toString());
        dto.setSeries(1);
        dto.setVersion("1");
        dto.setNumberOfQuestions(2);
        dto.setNumberOfAnswers(0);
        dto.setCreationDate("2021-04-10T15:34:10.830873Z");
        dto.setAvailableDate("2021-04-11T15:34:10.830873Z");
        dto.setConclusionDate("2021-04-13T15:34:10.830873Z");
        dto.setResultsDate("2021-04-14T15:34:10.830873Z");
        dto.setQuestions(new ArrayList<QuestionDto>())

        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(1)
        questionDto.setKey(1)
        questionDto.setTitle("title")
        questionDto.setContent("content")
        questionDto.setDifficulty(1)
        questionDto.setNumberOfAnswers(0)
        questionDto.setNumberOfGeneratedQuizzes(0)
        questionDto.setNumberOfNonGeneratedQuizzes(0)
        questionDto.setNumberOfCorrect(0)
        questionDto.setCreationDate("2021-04-02T15:34:10.830873Z")
        questionDto.setStatus("AVAILABLE")
        questionDto.setImage(null)
        questionDto.setTopics(null)
        questionDto.setSequence(1)

        QuestionDto questionDto2 = new QuestionDto();
        questionDto2.setId(2)
        questionDto2.setKey(2)
        questionDto2.setTitle("title2")
        questionDto2.setContent("content2")
        questionDto2.setDifficulty(1)
        questionDto2.setNumberOfAnswers(0)
        questionDto2.setNumberOfGeneratedQuizzes(0)
        questionDto2.setNumberOfNonGeneratedQuizzes(0)
        questionDto2.setNumberOfCorrect(0)
        questionDto2.setCreationDate("2021-04-02T15:34:10.830873Z")
        questionDto2.setStatus("AVAILABLE")
        questionDto2.setImage(null)
        questionDto2.setTopics(null)
        questionDto2.setSequence(2)

        dto.getQuestions().add(questionDto)
        dto.getQuestions().add(questionDto2)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/quiz/update").contentType(MediaType.APPLICATION_JSON)
                .content(this.quizJson.write(dto).getJson()))
                .andExpect(status().isOk())
        //.andExpect(content().json("{\"id\":1,\"username\":\"a@a.a\",\"email\":\"user1@mail.com\",\"name\":\"User 1 Name\",\"role\":\"STUDENT\",\"active\":true,\"creationDate\":\"2021-04-10T15:34:10.830873Z\",\"lastAccess\":\"2021-04-10T15:34:10.830873Z\"}"))

    }*/





    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
