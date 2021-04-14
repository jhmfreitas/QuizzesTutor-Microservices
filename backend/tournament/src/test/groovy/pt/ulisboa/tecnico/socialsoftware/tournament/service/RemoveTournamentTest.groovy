package pt.ulisboa.tecnico.socialsoftware.tournament.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.common.dtos.execution.CourseExecutionStatus
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TopicListDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TournamentDto
import pt.ulisboa.tecnico.socialsoftware.common.exceptions.TutorException
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tournament.domain.TournamentCourseExecution
import pt.ulisboa.tecnico.socialsoftware.tournament.domain.TournamentCreator
import pt.ulisboa.tecnico.socialsoftware.tournament.domain.TournamentTopic

import static pt.ulisboa.tecnico.socialsoftware.common.exceptions.ErrorMessage.QUIZ_HAS_ANSWERS
import static pt.ulisboa.tecnico.socialsoftware.common.exceptions.ErrorMessage.TOURNAMENT_IS_OPEN
import static pt.ulisboa.tecnico.socialsoftware.common.exceptions.ErrorMessage.USER_NOT_FOUND

@DataJpaTest
class RemoveTournamentTest extends TournamentTest {
    def tournamentDto

    def setup() {
        tournamentDto = new TournamentDto()
        tournamentDto.setEndTime(STRING_DATE_LATER)
        tournamentDto.setNumberOfQuestions(NUMBER_OF_QUESTIONS)
        tournamentDto.setCanceled(false)
    }

    def "user that created tournament removes it"() {
        given:
        tournamentDto.setStartTime(STRING_DATE_TOMORROW)
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)

        when:
        tournamentService.removeTournament(tournamentDto.getId())

        then:
        tournamentRepository.count() == 0L
    }

    def "user that created an open tournament tries to remove it"() {
        given: "a tournament"
        tournamentDto.setStartTime(STRING_DATE_TODAY)
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)

        when:
        tournamentService.removeTournament(tournamentDto.getId())

        then:
        def exception = thrown(TutorException)
        exception.getErrorMessage() == TOURNAMENT_IS_OPEN
        tournamentRepository.count() == 1L
    }

    def "user that created tournament tries to remove it after has ended with no answers"() {
        given: "a tournament"
        tournamentDto.setStartTime(STRING_DATE_TODAY)
        tournamentDto.setEndTime(STRING_DATE_TODAY)
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)

        when:
        tournamentService.removeTournament(tournamentDto.getId())

        then:
        tournamentRepository.count() == 0L
    }

    def "user that created tournament tries to remove it with answers"() {
        given: "a tournament"
        tournamentDto.setStartTime(STRING_DATE_TODAY)
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)
        and: "join a tournament"
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")
        and: "solve a tournament"
        tournamentService.solveQuiz(participant1.getId(), tournamentDto.getId())
        and: "is now closed"
        tournamentDto.setEndTime(STRING_DATE_TODAY)

        when:
        tournamentService.removeTournament(tournamentDto.getId())

        then:
        def exception = thrown(TutorException)
        exception.getErrorMessage() == QUIZ_HAS_ANSWERS
        tournamentRepository.count() == 1L
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
