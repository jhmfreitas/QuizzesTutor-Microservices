package pt.ulisboa.tecnico.socialsoftware.tournament.services.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.answer.StatementQuizDto;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.ExternalStatementCreationDto;
import pt.ulisboa.tecnico.socialsoftware.common.remote.AnswerInterface;

@RestController
@RequestMapping(value = "/rest/answer")
public class AnswerController {

    @Autowired
    AnswerInterface answerInterface;

    @RequestMapping(value = "/generateQuiz/{courseExecutionId}/{creatorId}", method = RequestMethod.GET)
    public Integer generateQuizAndGetId(@PathVariable Integer courseExecutionId, @PathVariable Integer creatorId,
                                                        @RequestBody ExternalStatementCreationDto quizDetails) {
        return answerInterface.generateQuizAndGetId(creatorId, courseExecutionId, quizDetails);
    }

    @RequestMapping(value = "/startQuiz/{quizId}/{userId}", method = RequestMethod.GET)
    public StatementQuizDto startQuiz(@PathVariable Integer quizId, @PathVariable Integer userId) {
        return answerInterface.startQuiz(userId, quizId);
    }
}
