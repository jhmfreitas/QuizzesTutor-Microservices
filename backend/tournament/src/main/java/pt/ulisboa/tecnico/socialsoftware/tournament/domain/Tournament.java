package pt.ulisboa.tecnico.socialsoftware.tournament.domain;

import pt.ulisboa.tecnico.socialsoftware.dtos.tournament.ExternalStatementCreationDto;
import pt.ulisboa.tecnico.socialsoftware.dtos.tournament.TournamentDto;
import pt.ulisboa.tecnico.socialsoftware.dtos.tournament.TournamentParticipantDto;
import pt.ulisboa.tecnico.socialsoftware.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.utils.DateHandler;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static pt.ulisboa.tecnico.socialsoftware.exceptions.ErrorMessage.*;

@Entity
@Table(name = "tournaments")
public class Tournament  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "number_of_questions")
    private Integer numberOfQuestions;

    @Embedded
    private TournamentCreator creator;

    @Column(name = "is_canceled")
    private boolean isCanceled;

    @ElementCollection
    @CollectionTable(name = "tournaments_participants")
    private Set<TournamentParticipant> participants = new HashSet<>();

    @Embedded
    private TournamentCourseExecution courseExecution;

    @ElementCollection
    @CollectionTable(name = "tournaments_topics")
    private Set<TournamentTopic> topics = new HashSet<>();

    @Column(name = "quiz_id")
    private Integer quizId;

    @Column(name = "privateTournament")
    private boolean privateTournament;

    @Column(name = "password")
    private String password;

    public Tournament() {
    }

    public Tournament(TournamentCreator creator, TournamentCourseExecution courseExecution, Set<TournamentTopic> topics, TournamentDto tournamentDto) {
        setStartTime(DateHandler.toLocalDateTime(tournamentDto.getStartTime()));
        setEndTime(DateHandler.toLocalDateTime(tournamentDto.getEndTime()));
        setNumberOfQuestions(tournamentDto.getNumberOfQuestions());
        setCanceled(tournamentDto.isCanceled());
        setCreator(creator);
        setCourseExecution(courseExecution);
        setTopics(topics);
        setPassword(tournamentDto.getPassword());
        setPrivateTournament(tournamentDto.isPrivateTournament());
    }

    public Integer getId() { return id; }

    public LocalDateTime getStartTime() { return startTime; }

    public void setStartTime(LocalDateTime startTime) {
        // Added 2 minute as a buffer to take latency into consideration
        if (startTime == null || (this.endTime != null && this.endTime.isBefore(startTime) ||
                !DateHandler.now().isBefore(startTime.plusMinutes(2))))
        {
            throw new TutorException(TOURNAMENT_NOT_CONSISTENT, "startTime");
        }

        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() { return endTime; }

    public void setEndTime(LocalDateTime endTime) {
        if (endTime == null || (this.startTime != null && endTime.isBefore(this.startTime))) {
            throw new TutorException(TOURNAMENT_NOT_CONSISTENT, "endTime");
        }

        this.endTime = endTime;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        if (numberOfQuestions <= 0) {
            throw new TutorException(TOURNAMENT_NOT_CONSISTENT, "number of questions");
        }
        this.numberOfQuestions = numberOfQuestions;
    }

    public Integer getNumberOfQuestions() { return numberOfQuestions; }

    public TournamentCreator getCreator() {
        return creator;
    }

    public void setCreator(TournamentCreator creator) {
        this.creator = creator;
    }

    public boolean isCreator(Integer creatorId) { return creator.getId().equals(creatorId); }

    public void setCanceled(boolean isCanceled) { this.isCanceled = isCanceled; }

    public boolean isCanceled() { return isCanceled; }

    public void cancel() {
        checkCanChange();
        this.isCanceled = true;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Set<TournamentParticipant> getParticipants() { return participants; }

    public TournamentCourseExecution getCourseExecution() {
        return courseExecution;
    }

    public void setCourseExecution(TournamentCourseExecution courseExecution) {
        this.courseExecution = courseExecution;
    }

    public Set<TournamentTopic> getTopics() { return topics; }

    public void setTopics(Set<TournamentTopic> topics) {
        Set<TournamentTopic> topicsList = new HashSet<>();
        for (TournamentTopic topic: topics) {
            checkTopicCourse(topic);
            topicsList.add(topic);
        }

        this.topics = topicsList;
    }

    public void updateTopics(Set<TournamentTopic> newTopics) {
        if (newTopics.isEmpty()) throw new TutorException(TOURNAMENT_MUST_HAVE_ONE_TOPIC);

        Set<TournamentTopic> topicsList = new HashSet<>();

        for (TournamentTopic topic : newTopics) {
            checkTopicCourse(topic);
            topicsList.add(topic);
        }

        this.topics = topicsList;
    }

    public void checkTopicCourse(TournamentTopic topic) {
        if (!topic.getCourseId().equals(courseExecution.getCourseId())) {
            throw new TutorException(TOURNAMENT_TOPIC_COURSE);
        }
    }

    public void checkIsParticipant(TournamentParticipant participant) {
        if (!getParticipants().contains(participant)) {
            throw new TutorException(USER_NOT_JOINED, participant.getId());
        }

        if (participant.hasAnswered()) {
            throw new TutorException(USER_ALREDAY_ANSWERED_TOURNAMENT_QUIZ, participant.getId());
        }
    }

    public void addParticipant(TournamentParticipant participant, String password) {
        if (DateHandler.now().isAfter(getEndTime())) {
            throw new TutorException(TOURNAMENT_NOT_OPEN, getId());
        }

        if (isCanceled()) {
            throw new TutorException(TOURNAMENT_CANCELED, getId());
        }

        if (getParticipants().contains(participant)) {
            throw new TutorException(DUPLICATE_TOURNAMENT_PARTICIPANT, participant.getUsername());
        }

        if (isPrivateTournament() && !password.equals(getPassword())) {
            throw new TutorException(WRONG_TOURNAMENT_PASSWORD, getId());
        }

        this.participants.add(participant);
    }

    public void removeParticipant(TournamentParticipant participant) {
        checkIsParticipant(participant);
        this.participants.remove(participant);
    }

    public boolean hasQuiz() { return getQuizId() != null; }

    public void remove() {
        checkCanChange();

        creator = null;
        courseExecution = null;

        getTopics().clear();
        getParticipants().clear();
    }

    public void checkCanChange() {
        int numberOfAnswers = 0;

        if (this.quizId != null) {
            numberOfAnswers = Math.toIntExact(this.getParticipants().stream().filter(TournamentParticipant::hasAnswered).count());
        }

        LocalDateTime now = DateHandler.now();

        if (now.isAfter(getEndTime()) && numberOfAnswers != 0) {
            throw new TutorException(TOURNAMENT_ALREADY_CLOSED, getId());
        }

        if (now.isAfter(getStartTime()) && now.isBefore(getEndTime())) {
            throw new TutorException(TOURNAMENT_IS_OPEN, getId());
        }
    }

    public void updateTournament(TournamentDto tournamentDto, Set<TournamentTopic> topics) {
        checkCanChange();

        if (DateHandler.isValidDateFormat(tournamentDto.getStartTime())) {
            DateHandler.toISOString(getStartTime());
            setStartTime(DateHandler.toLocalDateTime(tournamentDto.getStartTime()));
        }

        if (DateHandler.isValidDateFormat(tournamentDto.getEndTime())) {
            DateHandler.toISOString(getEndTime());
            setEndTime(DateHandler.toLocalDateTime(tournamentDto.getEndTime()));
        }

        setNumberOfQuestions(tournamentDto.getNumberOfQuestions());

        updateTopics(topics);
    }

    public boolean isPrivateTournament() { return privateTournament; }

    public void setPrivateTournament(boolean privateTournament) { this.privateTournament = privateTournament; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public TournamentParticipant findParticipant(Integer userId) {
        return this.getParticipants().stream().filter(participant -> participant.getId().equals(userId)).
                findFirst().orElseThrow(() -> new TutorException(USER_NOT_JOINED, userId));
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", numberOfQuestions=" + numberOfQuestions +
                ", creator=" + creator +
                ", isCanceled=" + isCanceled +
                ", participants=" + participants +
                ", courseExecution=" + courseExecution +
                ", topics=" + topics +
                ", quizId=" + quizId +
                ", privateTournament=" + privateTournament +
                ", password='" + password + '\'' +
                '}';
    }

    public TournamentDto getDto() {
        TournamentDto dto = new TournamentDto();
        dto.setId(getId());
        dto.setCourseAcronym(getCourseExecution().getCourseAcronym());
        if (hasQuiz()) dto.setQuizId(getQuizId());
        dto.setStartTime(DateHandler.toISOString(getStartTime()));
        dto.setEndTime(DateHandler.toISOString(getEndTime()));
        dto.setNumberOfQuestions(getNumberOfQuestions());
        dto.setCanceled(isCanceled());
        dto.setTopicsDto(getTopics().stream()
                .map(TournamentTopic::getDto)
                .collect(Collectors.toSet()));
        dto.setCreator(getCreator().getDto());
        dto.setParticipants(getParticipants().stream()
                .map(TournamentParticipant::getDto)
                .sorted(Comparator.comparing(TournamentParticipantDto::getScore).reversed())
                .collect(Collectors.toList()));
        dto.setPrivateTournament(isPrivateTournament());
        dto.setPassword(getPassword());
        dto.setOpened(getStartTime().isBefore(DateHandler.now()) && getEndTime().isAfter(DateHandler.now()));
        dto.setClosed(getEndTime().isBefore(DateHandler.now()));
        return dto;
    }

    public ExternalStatementCreationDto getExternalStatementCreationDto() {
        ExternalStatementCreationDto dto = new ExternalStatementCreationDto();
        dto.setNumberOfQuestions(getNumberOfQuestions());
        dto.setTopics(getTopics().stream().map(TournamentTopic::getTopicDto).collect(Collectors.toSet()));
        dto.setStartTime(getStartTime());
        dto.setEndTime(getEndTime());
        dto.setId(getId());
        return dto;
    }
}
