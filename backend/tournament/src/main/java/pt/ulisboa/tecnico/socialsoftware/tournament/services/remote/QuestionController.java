package pt.ulisboa.tecnico.socialsoftware.tournament.services.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.FindTopicsDto;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TopicListDto;
import pt.ulisboa.tecnico.socialsoftware.common.remote.QuestionInterface;

@RestController
@RequestMapping(value = "/rest/questions")
public class QuestionController {

    @Autowired
    private QuestionInterface questionInterface;

    @RequestMapping(method = RequestMethod.POST, value = "/findTopics", consumes = "application/json",
            produces = "application/json")
    public FindTopicsDto findTopics(@RequestBody TopicListDto topicListDto) {
        return questionInterface.findTopics(topicListDto);
    }
}
