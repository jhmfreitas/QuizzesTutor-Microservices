package pt.ulisboa.tecnico.socialsoftware.tournament.services.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto;
import pt.ulisboa.tecnico.socialsoftware.common.remote.UserInterface;

@RestController
@RequestMapping(value = "/rest/users")
public class UserController {

    @Autowired
    private UserInterface userInterface;

    @RequestMapping(method = RequestMethod.GET, value = "/find/{userId}", produces = "application/json")
    public UserDto findUser(@PathVariable Integer userId) {
        return userInterface.findUser(userId);
    }
}
