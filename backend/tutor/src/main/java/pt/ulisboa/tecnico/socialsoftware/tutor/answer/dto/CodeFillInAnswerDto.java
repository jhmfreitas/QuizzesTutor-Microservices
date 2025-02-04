package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.dtos.question.OptionDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.CodeFillInAnswer;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.CodeFillInOption;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CodeFillInAnswerDto extends AnswerDetailsDto {
    private List<OptionDto> options = new ArrayList<>();

    public CodeFillInAnswerDto() {
    }

    public CodeFillInAnswerDto(CodeFillInAnswer answer) {
        if (answer.getFillInOptions() != null)
            this.options = answer.getFillInOptions().stream().map(CodeFillInOption::getDto).collect(Collectors.toList());
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }
}
