package pt.ulisboa.tecnico.socialsoftware.tutor.internal;

import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User;

class BaseUtils {

    public static Integer RANDOM_INTEGER = new Random().nextInt()

    public static final UserDto userDto = new UserDto(
            id: RANDOM_INTEGER,
            name: "name",
            username:""
    )
}
