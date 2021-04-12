package pt.ulisboa.tecnico.socialsoftware.tournament.services.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto;
import pt.ulisboa.tecnico.socialsoftware.common.exceptions.RemoteAccessException;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final RestTemplate restTemplate = new RestTemplate();

    public int port = 8080;

    @RequestMapping(method = RequestMethod.GET, value = "/rest/users/find/{userId}", produces = "application/json")
    public UserDto findUser(@PathVariable Integer userId) {
        logger.info("findUser id:{}", userId);
        try {
            UserDto userDto = restTemplate.getForObject("http://localhost:" + this.port +
                    "/rest/users/find/" + userId, UserDto.class);
            logger.info("UserDto: {}", userDto);
            return userDto;
        } catch (HttpClientErrorException e) {
            logger.info(
                    "findUser HttpClientErrorException errorMessage:{}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.info("findUser Exception errorMessage:{}", e.getMessage());
            throw new RemoteAccessException();
        }
    }
}
