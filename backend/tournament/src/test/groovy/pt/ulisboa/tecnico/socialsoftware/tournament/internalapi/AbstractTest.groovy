package pt.ulisboa.tecnico.socialsoftware.tournament.internalapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.contract.stubrunner.StubFinder
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

class AbstractTest extends Specification {

    public String TUTOR_GROUP_ID = "pt.ulisboa.tecnico.socialsoftware.tutor"

    @Autowired
    StubFinder stubFinder

    @Autowired
    MockMvc mockMvc;

}
