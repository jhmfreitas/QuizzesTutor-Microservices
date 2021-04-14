package pt.ulisboa.tecnico.socialsoftware.tutor.internal

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.web.FilterChainProxy
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pt.ulisboa.tecnico.socialsoftware.common.dtos.answer.StatementQuizDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.execution.CourseExecutionDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.ExternalStatementCreationDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.FindTopicsDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TopicListDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto
import pt.ulisboa.tecnico.socialsoftware.tutor.TutorServiceApplication
import pt.ulisboa.tecnico.socialsoftware.tutor.api.AnswerInternalController
import pt.ulisboa.tecnico.socialsoftware.tutor.api.CourseExecutionInternalController
import pt.ulisboa.tecnico.socialsoftware.tutor.api.MonolithService
import pt.ulisboa.tecnico.socialsoftware.tutor.api.QuestionInternalController
import pt.ulisboa.tecnico.socialsoftware.tutor.api.QuizInternalController
import pt.ulisboa.tecnico.socialsoftware.tutor.api.UserInternalController
import spock.lang.Specification

@SpringBootTest(classes = [TutorServiceApplication.class , Config.class] ,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
abstract class TutorServiceRestBase extends Specification {

    @Autowired
    WebApplicationContext webApplicationContext

    @Autowired
    private FilterChainProxy springSecurityFilterChain;


    def setup() {
        RestAssuredMockMvc
                .standaloneSetup(MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                        .addFilter(this.springSecurityFilterChain))
    }


    @Configuration
    static class Config extends ApiTestBase {

        @Bean
        @Primary
        MonolithService monolithService() {
            return new MonolithService() {
                @Override
                UserDto findUser(Integer userId) {
                    return getUserDtoTest()
                }

                @Override
                CourseExecutionDto findCourseExecution(Integer courseExecutionId) {
                    return getCourseExecutionDtoTest()
                }

                @Override
                Integer getDemoCourseExecutionId() {
                    return getDemoCourseExecutionIdTest()
                }

                @Override
                Integer generateQuizAndGetId(Integer creatorId, Integer courseExecutionId, ExternalStatementCreationDto quizDetails) {
                    return getQuizIdTest()
                }

                @Override
                StatementQuizDto startQuiz(Integer userId, Integer quizId) {
                    return getStatementQuizDtoTest()
                }

                @Override
                QuizDto findQuizById(Integer quizId) {
                    return getQuizDtoTest()
                }

                @Override
                void updateQuiz(QuizDto quizDto) {
                }

                @Override
                void deleteExternalQuiz(Integer quizId) {
                }
            }
        }

        @Bean
        @Primary
        UserInternalController userInternalController() {
            return new UserInternalController(monolithService())
        }

        @Bean
        @Primary
        QuestionInternalController questionInternalController() {
            return new QuestionInternalController(monolithService())
        }

        @Bean
        @Primary
        QuizInternalController quizInternalController() {
            return new QuizInternalController(monolithService())
        }

        @Bean
        @Primary
        CourseExecutionInternalController courseExecutionInternalController() {
            return new CourseExecutionInternalController(monolithService())
        }

        @Bean
        @Primary
        AnswerInternalController answerInternalController() {
            return new AnswerInternalController(monolithService())
        }
    }
}