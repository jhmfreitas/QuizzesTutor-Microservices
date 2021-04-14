package pt.ulisboa.tecnico.socialsoftware.common.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizDto;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.ExternalStatementCreationDto;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.FindTopicsDto;
import pt.ulisboa.tecnico.socialsoftware.common.exceptions.RemoteAccessException;

public class QuizInterface implements QuizContract {
    private static final Logger logger = LoggerFactory.getLogger(QuizInterface.class);

    private static final String ENDPOINT = "http://localhost:8080";

    @Override
    public QuizDto findQuizById(Integer quizId) {
        logger.info("findQuizById quizId:{}", quizId);
        RestTemplate restTemplate = new RestTemplate();
        try {
            QuizDto quizDto = restTemplate.getForObject(ENDPOINT + "/rest/quiz/find/" + quizId,
                     QuizDto.class);
            logger.info("findQuizById: {}", quizDto);
            return quizDto;
        } catch (HttpClientErrorException e) {
            logger.info("findQuizById HttpClientErrorException errorMessage:{}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.info("findQuizById Exception errorMessage:{}", e.getMessage());
            throw new RemoteAccessException();
        }
    }

    @Override
    public void updateQuiz(QuizDto quizDto) {
        logger.info("updateQuiz quizDto:{}", quizDto);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(ENDPOINT + "/rest/quiz/update", quizDto, String.class);
        } catch (HttpClientErrorException e) {
            logger.info("updateQuiz HttpClientErrorException errorMessage:{}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.info("updateQuiz Exception errorMessage:{}", e.getMessage());
            throw new RemoteAccessException();
        }
    }

    @Override
    public void deleteExternalQuiz(Integer quizId) {
        logger.info("deleteExternalQuiz quizId:{}", quizId);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(ENDPOINT + "/rest/quiz/delete/" + quizId, null, String.class);
        } catch (HttpClientErrorException e) {
            logger.info("deleteExternalQuiz HttpClientErrorException errorMessage:{}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.info("deleteExternalQuiz Exception errorMessage:{}", e.getMessage());
            throw new RemoteAccessException();
        }
    }
}
