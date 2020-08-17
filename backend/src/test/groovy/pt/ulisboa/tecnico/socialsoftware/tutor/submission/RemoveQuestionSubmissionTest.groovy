package pt.ulisboa.tecnico.socialsoftware.tutor.questionsubmission.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.questionsubmission.domain.Review
import pt.ulisboa.tecnico.socialsoftware.tutor.questionsubmission.domain.QuestionSubmission
import pt.ulisboa.tecnico.socialsoftware.tutor.user.User

@DataJpaTest
class RemoveQuestionSubmissionTest extends SpockTest{
    def student
    def teacher
    def question
    def questionSubmission

    def setup() {
        student = new User(USER_1_NAME, USER_1_USERNAME, User.Role.STUDENT)
        student.setEnrolledCoursesAcronyms(courseExecution.getAcronym())
        userRepository.save(student)
        teacher = new User(USER_2_NAME, USER_2_USERNAME, User.Role.TEACHER)
        userRepository.save(teacher)
        question = new Question()
        question.setKey(1)
        question.setTitle(QUESTION_1_TITLE)
        question.setContent(QUESTION_1_CONTENT)
        question.setCourse(course)
        question.setStatus(Question.Status.IN_REVISION)
        questionRepository.save(question)
        questionSubmission = new QuestionSubmission()
        questionSubmission.setQuestion(question)
        questionSubmission.setUser(student)
        questionSubmission.setCourseExecution(courseExecution)
        questionSubmissionRepository.save(questionSubmission)
    }

    def "student removes a submitted question with no reviews"(){
        when:
        questionSubmissionService.removeSubmittedQuestion(questionSubmission.getId())

        then: "the submitted question and associated reviews are removed"
        questionRepository.count() == 0L
        optionRepository.count() == 0L
        questionSubmissionRepository.count() == 0L
        reviewRepository.count() == 0L
    }

    def "error cases: alreadyInReview=#!hasReviews | hasReviews=#hasReviews"() {
        given:
        if (hasReviews) {
            def review = new Review()
            review.setComment(REVIEW_1_COMMENT)
            review.setUser(teacher)
            review.setQuestionSubmission(questionSubmission)
            review.setStatus(Review.Status.IN_REVISION)
            questionSubmission.addReview(review)
            reviewRepository.save(review)
        } else {
            question.setStatus(Question.Status.IN_REVIEW)
        }

        when:
        questionSubmissionService.removeSubmittedQuestion(questionSubmission.getId())

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.CANNOT_DELETE_REVIEWED_QUESTION

        where:
        hasReviews << [false, true]
    }
    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}




