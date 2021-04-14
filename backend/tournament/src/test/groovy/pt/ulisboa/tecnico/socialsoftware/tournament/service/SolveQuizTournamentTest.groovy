package pt.ulisboa.tecnico.socialsoftware.tournament.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.common.dtos.answer.StatementQuizDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.StudentDto
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration

@DataJpaTest
class SolveQuizTournamentTest extends TournamentTest {
    def tournamentDto

    def setup() {
        tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)
    }

    def "1 student solve a tournament" () {
        given:
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")

        when:
        def result = tournamentService.solveQuiz(participant1.getId(), tournamentDto.getId())

        then: "solved it"
        result != null
    }

    def "2 student solve a tournament" () {
        given:
        def studentDto2 = new StudentDto()
        studentDto2.setId(2)
        studentDto2.setName(USER_2_NAME)
        studentDto2.setUsername(USER_2_USERNAME)
        def participant2 = createTournamentParticipant(studentDto2)
        and:
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant2, "")

        when:
        def result1 = tournamentService.solveQuiz(participant1.getId(), tournamentDto.getId())
        def result2 = tournamentService.solveQuiz(participant2.getId(), tournamentDto.getId())

        then: "solved it"
        result1 != null
        result2 != null
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}