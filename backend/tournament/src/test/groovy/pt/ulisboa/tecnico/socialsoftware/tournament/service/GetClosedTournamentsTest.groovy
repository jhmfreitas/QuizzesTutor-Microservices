package pt.ulisboa.tecnico.socialsoftware.tournament.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration

@DataJpaTest
class GetClosedTournamentsTest extends TournamentTest {
    def "create 1 tournament on time and get closed tournaments"() {
        given: 'a tournamentDto'
        def tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        when:
        def result = tournamentService.getClosedTournamentsForCourseExecution(externalCourseExecution.getId())

        then: "there is no returned data"
        result.size() == 0
    }

    def "create 1 canceled tournament and get closed tournaments"() {
        given: 'a tournamentDto'
        def tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_TODAY, NUMBER_OF_QUESTIONS, true)

        when:
        def result = tournamentService.getClosedTournamentsForCourseExecution(EXTERNAL_COURSE_EXECUTION_ID_1)

        then: "there is no returned data"
        result.size() == 0
    }

    def "create 2 tournaments on time and get closed tournaments"() {
        given: 'a tournamentDto1'
        def tournamentDto1 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto2'
        def tournamentDto2 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        when:
        def result = tournamentService.getClosedTournamentsForCourseExecution(externalCourseExecution.getId())

        then: "the returned data is correct"
        result.size() == 0
    }

    def "create 2 tournaments out of time and 1 on time and get closed tournaments"() {
        given: 'a tournamentDto1'
        def tournamentDto1 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_TODAY, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto2'
        def tournamentDto2 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_TODAY, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto3'
        def tournamentDto3 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        when:
        def result = tournamentService.getClosedTournamentsForCourseExecution(externalCourseExecution.getId())

        then: "the returned data is correct"
        result.size() == 2
    }

    def "create 3 tournaments out of time and 1 canceled and get closed tournaments"() {
        given: 'a tournamentDto1'
        def tournamentDto1 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_TODAY, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto2'
        def tournamentDto2 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_TODAY, NUMBER_OF_QUESTIONS, false)
        and: 'a tournamentDto3'
        def tournamentDto3 = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_TODAY, NUMBER_OF_QUESTIONS, true)

        when:
        def result = tournamentService.getClosedTournamentsForCourseExecution(externalCourseExecution.getId())

        then: "the returned data is correct"
        result.size() == 2
    }

    def "create 0 tournaments and get closed tournaments"() {
        given: 'nothing'

        when:
        def result = tournamentService.getClosedTournamentsForCourseExecution(externalCourseExecution.getId())

        then: "there is no returned data"
        result.size() == 0
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}