package pt.ulisboa.tecnico.socialsoftware.tournament.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.common.exceptions.TutorException
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration

import static pt.ulisboa.tecnico.socialsoftware.common.exceptions.ErrorMessage.TOURNAMENT_IS_OPEN

@DataJpaTest
class CancelTournamentTest extends TournamentTest {

    def "user that created tournament cancels it"() {
        given:
        def tournamentDto = createTournament(creator1, STRING_DATE_TOMORROW, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        when:
        tournamentService.cancelTournament(tournamentDto.getId())

        then:
        tournamentRepository.count() == 1L
        def result = tournamentRepository.findAll().get(0)
        result.isCanceled()
    }

    def "user that created an open tournament tries to cancel it"() {
        given:
        def tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        when:
        tournamentService.cancelTournament(tournamentDto.getId())

        then:
        def exception = thrown(TutorException)
        exception.getErrorMessage() == TOURNAMENT_IS_OPEN
        tournamentRepository.count() == 1L
    }

    def "user that created tournament tries to cancel it after has ended with no answers"() {
        given:
        def tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_TODAY, NUMBER_OF_QUESTIONS, false)

        when:
        tournamentService.cancelTournament(tournamentDto.getId())

        then:
        tournamentRepository.count() == 1L
        def result = tournamentRepository.findAll().get(0)
        result.isCanceled()
    }

    def "user that created tournament tries to cancel it with answers"() {
        given:
        def tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)
        and: "join a tournament"
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")
        and: "solve a tournament"
        tournamentService.solveQuiz(creator1.getId(), tournamentDto.getId())
        and: "is now closed"
        tournamentDto.setEndTime(STRING_DATE_TODAY)

        when:
        tournamentService.cancelTournament(tournamentDto.getId())

        then:
        def exception = thrown(TutorException)
        exception.getErrorMessage() == TOURNAMENT_IS_OPEN
        tournamentRepository.count() == 1L
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
