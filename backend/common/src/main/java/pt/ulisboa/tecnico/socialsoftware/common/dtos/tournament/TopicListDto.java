package pt.ulisboa.tecnico.socialsoftware.common.dtos.tournament;

import java.io.Serializable;
import java.util.Set;

public class TopicListDto implements Serializable {

    private Set<Integer> topicList;

    public TopicListDto(Set<Integer> topicList) {
        this.topicList = topicList;
    }

    public Set<Integer> getTopicList() {
        return topicList;
    }
}
