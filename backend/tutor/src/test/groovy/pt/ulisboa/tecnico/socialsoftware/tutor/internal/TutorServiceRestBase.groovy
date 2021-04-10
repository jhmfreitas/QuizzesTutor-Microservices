package pt.ulisboa.tecnico.socialsoftware.tutor.internal;

import io.restassured.config.EncoderConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.web.FilterChainProxy
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.api.MonolithService;
import pt.ulisboa.tecnico.socialsoftware.tutor.api.UserInternalController;
import spock.lang.Specification;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
@ActiveProfiles("dev")
abstract class TutorServiceRestBase extends Specification {

    @Autowired
    WebApplicationContext webApplicationContext

    /*@Autowired
    MockMvc mockMvc;*/

    MonolithService monolithService = Mock(MonolithService)
    UserInternalController userInternalController = new UserInternalController(monolithService)

    def setup() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext)

        monolithService.findUser(_ as Integer) >> UserDto

        EncoderConfig encoderConfig = new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssuredMockMvc.config = new RestAssuredMockMvcConfig().encoderConfig(encoderConfig);

        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)

        //RestAssuredMockMvc.mockMvc(mockMvc)
        //EncoderConfig encoderConfig = new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false);
        //RestAssuredMockMvc.config = new RestAssuredMockMvcConfig().encoderConfig(encoderConfig);
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();


        /*RestAssuredMockMvc.standaloneSetup(userInternalController).apply(SecurityMockMvcConfigurers.springSecurity())
                .build();*/
    }
}
