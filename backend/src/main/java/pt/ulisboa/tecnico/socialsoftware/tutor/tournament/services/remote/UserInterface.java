package pt.ulisboa.tecnico.socialsoftware.tutor.tournament.services;

import pt.ulisboa.tecnico.socialsoftware.tutor.user.dto.UserDto;

public interface UserInterface {

    UserDto getUserInfo(Integer userId);
}
