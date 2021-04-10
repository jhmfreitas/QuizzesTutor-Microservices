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
        url '/rest/users/search/1'
        """body(file("request.json"))"""
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body(
                id: 1,
                username: "username",
                email: "user@test.com",
                name: "name",
                role: "Student",
                active: true,
                creationDate: "2020-02-03",
                lastAccess: "2021-02-03"
        )
        headers {
            contentType(applicationJson())
        }
    }
}