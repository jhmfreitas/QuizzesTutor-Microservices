package pt.ulisboa.tecnico.socialsoftware.tutor.internal

import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto
import pt.ulisboa.tecnico.socialsoftware.common.utils.CloudContractUtils
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest

class ApiTestBase {

    def getUserDtoTest() {
        UserDto dto = new UserDto()
        dto.setId(CloudContractUtils.RANDOM_INTEGER)
        dto.setUsername(SpockTest.USER_1_USERNAME)
        dto.setEmail(SpockTest.USER_1_EMAIL)
        dto.setName(SpockTest.USER_1_NAME)
        dto.setRole("STUDENT")
        dto.setActive(true)
        dto.setCreationDate("2021-04-10T15:34:10.830873Z")
        dto.setLastAccess("2021-04-10T15:34:10.830873Z")
        return dto
    }
}
