package pt.ulisboa.tecnico.socialsoftware.tournament.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration

@DataJpaTest
class GetTournamentsTest extends TournamentTest {
    def "create 1 tournament on time and get all tournaments"() {
        given: 'a tournamentDto'
        def tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        when:
        def result = tournamentService.getTournamentsForCourseExecution(EXTERNAL_COURSE_EXECUTION_ID_1)

        then: "there is no returned data"
        result.size() == 1
    }

    def "create 1 canceled tournament and get all tournaments"() {
        given: 'a tournamentDto'
        def tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, true)

        when:
        def result = tournamentService.getTournamentsForCourseExecution(EXTERNAL_COURSE_EXECUTION_ID_1)

        then: "there is no returned data"
        result.size() == 1
    }

    def "create 2 tournaments on time and get all tournaments"() {
        given: 'a tournamentDto1'
        def tournamentDto1 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto2'
        def tournamentDto2 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        when:
        def result = tournamentService.getTournamentsForCourseExecution(EXTERNAL_COURSE_EXECUTION_ID_1)

        then: "the returned data is correct"
        result.size() == 2
    }


    def "create 2 tournaments out of time and 1 in time and get all tournaments"() {
        given: 'a tournamentDto1'
        def tournamentDto1 = createTournament(creator1, STRING_DATE_TOMORROW, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto2'
        def tournamentDto2 = createTournament(creator1, STRING_DATE_TOMORROW, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto3'
        def tournamentDto3 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        when:
        def result = tournamentService.getTournamentsForCourseExecution(EXTERNAL_COURSE_EXECUTION_ID_1)

        then: "the returned data is correct"
        result.size() == 3
    }

    def "create 2 tournaments out of time and 1 canceled and get all tournaments"() {
        given: 'a tournamentDto1'
        def tournamentDto1 = createTournament(creator1, STRING_DATE_TOMORROW, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto2'
        def tournamentDto2 = createTournament(creator1, STRING_DATE_TOMORROW, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto3'
        def tournamentDto3 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, true)

        when:
        def result = tournamentService.getTournamentsForCourseExecution(EXTERNAL_COURSE_EXECUTION_ID_1)

        then: "the returned data is correct"
        result.size() == 3
    }

    def "create 0 tournaments and get all tournaments"() {
        given: 'nothing'

        when:
        def result = tournamentService.getTournamentsForCourseExecution(EXTERNAL_COURSE_EXECUTION_ID_1)

        then: "there is no returned data"
        result.size() == 0
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
