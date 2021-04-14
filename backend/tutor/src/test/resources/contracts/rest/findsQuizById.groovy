package contracts.rest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        Represents a successful scenario of getting quiz info by id
        ```
        given:
            service needs quiz id
        when:
            service sends quiz id
        then:
            we'll grant him the quiz dto
        ```
    """)

    request {
        method 'GET'
        url '/rest/quiz/find/1'
    }
    response {
        status 200
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
        headers {
            contentType(applicationJson())
        }
    }
}
