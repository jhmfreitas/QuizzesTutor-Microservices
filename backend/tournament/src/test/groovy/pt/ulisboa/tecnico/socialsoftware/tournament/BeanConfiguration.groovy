package pt.ulisboa.tecnico.socialsoftware.tournament

import com.google.common.eventbus.EventBus
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import pt.ulisboa.tecnico.socialsoftware.common.remote.AnswerInterface
import pt.ulisboa.tecnico.socialsoftware.common.remote.CourseExecutionInterface
import pt.ulisboa.tecnico.socialsoftware.common.remote.QuestionInterface
import pt.ulisboa.tecnico.socialsoftware.common.remote.QuizInterface
import pt.ulisboa.tecnico.socialsoftware.common.remote.UserInterface
import pt.ulisboa.tecnico.socialsoftware.tournament.services.local.TournamentProvidedService
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.TournamentRequiredService
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.UserController

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
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
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
    EventBus eventBus() {
        return new EventBus()
    }

    @Bean
    UserInterface userInterface() {
        return new UserInterface()
    }

    @Bean
    AnswerInterface answerInterface() {
        return new AnswerInterface()
    }

    @Bean
    CourseExecutionInterface courseExecutionInterface() {
        return new CourseExecutionInterface()
    }

    @Bean
    QuizInterface quizInterface() {
        return new QuizInterface()
    }

    @Bean
    QuestionInterface questionInterface() {
        return new QuestionInterface()
    }


    @Bean
    TournamentRequiredService tournamentRequiredService() {
        return new TournamentRequiredService()
    }

    @Bean
    UserController userController() {
        return new UserController()
    }

}