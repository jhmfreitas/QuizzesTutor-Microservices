package pt.ulisboa.tecnico.socialsoftware.tutor.tournament.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class TournamentParticipant {

    @Column(name = "participant_id")
    private Integer id;

    @Column(name = "participant_username")
    private String username;

    @Column(name = "participant_name")
    private String name;

    @Column(name = "participant_answered")
    private boolean answered;

    @Column(name = "number_of_answered")
    private Integer numberOfAnswered;

    @Column(name = "number_of_correct")
    private Integer numberOfCorrect;

    public TournamentParticipant() {
    }

    public TournamentParticipant(Integer userId, String username, String name) {
        this.id = userId;
        this.username = username;
        this.name = name;
        this.answered = false;
        this.numberOfAnswered = 0;
        this.numberOfCorrect = 0;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public Integer getNumberOfAnswered() {
        return numberOfAnswered;
    }

    public void setNumberOfAnswered(Integer numberOfAnswered) {
        this.numberOfAnswered = numberOfAnswered;
    }

    public Integer getNumberOfCorrect() {
        return numberOfCorrect;
    }

    public void setNumberOfCorrect(Integer numberOfCorrect) {
        this.numberOfCorrect = numberOfCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentParticipant that = (TournamentParticipant) o;
        return answered == that.answered && id.equals(that.id) && username.equals(that.username) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, name, answered);
    }
}
