package pt.ulisboa.tecnico.socialsoftware.tutor.internal

import pt.ulisboa.tecnico.socialsoftware.common.dtos.answer.StatementAnswerDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.answer.StatementQuestionDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.answer.StatementQuizDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.course.CourseType
import pt.ulisboa.tecnico.socialsoftware.common.dtos.execution.CourseExecutionDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.execution.CourseExecutionStatus
import pt.ulisboa.tecnico.socialsoftware.common.dtos.question.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.quiz.QuizType
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.FindTopicsDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament.TopicWithCourseDto
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest

class ApiTestBase {

    def getUserDtoTest() {
        UserDto dto = new UserDto()
        dto.setId(1)
        dto.setUsername(SpockTest.USER_1_USERNAME)
        dto.setEmail(SpockTest.USER_1_EMAIL)
        dto.setName(SpockTest.USER_1_NAME)
        dto.setRole("STUDENT")
        dto.setActive(true)
        dto.setCreationDate("2021-04-10T15:34:10.830873Z")
        dto.setLastAccess("2021-04-10T15:34:10.830873Z")
        return dto
    }

    def getCourseExecutionDtoTest() {
        CourseExecutionDto dto = new CourseExecutionDto()
        dto.setAcademicTerm("1 Semestre 2019/2020");
        dto.setAcronym(SpockTest.COURSE_1_ACRONYM);
        dto.setCourseExecutionId(1);
        dto.setCourseExecutionType(CourseType.TECNICO);
        dto.setCourseId(1);
        dto.setCourseType(CourseType.TECNICO);
        dto.setName(SpockTest.COURSE_1_NAME);
        dto.setStatus(CourseExecutionStatus.ACTIVE);
        dto.setNumberOfActiveTeachers(1);
        dto.setNumberOfInactiveTeachers(0);
        dto.setNumberOfActiveStudents(4);
        dto.setNumberOfInactiveStudents(1);
        dto.setNumberOfQuizzes(2);
        dto.setNumberOfQuestions(2);
        dto.setCourseExecutionUsers(new ArrayList<>());
        dto.setEndDate("2021-05-10T15:34:10.830873Z")

        UserDto userDto1 = new UserDto()
        userDto1.setId(1)
        userDto1.setUsername(SpockTest.USER_1_USERNAME)
        userDto1.setEmail(SpockTest.USER_1_EMAIL)
        userDto1.setName(SpockTest.USER_1_NAME)
        userDto1.setRole("STUDENT")
        userDto1.setActive(true)
        userDto1.setCreationDate("2021-04-10T15:34:10.830873Z")
        userDto1.setLastAccess("2021-04-13T15:34:10.830873Z")

        UserDto userDto2 = new UserDto()
        userDto2.setId(2)
        userDto2.setUsername(SpockTest.USER_2_USERNAME)
        userDto2.setEmail(SpockTest.USER_2_EMAIL)
        userDto2.setName(SpockTest.USER_2_NAME)
        userDto2.setRole("STUDENT")
        userDto2.setActive(true)
        userDto2.setCreationDate("2021-04-10T15:34:10.830873Z")
        userDto2.setLastAccess("2021-04-13T15:34:10.830873Z")

        dto.getCourseExecutionUsers().add(userDto1)
        dto.getCourseExecutionUsers().add(userDto2)
        return dto
    }

    def getDemoCourseExecutionIdTest() {
        return 1
    }

    def getQuizIdTest() {
        return 1
    }

    def getQuizDtoTest() {
        QuizDto dto = new QuizDto()
        dto.setId(1);
        dto.setKey(1);
        dto.setScramble(true);
        dto.setQrCodeOnly(false);
        dto.setOneWay(true);
        dto.setTitle("title");
        dto.setTimed(true);
        dto.setType(QuizType.EXTERNAL_QUIZ.toString());
        dto.setSeries(1);
        dto.setVersion("1");
        dto.setNumberOfQuestions(2);
        dto.setNumberOfAnswers(0);
        dto.setCreationDate("2021-04-10T15:34:10.830873Z");
        dto.setAvailableDate("2021-04-11T15:34:10.830873Z");
        dto.setConclusionDate("2021-04-13T15:34:10.830873Z");
        dto.setResultsDate("2021-04-14T15:34:10.830873Z");
        dto.setQuestions(new ArrayList<QuestionDto>())

        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(1)
        questionDto.setKey(1)
        questionDto.setTitle("title")
        questionDto.setContent("content")
        questionDto.setDifficulty(1)
        questionDto.setNumberOfAnswers(0)
        questionDto.setNumberOfGeneratedQuizzes(0)
        questionDto.setNumberOfNonGeneratedQuizzes(0)
        questionDto.setNumberOfCorrect(0)
        questionDto.setCreationDate("2021-04-02T15:34:10.830873Z")
        questionDto.setStatus("AVAILABLE")
        questionDto.setImage(null)
        questionDto.setTopics(null)
        questionDto.setSequence(1)

        QuestionDto questionDto2 = new QuestionDto();
        questionDto2.setId(2)
        questionDto2.setKey(2)
        questionDto2.setTitle("title2")
        questionDto2.setContent("content2")
        questionDto2.setDifficulty(1)
        questionDto2.setNumberOfAnswers(0)
        questionDto2.setNumberOfGeneratedQuizzes(0)
        questionDto2.setNumberOfNonGeneratedQuizzes(0)
        questionDto2.setNumberOfCorrect(0)
        questionDto2.setCreationDate("2021-04-02T15:34:10.830873Z")
        questionDto2.setStatus("AVAILABLE")
        questionDto2.setImage(null)
        questionDto2.setTopics(null)
        questionDto2.setSequence(2)

        dto.getQuestions().add(questionDto)
        dto.getQuestions().add(questionDto2)
        return dto
    }

    def getStatementQuizDtoTest() {
        StatementQuizDto dto = new StatementQuizDto()
        dto.setId(1);
        dto.setQuizAnswerId(1);
        dto.setTitle("title");
        dto.setOneWay(true);
        dto.setTimed(true);
        dto.setAvailableDate("2021-04-10T15:34:10.830873Z")
        dto.setConclusionDate("2021-04-20T15:34:10.830873Z")
        dto.setTimeToAvailability(1000)
        dto.setTimeToSubmission(1000)
        dto.setQuestions(new ArrayList<StatementQuestionDto>())

        StatementQuestionDto statementQuestionDto1 = new StatementQuestionDto()
        statementQuestionDto1.setContent("content 1")
        statementQuestionDto1.setImage(null)
        statementQuestionDto1.setSequence(1)
        statementQuestionDto1.setQuestionId(1)

        StatementQuestionDto statementQuestionDto2 = new StatementQuestionDto()
        statementQuestionDto2.setContent("content 2")
        statementQuestionDto2.setImage(null)
        statementQuestionDto2.setSequence(2)
        statementQuestionDto2.setQuestionId(2)

        dto.getQuestions().add(statementQuestionDto1)
        dto.getQuestions().add(statementQuestionDto2)

        StatementAnswerDto statementAnswerDto = new StatementAnswerDto()
        statementAnswerDto.setTimeTaken(100);
        statementAnswerDto.setSequence(1);
        statementAnswerDto.setQuestionAnswerId(1);
        statementAnswerDto.setQuizQuestionId(1);
        statementAnswerDto.setUserDiscussion(null)
        statementAnswerDto.setTimeToSubmission(100)

        dto.setAnswers(new ArrayList<StatementAnswerDto>())
        dto.getAnswers().add(statementAnswerDto)
        return dto
    }
}
