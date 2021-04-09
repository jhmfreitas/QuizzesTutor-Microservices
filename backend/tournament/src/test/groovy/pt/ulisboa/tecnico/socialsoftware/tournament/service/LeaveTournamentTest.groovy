package pt.ulisboa.tecnico.socialsoftware.tournament.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.common.exceptions.TutorException
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration

import static pt.ulisboa.tecnico.socialsoftware.common.exceptions.ErrorMessage.USER_NOT_JOINED

@DataJpaTest
class LeaveTournamentTest extends TournamentTest {
    def tournamentDto
    //def user2

    def setup() {
        //user2 = createUser(2, USER_2_NAME, USER_2_USERNAME)

        tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        //createMultipleChoiceQuestion(LOCAL_DATE_TODAY, QUESTION_1_CONTENT, QUESTION_1_TITLE, Question.Status.AVAILABLE, externalCourse)
    }

    def "Student leaves the tournament" () {
        given: 'user joins'
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")

        when:
        tournamentService.leaveTournament(participant1.getId(), tournamentDto.getId())

        then: "the tournament has no participants"
        def result = tournamentRepository.findById(tournamentDto.getId()).orElse(null)
        result.getParticipants().size() == 0
    }

    def "Non participant student leaves the tournament" () {
        when:
        tournamentService.leaveTournament(USER_ID_NOT_USED, tournamentDto.getId())

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == USER_NOT_JOINED
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
