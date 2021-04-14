package contracts.rest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        Represents a successful scenario of updating a quiz
        ```
        given:
            service needs quiz dto
        when:
            service sends quiz dto
        then:
            we'll grant him the generated quiz id
        ```
    """)

    request {
        method 'POST'
        url '/rest/quiz/update'
        headers {
            contentType(applicationJson())
        }
        body(
                id: 1,
                key: 1,
                scramble: true,
                qrCodeOnly: false,
                oneWay: true,
                timed: true,
                type: "EXTERNAL_QUIZ",
                title: "title",
                creationDate: "2021-04-10T15:34:10.830873Z",
                availableDate: "2021-04-11T15:34:10.830873Z",
                conclusionDate: "2021-04-13T15:34:10.830873Z",
                resultsDate: "2021-04-14T15:34:10.830873Z",
                series: 1,
                version: "1",
                numberOfQuestions: 2,
                numberOfAnswers: 0,
                questions: [
                        [
                                id: 1,
                                key: 1,
                                title: "title",
                                content: "content",
                                difficulty: 1,
                                numberOfAnswers: 0,
                                numberOfGeneratedQuizzes: 0,
                                numberOfNonGeneratedQuizzes: 0,
                                numberOfCorrect: 0,
                                creationDate: "2021-04-02T15:34:10.830873Z",
                                status: "AVAILABLE",
                                image: null,
                                topics: null,
                                sequence: 1
                        ],
                        [
                                id: 2,
                                key: 2,
                                title: "title2",
                                content: "content2",
                                difficulty: 1,
                                numberOfAnswers: 0,
                                numberOfGeneratedQuizzes: 0,
                                numberOfNonGeneratedQuizzes: 0,
                                numberOfCorrect: 0,
                                creationDate: "2021-04-02T15:34:10.830873Z",
                                status: "AVAILABLE",
                                image: null,
                                topics: null,
                                sequence: 2
                        ]
                ]
        )
    }
    response {
        status 200
    }
}
