package contracts.rest

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        Represents a successful scenario of getting course execution info
        ```
        given:
            service needs course execution id
        when:
            service sends course execution id
        then:
            we'll grant him the course execution info
        ```
    """)

    request {
        method 'GET'
        url '/rest/execution/find/1'
    }
    response {
        status 200
        body(
                courseExecutionType: "TECNICO",
                courseType: "TECNICO",
                status: "ACTIVE",
                endDate: "2021-05-10T15:34:10.830873Z",
                academicTerm: "1 Semestre 2019/2020",
                acronym: "C12",
                name: "Course 1 Name",
                courseExecutionId: 1,
                courseId: 1,
                numberOfQuestions: 2,
                numberOfQuizzes: 2,
                numberOfActiveStudents: 4,
                numberOfInactiveStudents: 1,
                numberOfActiveTeachers: 1,
                numberOfInactiveTeachers: 0,
                courseExecutionUsers: [
                        [
                                id: 1,
                                username: "a@a.a",
                                email: "user1@mail.com",
                                name: "User 1 Name",
                                role: "STUDENT",
                                active: true,
                                creationDate: "2021-04-10T15:34:10.830873Z",
                                lastAccess: "2021-04-13T15:34:10.830873Z"
                        ],
                        [
                                id: 2,
                                username: "a@a.b",
                                email: "user2@mail.com",
                                name: "User 2 Name",
                                role: "STUDENT",
                                active: true,
                                creationDate: "2021-04-10T15:34:10.830873Z",
                                lastAccess: "2021-04-13T15:34:10.830873Z"
                        ]

                ]
        )
        headers {
            contentType(applicationJson())
        }
    }
}