package pt.ulisboa.tecnico.socialsoftware.tournament

import com.google.common.eventbus.EventBus
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import pt.ulisboa.tecnico.socialsoftware.tournament.services.local.TournamentProvidedService
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.TournamentRequiredService
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.AnswerService
import pt.ulisboa.tecnico.socialsoftware.tutor.api.MonolithService
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.AuthUserService
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.AssessmentService
import pt.ulisboa.tecnico.socialsoftware.tutor.execution.CourseExecutionService
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.AnswersXmlImport
import pt.ulisboa.tecnico.socialsoftware.tutor.question.QuestionService
import pt.ulisboa.tecnico.socialsoftware.tutor.question.TopicService
import pt.ulisboa.tecnico.socialsoftware.tutor.questionsubmission.QuestionSubmissionService
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.QuizService
import pt.ulisboa.tecnico.socialsoftware.tutor.user.UserService

@TestConfiguration
@PropertySource("classpath:application-test.properties")
class BeanConfiguration {

    @Value('${spring.mail.host}')
    private String host

    @Value('${spring.mail.port}')
    private int port

    @Value('${spring.mail.username}')
    private String username

    @Value('${spring.mail.password}')
    private String password

    @Value('${spring.mail.properties.mail.smtp.auth}')
    private String auth;

    @Value('${spring.mail.properties.mail.smtp.starttls.enable}')
    private String starttls

    @Value('${spring.mail.properties.mail.transport.protocol}')
    private String protocol

    @Value('${spring.mail.properties.mail.debug}')
    private String debug

    @Bean
    QuizService quizService() {
        return new QuizService()
    }

    @Bean
    AnswerService answerService() {
        return new AnswerService()
    }

    @Bean
    AnswersXmlImport answersXmlImport() {
        return new AnswersXmlImport()
    }

    @Bean
    UserService userService() {
        return new UserService()
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }

    @Bean
    QuestionService questionService() {
        return new QuestionService()
    }

    @Bean
    CourseExecutionService courseService() {
        return new CourseExecutionService()
    }

    @Bean
    AuthUserService authUserService() {
        return new AuthUserService()
    }

    @Bean
    TopicService topicService() {
        return new TopicService()
    }

    @Bean
    TournamentProvidedService TournamentProvidedService() {
        return new TournamentProvidedService()
    }

    @Bean
    TournamentRequiredService TournamentRequiredService() {
        return new TournamentRequiredService()
    }

    @Bean
    AssessmentService assessmentService() {
        return new AssessmentService()
    }

    @Bean
    QuestionSubmissionService questionSubmissionService() {
        return new QuestionSubmissionService()
    }

    @Bean
    EventBus eventBus() {
        return new EventBus()
    }

   @Bean
    MonolithService monolithService() {
        return new MonolithService()
    }
}