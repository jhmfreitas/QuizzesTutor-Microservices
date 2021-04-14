package pt.ulisboa.tecnico.socialsoftware.tournament.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.common.dtos.answer.StatementQuizDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizType
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.ExternalStatementCreationDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TopicWithCourseDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.StudentDto
import pt.ulisboa.tecnico.socialsoftware.common.exceptions.TutorException
import pt.ulisboa.tecnico.socialsoftware.common.utils.DateHandler
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration

import static pt.ulisboa.tecnico.socialsoftware.common.exceptions.ErrorMessage.NOT_ENOUGH_QUESTIONS_TOURNAMENT

@DataJpaTest
class QuizTournamentGenerateTest extends TournamentTest {
    def tournamentDto
    def question
    def studentDto2

    def setup() {
        tournamentDto = createTournament(creator1, STRING_DATE_TODAY, STRING_DATE_LATER, NUMBER_OF_QUESTIONS, false)

        studentDto2 = new StudentDto()
        studentDto2.setId(2)
        studentDto2.setName(USER_2_NAME)
        studentDto2.setUsername(USER_2_USERNAME)
    }

    def "generate a quiz with 1 student solving" () {
        given:
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")
        tournamentRequiredStub.createQuiz(_ as Integer, _ as Integer, _ as ExternalStatementCreationDto) >> 1
        tournamentRequiredStub.startTournamentQuiz(_ as Integer, _ as Integer) >> new StatementQuizDto()

        when:
        tournamentService.solveQuiz(participant1.getId(), tournamentDto.getId())

        then: "quiz is generated"
        def tournament = tournamentRepository.findById(tournamentDto.getId()).get()
        tournament.getQuizId() == 1
    }

    def "generate a quiz with 1 student solving and question with less one topic" () {
        given: 'a participant'
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")
        and: 'remove topic from question'
        topicDto2.getQuestions().remove(question)
        tournamentRequiredStub.createQuiz(_ as Integer, _ as Integer, _ as ExternalStatementCreationDto) >> 1
        tournamentRequiredStub.startTournamentQuiz(_ as Integer, _ as Integer) >> new StatementQuizDto()

        when:
        tournamentService.solveQuiz(participant1.getId(), tournamentDto.getId())

        then:
        def error = thrown(TutorException)
        error.errorMessage == NOT_ENOUGH_QUESTIONS_TOURNAMENT
    }

    def "generate a quiz with 1 student solving and question with more one topic" () {
        given: 'a participant'
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")
        and: 'a new topic'
        def topicDto3 = new TopicWithCourseDto()
        topicDto3.setId(2)
        topicDto3.setName(TOPIC_3_NAME)
        topicDto3.setCourseId(1)
        and: 'add topic to question'
        question.addTopic(topicDto3)

        when:
        tournamentService.solveQuiz(participant1.getId(), tournamentDto.getId())

        then:
        def error = thrown(TutorException)
        error.errorMessage == NOT_ENOUGH_QUESTIONS_TOURNAMENT
    }

    def "generate a quiz with 2 student solving" () {
        given:
        def participant2 = createTournamentParticipant(studentDto2)
        and:
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant2, "")
        tournamentRequiredStub.createQuiz(_ as Integer, _ as Integer, _ as ExternalStatementCreationDto) >> 1
        tournamentRequiredStub.startTournamentQuiz(_ as Integer, _ as Integer) >> new StatementQuizDto()

        when: 'both students solve the quiz'
        tournamentService.solveQuiz(participant1.getId(), tournamentDto.getId())
        tournamentService.solveQuiz(participant2.getId(), tournamentDto.getId())

        then: 'one quiz generated'
        def tournament = tournamentRepository.findById(tournamentDto.getId()).get()
        tournament.getQuizId() == 1
    }

    def "disabling assessment for already created tournament" () {
        given:
        tournamentRepository.findById(tournamentDto.getId()).orElse(null).addParticipant(participant1, "")
        and:
        assessment.setStatus(Assessment.Status.DISABLED)

        when:
        tournamentService.solveQuiz(participant1.getId(), tournamentDto.getId())

        then:
        def error = thrown(TutorException)
        error.errorMessage == NOT_ENOUGH_QUESTIONS_TOURNAMENT
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}