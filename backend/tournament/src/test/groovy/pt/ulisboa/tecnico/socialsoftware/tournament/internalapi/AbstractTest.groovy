package pt.ulisboa.tecnico.socialsoftware.tournament.internalapi

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.json.JacksonTester
import org.springframework.context.annotation.Bean
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.TournamentRequiredService
import spock.lang.Specification;

class AbstractTest extends Specification{

    public JacksonTester<UserDto> json;


    void setup() {
        ObjectMapper objectMappper = new ObjectMapper();
        // Possibly configure the mapper
        JacksonTester.initFields(this, objectMappper);
    }

}
