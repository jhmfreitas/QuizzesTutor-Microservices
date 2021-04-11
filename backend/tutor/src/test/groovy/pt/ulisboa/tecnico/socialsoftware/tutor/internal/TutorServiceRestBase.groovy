package pt.ulisboa.tecnico.socialsoftware.tutor.internal

import io.restassured.config.EncoderConfig
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto
import pt.ulisboa.tecnico.socialsoftware.tutor.TutorServiceApplication
import pt.ulisboa.tecnico.socialsoftware.tutor.api.MonolithService
import pt.ulisboa.tecnico.socialsoftware.tutor.api.UserInternalController
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity

@SpringBootTest(classes = [TutorServiceApplication.class , Config.class] ,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
abstract class TutorServiceRestBase extends Specification {

    @Autowired
    WebApplicationContext webApplicationContext

    def setup() {
        RestAssuredMockMvc.webAppContextSetup(this.webApplicationContext)

        EncoderConfig encoderConfig = new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssuredMockMvc.config = new RestAssuredMockMvcConfig().encoderConfig(encoderConfig);

        MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(springSecurity())
                .build()
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

   /* @TestConfiguration
    static class LocalBeanConfiguration extends ApiTestBase {}*/
}
