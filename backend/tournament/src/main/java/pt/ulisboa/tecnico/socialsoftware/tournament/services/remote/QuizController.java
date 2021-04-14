package pt.ulisboa.tecnico.socialsoftware.tournament.services.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizDto;
import pt.ulisboa.tecnico.socialsoftware.common.remote.QuizInterface;

@RestController
@RequestMapping(value = "/rest/quiz")
public class QuizController {

    @Autowired
    QuizInterface quizInterface;

    @RequestMapping(value = "/find/{quizId}", method = RequestMethod.GET)
    public QuizDto findQuizById(@PathVariable Integer quizId) {
        return quizInterface.findQuizById(quizId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateQuiz(@RequestBody QuizDto quizDto) {
        quizInterface.updateQuiz(quizDto);
    }

    @RequestMapping(value = "/delete/{quizId}", method = RequestMethod.POST)
    public void deleteQuiz(@PathVariable Integer quizId) {
        quizInterface.deleteExternalQuiz(quizId);
    }
}
