package pt.ulisboa.tecnico.socialsoftware.tutor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto;

@RestController
@RequestMapping(value = "/rest/users")
public class UserInternalController {
    private static final Logger logger = LoggerFactory.getLogger(UserInternalController.class);

    @Autowired
    MonolithService monolithService;

    public UserInternalController(MonolithService monolithService) {
        this.monolithService = monolithService;
    }

    @RequestMapping(value = "/find/{userId}", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<UserDto> findUser(@PathVariable Integer userId) {
        logger.info("findUser id:{}", userId);
        try {
            UserDto result = monolithService.findUser(userId);
            logger.info("Result:{}", result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception:{}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
