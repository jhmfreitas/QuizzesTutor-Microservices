package pt.ulisboa.tecnico.socialsoftware.tournament.services.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.execution.CourseExecutionDto;
import pt.ulisboa.tecnico.socialsoftware.common.remote.CourseExecutionInterface;

@RestController
@RequestMapping(value = "/rest/execution")
public class CourseExecutionController {

    @Autowired
    CourseExecutionInterface courseExecutionInterface;

    @RequestMapping(value = "/find/{courseExecutionId}", method = RequestMethod.GET, produces = "application/json")
    public CourseExecutionDto findCourseExecution(@PathVariable Integer courseExecutionId) {
        return courseExecutionInterface.findCourseExecution(courseExecutionId);
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public Integer findDemoCourseExecution() {
        return courseExecutionInterface.getDemoCourseExecutionId();
    }
}
