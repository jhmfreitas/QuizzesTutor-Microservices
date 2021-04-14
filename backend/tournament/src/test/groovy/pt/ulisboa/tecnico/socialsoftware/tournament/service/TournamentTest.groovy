package pt.ulisboa.tecnico.socialsoftware.tournament.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.common.dtos.execution.CourseExecutionDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.execution.CourseExecutionStatus
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TopicWithCourseDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.StudentDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto
import pt.ulisboa.tecnico.socialsoftware.common.utils.DateHandler
import pt.ulisboa.tecnico.socialsoftware.tournament.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tournament.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tournament.domain.*
import pt.ulisboa.tecnico.socialsoftware.tournament.services.remote.TournamentRequiredService

@DataJpaTest
class TournamentTest extends SpockTest {
    public static String STRING_DATE_TODAY = DateHandler.toISOString(DateHandler.now())

    def assessment
    def tournamentTopic1
    def tournamentTopic2
    def topics
    def topicsList
    def studentDto
    def creator1
    def tournamentExternalCourseExecution
    def participant1
    def courseExecutionDto
    def topicDto1
    def topicDto2
    def tournamentRequiredStub

    def setup() {
        studentDto = new StudentDto()
        studentDto.setId(1)
        studentDto.setName(USER_1_NAME)
        studentDto.setUsername(USER_1_USERNAME)

        creator1 = createTournamentCreator(studentDto)
        participant1 = createTournamentParticipant(studentDto)

        topicDto1 = new TopicWithCourseDto()
        topicDto1.setId(1)
        topicDto1.setName(TOPIC_1_NAME)
        topicDto1.setCourseId(1)
        tournamentTopic1 = createTournamentTopic(topicDto1)

        topicDto2 = new TopicWithCourseDto()
        topicDto2.setId(2)
        topicDto2.setName(TOPIC_2_NAME)
        topicDto2.setCourseId(1)
        tournamentTopic2 = createTournamentTopic(topicDto2)

        topics = new HashSet<Integer>(tournamentTopic1.getId(), tournamentTopic2.getId())
        topicsList = new HashSet<TournamentTopic>()
        topicsList.add(tournamentTopic1)
        topicsList.add(tournamentTopic2)

        STRING_DATE_TODAY = DateHandler.toISOString(DateHandler.now())

        courseExecutionDto = new CourseExecutionDto()
        courseExecutionDto.setCourseId(1)
        courseExecutionDto.setName(COURSE_1_NAME)
        courseExecutionDto.setStatus(CourseExecutionStatus.ACTIVE)
        courseExecutionDto.setAcronym(COURSE_1_ACRONYM)
        tournamentExternalCourseExecution = createTournamentCourseExecution(1, courseExecutionDto)
        tournamentRequiredStub = Stub(TournamentRequiredService)
        tournamentService.setTournamentRequiredService(tournamentRequiredStub)
    }

    def createTournament(TournamentCreator creator, String startTime, String endTime, Integer numberOfQuestions, boolean isCanceled) {
        def tournament = new Tournament()
        tournament.setStartTime(DateHandler.toLocalDateTime(startTime))
        tournament.setEndTime(DateHandler.toLocalDateTime(endTime))
        tournament.setNumberOfQuestions(numberOfQuestions)
        tournament.setCanceled(isCanceled)
        tournament.setCreator(creator)
        tournament.setCourseExecution(tournamentExternalCourseExecution)
        tournament.setTopics(topicsList)
        tournament.setPassword('')
        tournament.setPrivateTournament(false)
        tournamentRepository.save(tournament)

        return tournament.getDto()
    }

    def createPrivateTournament(TournamentCreator creator, String startTime, String endTime, Integer numberOfQuestions, boolean isCanceled, String password) {
        def tournament = new Tournament()
        tournament.setStartTime(DateHandler.toLocalDateTime(startTime))
        tournament.setEndTime(DateHandler.toLocalDateTime(endTime))
        tournament.setNumberOfQuestions(numberOfQuestions)
        tournament.setCanceled(isCanceled)
        tournament.setCreator(creator)
        tournament.setCourseExecution(tournamentExternalCourseExecution)
        tournament.setTopics(topicsList)
        tournament.setPassword(password)
        tournament.setPrivateTournament(true)
        tournamentRepository.save(tournament)

        return tournament.getDto()
    }

    def createTournamentCreator(StudentDto studentDto) {
        return new TournamentCreator(studentDto.getId(), studentDto.getUsername(), studentDto.getName())
    }

    def createTournamentParticipant(UserDto userDto) {
        return new TournamentParticipant(userDto.getId(), userDto.getUsername(), userDto.getName())
    }

    def createTournamentTopic(TopicWithCourseDto topicDto) {
        return new TournamentTopic(topicDto.getId(), topicDto.getName(), topicDto.getCourseId())
    }

    def createTournamentCourseExecution(Integer courseExecutionId, CourseExecutionDto courseExecutionDto) {
        return new TournamentCourseExecution(courseExecutionId,
                courseExecutionDto.getCourseId(),
                CourseExecutionStatus.valueOf(courseExecutionDto.getStatus().toString()),
                courseExecutionDto.getAcronym())
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
