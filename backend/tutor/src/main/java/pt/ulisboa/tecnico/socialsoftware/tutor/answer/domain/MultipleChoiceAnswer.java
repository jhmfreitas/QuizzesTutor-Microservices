package pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain;

import pt.ulisboa.tecnico.socialsoftware.dtos.question.QuestionTypes;
import pt.ulisboa.tecnico.socialsoftware.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.AnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.MultipleChoiceAnswerDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.MultipleChoiceStatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleChoiceQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Option;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static pt.ulisboa.tecnico.socialsoftware.exceptions.ErrorMessage.QUESTION_OPTION_MISMATCH;

@Entity
@DiscriminatorValue(QuestionTypes.MULTIPLE_CHOICE_QUESTION)
public class MultipleChoiceAnswer extends AnswerDetails {
    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;

    public MultipleChoiceAnswer() {
        super();
    }

    public MultipleChoiceAnswer(QuestionAnswer questionAnswer){
        super(questionAnswer);
    }

    public MultipleChoiceAnswer(QuestionAnswer questionAnswer, Option option){
        super(questionAnswer);
        this.setOption(option);
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;

        if (option != null)
            option.addQuestionAnswer(this);
    }

    public void setOption(MultipleChoiceQuestion question, MultipleChoiceStatementAnswerDetailsDto multipleChoiceStatementAnswerDetailsDto) {
        if (multipleChoiceStatementAnswerDetailsDto.getOptionId() != null) {
            Option option = question.getOptions().stream()
                    .filter(option1 -> option1.getId().equals(multipleChoiceStatementAnswerDetailsDto.getOptionId()))
                    .findAny()
                    .orElseThrow(() -> new TutorException(QUESTION_OPTION_MISMATCH, multipleChoiceStatementAnswerDetailsDto.getOptionId()));

            if (this.getOption() != null) {
                this.getOption().getQuestionAnswers().remove(this);
            }

            this.setOption(option);
        } else {
            this.setOption(null);
        }
    }

    @Override
    public boolean isCorrect() {
        return getOption() != null && getOption().isCorrect();
    }


    public void remove() {
        if (option != null) {
            option.getQuestionAnswers().remove(this);
            option = null;
        }
    }

    @Override
    public AnswerDetailsDto getAnswerDetailsDto() {
        return new MultipleChoiceAnswerDto(this);
    }

    @Override
    public boolean isAnswered() {
        return this.getOption() != null;
    }

    @Override
    public String getAnswerRepresentation() {
        return this.getOption() != null ? MultipleChoiceQuestion.convertSequenceToLetter(this.getOption().getSequence()) : "-";
    }

    @Override
    public StatementAnswerDetailsDto getStatementAnswerDetailsDto() {
        return new MultipleChoiceStatementAnswerDetailsDto(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitAnswerDetails(this);
    }

}
