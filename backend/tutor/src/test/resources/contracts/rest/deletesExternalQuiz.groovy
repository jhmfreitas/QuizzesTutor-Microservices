package contracts.rest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        Represents a successful scenario of deleting an external quiz
        ```
        given:
            service needs quiz id
        when:
            service sends quiz id
        then:
            we'll delete the quiz
        ```
    """)

    request {
        method 'POST'
        url '/rest/quiz/delete/1'
    }
    response {
        status 200
    }
}
