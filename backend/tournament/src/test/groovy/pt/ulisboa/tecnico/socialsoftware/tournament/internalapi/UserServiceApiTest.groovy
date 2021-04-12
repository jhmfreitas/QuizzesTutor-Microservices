package pt.ulisboa.tecnico.socialsoftware.tournament.internalapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.contract.stubrunner.StubFinder
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.UserController

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "pt.ulisboa.tecnico.socialsoftware.tutor:tutor")
@DirtiesContext
@WithMockUser
@ActiveProfiles("test")
class UserServiceApiTest extends AbstractTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserController userController

    @Autowired
    StubFinder stubFinder

    @StubRunnerPort("tutor")
    int producerPort;

    def setup() {
        this.userController.port = this.producerPort;
    }

    def 'stubs are installed'() {
        when:
        stubFinder.findStubUrl("pt.ulisboa.tecnico.socialsoftware.tutor", 'tutor')
        then:
        noExceptionThrown()
        and:
        stubFinder.findAllRunningStubs().isPresent('tutor')
    }

    def 'find user operation succeeds' () {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/users/find/1"))
                .andExpect(status().isOk())
    }


    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
