package pt.ulisboa.tecnico.socialsoftware.tournament.config;

import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pt.ulisboa.tecnico.socialsoftware.tournament.sagas.createTournament.CreateTournamentSaga;
import pt.ulisboa.tecnico.socialsoftware.tournament.sagas.participants.*;
import pt.ulisboa.tecnico.socialsoftware.tournament.sagas.removeTournament.RemoveTournamentSaga;
import pt.ulisboa.tecnico.socialsoftware.tournament.sagas.updateTournament.UpdateTournamentSaga;

/**
 * The configuration class to instantiate and wire the domain service class.
 */
@Configuration
@Import({SagaOrchestratorConfiguration.class, TramMessageProducerJdbcConfiguration.class,
        EventuateTramKafkaMessageConsumerConfiguration.class})
public class TournamentServiceOrchestratorConfiguration {

    @Bean
    public TournamentServiceProxy tournamentServiceProxy() {
        return new TournamentServiceProxy();
    }

    @Bean
    public QuizServiceProxy quizServiceProxy() {
        return new QuizServiceProxy();
    }

    @Bean
    public AnswerServiceProxy answerServiceProxy() {
        return new AnswerServiceProxy();
    }

    @Bean
    public CourseExecutionServiceProxy courseExecutionServiceProxy() {
        return new CourseExecutionServiceProxy();
    }

    @Bean
    public QuestionServiceProxy questionServiceProxy() {
        return new QuestionServiceProxy();
    }

    @Bean
    public RemoveTournamentSaga confirmRegistrationSaga(TournamentServiceProxy tournamentService,
                                                        QuizServiceProxy quizService) {
        return new RemoveTournamentSaga(tournamentService, quizService);
    }

    @Bean
    public CreateTournamentSaga createTournamentSaga(TournamentServiceProxy tournamentService,
                                                     AnswerServiceProxy answerService,
                                                     CourseExecutionServiceProxy executionService,
                                                     QuestionServiceProxy questionService) {
        return new CreateTournamentSaga(tournamentService, answerService, executionService, questionService);
    }

    @Bean
    public UpdateTournamentSaga updateTournamentSaga(TournamentServiceProxy tournamentService,
                                                        QuizServiceProxy quizService,
                                                        QuestionServiceProxy questionService) {
        return new UpdateTournamentSaga(tournamentService, quizService, questionService);
    }
}
