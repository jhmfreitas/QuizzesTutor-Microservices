package pt.ulisboa.tecnico.socialsoftware.tournament.internalapi

import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.contract.stubrunner.StubFinder
import org.springframework.cloud.contract.stubrunner.StubNotFoundException
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tournament.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tournament.WebConfiguration
import spock.lang.Specification
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.MockitoAnnotations;

@WithMockUser("spring")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(ids = "pt.ulisboa.tecnico.socialsoftware.tutor:tutor:+:stubs:8080",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@DirtiesContext
@ContextConfiguration(classes = WebConfiguration.class)
@WebAppConfiguration
@EnableWebMvc
class UserServiceApiTest extends Specification {

    @Autowired
    MockMvc mockMvc;

    @Autowired StubFinder stubFinder

    def 'first'() {
        when:
        stubFinder.findStubUrl("pt.ulisboa.tecnico.socialsoftware.tutor", 'tutor')
        then:
        noExceptionThrown()

        and:
        stubFinder.findAllRunningStubs().isPresent('tutor')

        /*and:
        stubFinder.findAllRunningStubs().toString() == ""*/
    }

    def 'test' () {

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/users/find/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(content().string("Even"))

    }

    def 'test2' () {

        expect:

        mockMvc.perform(MockMvcRequestBuilders.get("/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
    }

    def 'test3' () {

        expect:

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/users/search/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
    }
}
