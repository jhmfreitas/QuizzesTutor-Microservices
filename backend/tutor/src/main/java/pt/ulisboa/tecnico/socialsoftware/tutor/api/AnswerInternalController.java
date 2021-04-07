package pt.ulisboa.tecnico.socialsoftware.tutor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.answer.StatementQuizDto;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.ExternalStatementCreationDto;

@RestController
@RequestMapping(value = "/rest/answer")
public class AnswerInternalController {
    private static final Logger logger = LoggerFactory.getLogger(AnswerInternalController.class);

    @Autowired
    MonolithService monolithService;

    @RequestMapping(value = "/generateQuiz/{courseExecutionId}/{creatorId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> generateQuizAndGetId(@PathVariable Integer courseExecutionId, @PathVariable Integer creatorId,
                                                        @RequestBody ExternalStatementCreationDto quizDetails) {
        logger.info("generateQuizAndGetId quizDetails:{}", quizDetails);
        try {
            Integer result = monolithService.generateQuizAndGetId(creatorId, courseExecutionId, quizDetails);
            logger.info("Result:{}", result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception:{}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/startQuiz/{quizId}/{userId}", method = RequestMethod.GET)
    public ResponseEntity<StatementQuizDto> startQuiz(@PathVariable Integer quizId, @PathVariable Integer userId) {
        logger.info("startQuiz quizId: {} userId:{}", quizId, userId);
        try {
            StatementQuizDto result = monolithService.startQuiz(userId, quizId);
            logger.info("Result:{}", result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception:{}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
