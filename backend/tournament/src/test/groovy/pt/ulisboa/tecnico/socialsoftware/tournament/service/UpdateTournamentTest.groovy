package pt.ulisboa.tecnico.socialsoftware.tournament.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TopicListDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TopicWithCourseDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TournamentDto
import pt.ulisboa.tecnico.socialsoftware.common.exceptions.TutorException
import pt.ulisboa.tecnico.socialsoftware.common.utils.DateHandler
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tournament.domain.TournamentCreator
import pt.ulisboa.tecnico.socialsoftware.tournament.domain.TournamentTopic

import static pt.ulisboa.tecnico.socialsoftware.common.exceptions.ErrorMessage.TOURNAMENT_IS_OPEN
import static pt.ulisboa.tecnico.socialsoftware.common.exceptions.ErrorMessage.TOURNAMENT_TOPIC_COURSE

@DataJpaTest
class UpdateTournamentTest extends TournamentTest {
    def topicDto3
    def tournamentDto
    def tournamentTopic3

    def setup() {
        topicDto3 = new TopicWithCourseDto()
        topicDto3.setId(3)
        topicDto3.setName(TOPIC_3_NAME)
        topicDto3.setCourseId(1)
        tournamentTopic3 = new TournamentTopic(topicDto3.getId(), topicDto3.getName(), topicDto3.getCourseId())

        tournamentDto = new TournamentDto()
        tournamentDto.setStartTime(STRING_DATE_TOMORROW)
        tournamentDto.setEndTime(STRING_DATE_LATER)
        tournamentDto.setNumberOfQuestions(NUMBER_OF_QUESTIONS)
        tournamentDto.setCanceled(false)
    }

    def "user that created tournament changes start time"() {
        given:
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)
        and: "new startTime"
        def newStartTime = STRING_DATE_TOMORROW_PLUS_10_MINUTES
        tournamentDto.setStartTime(newStartTime)

        when:
        tournamentService.updateTournament(topics, tournamentDto)

        then:
        tournamentRepository.count() == 1L
        def result = tournamentRepository.findAll().get(0)
        DateHandler.toISOString(result.getStartTime()) == newStartTime
    }

    def "user that created tournament changes end time"() {
        given:
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)
        and: "new endTime"
        def newEndTime = STRING_DATE_LATER_PLUS_10_MINUTES
        tournamentDto.setEndTime(newEndTime)

        when:
        tournamentService.updateTournament(topics, tournamentDto)

        then:
        tournamentRepository.count() == 1L
        def result = tournamentRepository.findAll().get(0)
        DateHandler.toISOString(result.getEndTime()) == newEndTime
    }

    def "user that created tournament changes number of questions"() {
        given: "a tournament"
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)
        and: "a new number of questions"
        def newNumberOfQuestions = 10
        tournamentDto.setNumberOfQuestions(newNumberOfQuestions)

        when:
        tournamentService.updateTournament(topics, tournamentDto)

        then:
        tournamentRepository.count() == 1L
        def result = tournamentRepository.findAll().get(0)
        result.numberOfQuestions == newNumberOfQuestions
    }

    def "user that created tournament adds topic of same course"() {
        given: "a tournament"
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)
        and: "a new topics list"
        topics.add(topicDto3.getId())
        topicsList.add(createTournamentTopic(topicDto3))

        when:
        tournamentService.updateTournament(topics, tournamentDto)

        then:
        tournamentRepository.count() == 1L
        def result = tournamentRepository.findAll().get(0)
        result.getTopics() == [tournamentTopic1, tournamentTopic2, tournamentTopic3]  as Set
    }

    def "user that created tournament adds topic of different course"() {
        given: "a tournament"
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)
        and: "a new topics list"
        def topicDto4 = new TopicWithCourseDto()
        topicDto4.setId(4)
        topicDto4.setName(TOPIC_3_NAME)
        and: "with a different course"
        topicDto4.setCourseId(2)
        topics.add(topicDto4.getId())
        topicsList.add(createTournamentTopic(topicDto4))

        when:
        tournamentService.updateTournament(topics, tournamentDto)

        then:
        def exception = thrown(TutorException)
        exception.getErrorMessage() == TOURNAMENT_TOPIC_COURSE
        tournamentRepository.count() == 1L
        def result = tournamentRepository.findAll().get(0)
        result.getTopics() == [tournamentTopic1, tournamentTopic2]  as Set
    }

    def "user that created tournament removes existing topic from tournament that contains that topic"() {
        given: "a tournament"
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)
        and: "a new topics list"
        topics.remove(topicDto2.getId())
        topicsList.remove(tournamentTopic2)

        when:
        tournamentService.updateTournament(topics, tournamentDto)

        then:
        tournamentRepository.count() == 1L
        def result = tournamentRepository.findAll().get(0)
        result.getTopics() == [tournamentTopic1] as Set
    }

    def "user that created an open tournament tries to change it"() {
        given: "a tournament"
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto.setStartTime(STRING_DATE_TODAY)
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)

        when:
        tournamentService.updateTournament(topics, tournamentDto)

        then:
        def exception = thrown(TutorException)
        exception.getErrorMessage() == TOURNAMENT_IS_OPEN
        tournamentRepository.count() == 1L
    }

    def "user that created tournament tries to change it after has ended with no answers"() {
        given: "a tournament"
        tournamentDto.setStartTime(STRING_DATE_TODAY)
        tournamentDto.setEndTime(STRING_DATE_TODAY)
        tournamentRequiredStub.getTournamentCreator(_ as Integer) >> new TournamentCreator(creator1.getId(), USER_1_USERNAME, USER_1_NAME)
        tournamentRequiredStub.getTournamentCourseExecution(_ as Integer) >> tournamentExternalCourseExecution
        tournamentRequiredStub.getTournamentTopics(_ as TopicListDto) >> topicsList
        tournamentDto = tournamentService.createTournament(creator1.getId(), EXTERNAL_COURSE_EXECUTION_ID_1, topics, tournamentDto)
        and: "a new number of questions"
        def newNumberOfQuestions = 10
        tournamentDto.setNumberOfQuestions(newNumberOfQuestions)

        when:
        tournamentService.updateTournament(topics, tournamentDto)

        then:
        tournamentRepository.count() == 1L
        def result = tournamentRepository.findAll().get(0)
        result.numberOfQuestions == newNumberOfQuestions
    }

    def "user that created tournament tries to change it with answers"() {
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
        tournamentService.updateTournament(topics, tournamentDto)

        then:
        def exception = thrown(TutorException)
        exception.getErrorMessage() == TOURNAMENT_IS_OPEN
        tournamentRepository.count() == 1L
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
