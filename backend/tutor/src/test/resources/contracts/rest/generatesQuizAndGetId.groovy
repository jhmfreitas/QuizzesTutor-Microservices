package contracts.rest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        Represents a successful scenario of generating a quiz and returning the id
        ```
        given:
            service needs course execution id, creator id and quizDetails
        when:
            service sends course execution id, creator id and quizDetails
        then:
            we'll grant him the generated quiz id
        ```
    """)

    request {
        method 'GET'
        url '/rest/answer/generateQuiz/1/1'
        headers {
            contentType(applicationJson())
        }
        body(
                id: 1,
                startTime: anyDateTime(),
                endTime: anyDateTime(),
                numberOfQuestions: anyPositiveInt(),
                topics: [
                            [
                                    id: anyPositiveInt(),
                                    name: "Topic 1 Name",
                                    numberOfQuestions: anyPositiveInt()
                            ],
                            [
                                    id: anyPositiveInt(),
                                    name: "Topic 2 Name",
                                    numberOfQuestions: anyPositiveInt()
                            ]
                ]
        )
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
