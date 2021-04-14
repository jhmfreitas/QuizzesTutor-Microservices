package pt.ulisboa.tecnico.socialsoftware.tournament.internalapi

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.contract.stubrunner.StubFinder
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.ExternalStatementCreationDto
import spock.lang.Specification

class AbstractTest extends Specification {

    public String TUTOR_GROUP_ID = "pt.ulisboa.tecnico.socialsoftware.tutor"

    @Autowired
    StubFinder stubFinder

    @Autowired
    MockMvc mockMvc;

    public JacksonTester<QuizDto> quizJson;
    public JacksonTester<ExternalStatementCreationDto> externalStatementCreationDtoJson;

    void setup() {
        ObjectMapper objectMappper = new ObjectMapper();
        // Possibly configure the mapper
        JacksonTester.initFields(this, objectMappper);
    }

}
