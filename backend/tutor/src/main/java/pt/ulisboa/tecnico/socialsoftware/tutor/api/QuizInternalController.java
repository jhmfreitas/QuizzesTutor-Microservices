package pt.ulisboa.tecnico.socialsoftware.tutor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizDto;

@RestController
@RequestMapping(value = "/rest/quiz")
public class QuizInternalController {
    private static final Logger logger = LoggerFactory.getLogger(QuizInternalController.class);

    @Autowired
    MonolithService monolithService;

    @RequestMapping(value = "/find/{quizId}", method = RequestMethod.POST)
    public ResponseEntity<QuizDto> findQuizById(@PathVariable Integer quizId) {
        logger.info("findQuizById quizId:{}", quizId);
        try {
            QuizDto result = monolithService.findQuizById(quizId);
            logger.info("Result:{}", result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception:{}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> updateQuiz(@RequestBody QuizDto quizDto) {
        logger.info("updateQuiz quizDto:{}", quizDto);
        try {
            monolithService.updateQuiz(quizDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception:{}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{quizId}", method = RequestMethod.POST)
    public ResponseEntity<String> deleteExternalQuiz(@PathVariable Integer quizId) {
        logger.info("deleteExternalQuiz quizId:{}", quizId);
        try {
            monolithService.deleteExternalQuiz(quizId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception:{}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
