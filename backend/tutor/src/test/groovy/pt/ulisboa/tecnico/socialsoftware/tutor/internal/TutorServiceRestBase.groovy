package pt.ulisboa.tecnico.socialsoftware.tutor.internal

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.web.FilterChainProxy
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto
import pt.ulisboa.tecnico.socialsoftware.tutor.TutorServiceApplication
import pt.ulisboa.tecnico.socialsoftware.tutor.api.MonolithService
import pt.ulisboa.tecnico.socialsoftware.tutor.api.UserInternalController
import spock.lang.Specification

@SpringBootTest(classes = [TutorServiceApplication.class , Config.class] ,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
abstract class TutorServiceRestBase extends Specification {

    @Autowired
    WebApplicationContext webApplicationContext

    @Autowired
    private FilterChainProxy springSecurityFilterChain;


    def setup() {
        RestAssuredMockMvc
                .standaloneSetup(MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                        .addFilter(this.springSecurityFilterChain))
    }


    @Configuration
    static class Config extends ApiTestBase {

        @Bean
        @Primary
        MonolithService monolithService() {
            return new MonolithService() {
                @Override
                UserDto findUser(Integer userId) {
                    return getUserDtoTest()
                }
            }
        }

        @Bean
        @Primary
        UserInternalController userInternalController() {
            return new UserInternalController(monolithService())
        }
    }
}