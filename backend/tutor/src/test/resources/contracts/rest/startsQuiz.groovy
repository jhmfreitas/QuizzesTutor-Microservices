package contracts.rest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        Represents a successful scenario of starting a quiz and returning statement quiz dto
        ```
        given:
            service needs quiz id and user id
        when:
            service sends quiz id and user id
        then:
            we'll grant him the statement quiz dto
        ```
    """)

    request {
        method 'GET'
        url '/rest/answer/startQuiz/1/1'
    }
    response {
        status 200
        """                questions: {
                    [
                             [
                                     content: "content 1",
                                     image: null,
                                     sequence: 1,
                                     questionId: 1
                             ],
                             [
                                     content: "content 2",
                                     image: null,
                                     sequence: 2,
                                     questionId: 2
                             ],
                     ]
                } ,
                answers: { [
                    [
                            timeTaken: 100,
                            sequence: 1,
                            questionAnswerId: 1,
                            userDiscussion: null,
                            quizQuestionId: 1,
                            timeToSubmission: 100
                    ]
                ]}"""
        body(
                id: 1,
                quizAnswerId: 1,
                title: "title",
                oneWay: true,
                timed: true,
                availableDate: "2021-04-10T15:34:10.830873Z",
                conclusionDate: "2021-04-20T15:34:10.830873Z",
                timeToAvailability: 1000,
                timeToSubmission: 1000,
                questions:
                    [
                            [
                                    content: "content 1",
                                    image: null,
                                    sequence: 1,
                                    questionId: 1
                            ],
                            [
                                    content: "content 2",
                                    image: null,
                                    sequence: 2,
                                    questionId: 2
                            ],
                    ],
                answers: [
                        [
                                timeTaken: 100,
                                sequence: 1,
                                questionAnswerId: 1,
                                userDiscussion: null,
                                quizQuestionId: 1,
                                timeToSubmission: 100
                        ]
                ]
        )
        headers {
            contentType(applicationJson())
        }
    }
}