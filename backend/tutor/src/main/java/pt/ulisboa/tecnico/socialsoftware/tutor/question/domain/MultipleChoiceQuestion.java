package pt.ulisboa.tecnico.socialsoftware.tutor.question.domain;

import pt.ulisboa.tecnico.socialsoftware.dtos.question.MultipleChoiceQuestionDto;
import pt.ulisboa.tecnico.socialsoftware.dtos.question.OptionDto;
import pt.ulisboa.tecnico.socialsoftware.dtos.question.QuestionDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.dtos.question.QuestionTypes;
import pt.ulisboa.tecnico.socialsoftware.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pt.ulisboa.tecnico.socialsoftware.exceptions.ErrorMessage.*;

@Entity
@DiscriminatorValue(QuestionTypes.MULTIPLE_CHOICE_QUESTION)
public class MultipleChoiceQuestion extends QuestionDetails {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionDetails", fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<Option> options = new ArrayList<>();


    public MultipleChoiceQuestion() {
        super();
    }

    public MultipleChoiceQuestion(Question question, MultipleChoiceQuestionDto questionDto) {
        super(question);
        setOptions(questionDto.getOptions());
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        if (options.stream().filter(OptionDto::isCorrect).count() != 1) {
            throw new TutorException(ONE_CORRECT_OPTION_NEEDED);
        }

        int index = 0;
        for (OptionDto optionDto : options) {
            if (optionDto.getId() == null) {
                optionDto.setSequence(index++);
                new Option(optionDto).setQuestionDetails(this);
            } else {
                Option option = getOptions()
                        .stream()
                        .filter(op -> op.getId().equals(optionDto.getId()))
                        .findAny()
                        .orElseThrow(() -> new TutorException(OPTION_NOT_FOUND, optionDto.getId()));

                option.setContent(optionDto.getContent());
                option.setCorrect(optionDto.isCorrect());
            }
        }
    }

    public void addOption(Option option) {
        options.add(option);
    }

    public Integer getCorrectOptionId() {
        return this.getOptions().stream()
                .filter(Option::isCorrect)
                .findAny()
                .map(Option::getId)
                .orElse(null);
    }

    /*@Override
    public void update(MultipleChoiceQuestionDto questionDetails) {
        setOptions(questionDetails.getOptions());
    }*/


    @Override
    public void update(QuestionDetailsDto questionDetailsDto) {
        if (questionDetailsDto instanceof MultipleChoiceQuestionDto) {
            MultipleChoiceQuestionDto multipleChoiceQuestionDto = (MultipleChoiceQuestionDto) questionDetailsDto;
            setOptions(multipleChoiceQuestionDto.getOptions());
        }
        else {
            throw new TutorException(INVALID_QUESTION_DETAILS_DTO, questionDetailsDto.getClass().getName());
        }
    }

    @Override
    public void update(Updator updator) {
        updator.update(this);
    }

    @Override
    public String getCorrectAnswerRepresentation() {
        return convertSequenceToLetter(this.getCorrectAnswer());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitQuestionDetails(this);
    }

    public void visitOptions(Visitor visitor) {
        for (Option option : this.getOptions()) {
            option.accept(visitor);
        }
    }

    @Override
    public CorrectAnswerDetailsDto getCorrectAnswerDetailsDto() {
        return new MultipleChoiceCorrectAnswerDto(this);
    }

    @Override
    public StatementQuestionDetailsDto getStatementQuestionDetailsDto() {
        return new MultipleChoiceStatementQuestionDetailsDto(this);
    }

    @Override
    public StatementAnswerDetailsDto getEmptyStatementAnswerDetailsDto() {
        return new MultipleChoiceStatementAnswerDetailsDto();
    }

    @Override
    public AnswerDetailsDto getEmptyAnswerDetailsDto() {
        return new MultipleChoiceAnswerDto();
    }

    @Override
    public QuestionDetailsDto getQuestionDetailsDto() {
        return this.getDto();
    }

    public Integer getCorrectAnswer() {
        return this.getOptions()
                .stream()
                .filter(Option::isCorrect)
                .findAny().orElseThrow(() -> new TutorException(NO_CORRECT_OPTION))
                .getSequence();
    }

    @Override
    public void delete() {
        super.delete();
        for (Option option : this.options) {
            option.remove();
        }
        this.options.clear();
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
                "options=" + options +
                '}';
    }

    public static String convertSequenceToLetter(Integer correctAnswer) {
        return correctAnswer != null ? Character.toString('A' + correctAnswer) : "-";
    }

    @Override
    public String getAnswerRepresentation(List<Integer> selectedIds) {
        var result = this.options
                .stream()
                .filter(x -> selectedIds.contains(x.getId()))
                .map(x -> convertSequenceToLetter(x.getSequence()))
                .collect(Collectors.joining("|"));
        return !result.isEmpty() ? result : "-";
    }

    public MultipleChoiceQuestionDto getDto() {
        MultipleChoiceQuestionDto dto = new MultipleChoiceQuestionDto();
        dto.setOptions(getOptions().stream().map(Option::getDto).collect(Collectors.toList()));
        return dto;
    }
}
