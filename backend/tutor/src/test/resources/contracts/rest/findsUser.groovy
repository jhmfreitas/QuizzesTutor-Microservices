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
        url '/rest/users/find/1'
        """body(file("request.json"))"""
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body(
                id: $(regex(anInteger())),
                username: anyNonEmptyString(),
                email: $(regex(email())),
                name: anyNonEmptyString(),
                role: "STUDENT",
                active: $(regex(anyBoolean())),
                creationDate: anyNonEmptyString(),
                lastAccess: anyNonEmptyString()
        )
        headers {
            contentType(applicationJson())
        }
    }
}