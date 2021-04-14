package contracts.rest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        Represents a successful scenario of getting demo course execution id
        ```
        given:
            nothing
        when:
            service requests demo course execution info
        then:
            we'll grant him the demo course execution id
        ```
    """)

    request {
        method 'GET'
        url '/rest/execution/demo'
    }
    response {
        status 200
        body(
            1
        )
        headers {
            contentType(applicationJson())
        }
    }
}
