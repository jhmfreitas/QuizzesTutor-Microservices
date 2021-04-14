package contracts.rest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        Represents a successful scenario of getting a user
        ```
        given:
            service needs user information
        when:
            service sends user id
        then:
            we'll grant him the user information in a userDto
        ```
    """)

    request {
        method 'GET'
        """value(consumer(regex('/foo/[0-9]{5}')))"""
        url '/rest/users/find/1'
    }
    response {
        status 200
        body(
                id: 1,
                username: "a@a.a",
                email: "user1@mail.com",
                name: "User 1 Name",
                role: "STUDENT",
                active: true,
                creationDate: "2021-04-10T15:34:10.830873Z",
                lastAccess: "2021-04-10T15:34:10.830873Z"
        )
        headers {
            contentType(applicationJson())
        }
    }
}