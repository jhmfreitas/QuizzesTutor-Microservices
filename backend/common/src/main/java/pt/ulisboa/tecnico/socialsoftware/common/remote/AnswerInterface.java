package pt.ulisboa.tecnico.socialsoftware.common.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.answer.StatementQuizDto;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.ExternalStatementCreationDto;
import pt.ulisboa.tecnico.socialsoftware.common.exceptions.RemoteAccessException;

public class AnswerInterface implements AnswerContract {
    private static final Logger logger = LoggerFactory.getLogger(AnswerInterface.class);

    private static final String ENDPOINT = "http://localhost:8080";

    @Override
    public Integer generateQuizAndGetId(Integer creatorId, Integer courseExecutionId, ExternalStatementCreationDto quizDetails) {
        logger.info("generateQuizAndGetId creatorId: {} courseExecutionId: {} quizDetails:{}", creatorId, courseExecutionId, quizDetails);
        RestTemplate restTemplate = new RestTemplate();
        try {
            Integer findTopicsDto = restTemplate.postForObject(ENDPOINT + "/rest/answer/generateQuiz/" + courseExecutionId + "/" + creatorId,
                    quizDetails, Integer.class);
            logger.info("generateQuizAndGetId: {}", findTopicsDto);
            return findTopicsDto;
        } catch (HttpClientErrorException e) {
            logger.info("generateQuizAndGetId HttpClientErrorException errorMessage:{}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.info("generateQuizAndGetId Exception errorMessage:{}", e.getMessage());
            throw new RemoteAccessException();
        }
    }

    @Override
    public StatementQuizDto startQuiz(Integer userId, Integer quizId) {
        logger.info("startQuiz userId: {} quizId: {}", userId, quizId);
        RestTemplate restTemplate = new RestTemplate();
        try {
            StatementQuizDto statementQuizDto = restTemplate.postForObject(ENDPOINT + "/rest/answer/startQuiz/" + quizId + "/" + userId,
                    null, StatementQuizDto.class);
            logger.info("startQuiz: {}", statementQuizDto);
            return statementQuizDto;
        } catch (HttpClientErrorException e) {
            logger.info("startQuiz HttpClientErrorException errorMessage:{}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.info("startQuiz Exception errorMessage:{}", e.getMessage());
            throw new RemoteAccessException();
        }
    }
}
