package pt.ulisboa.tecnico.socialsoftware.tutor;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.AnswerService;
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.JwtTokenProvider;
import pt.ulisboa.tecnico.socialsoftware.tutor.config.TutorModuleConfiguration;
import pt.ulisboa.tecnico.socialsoftware.tutor.demoutils.TutorDemoUtils;

/*@PropertySource({"classpath:application.properties" })
@Import({TutorModuleConfiguration.class})
@EnableJpaRepositories
@EnableTransactionManagement
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication*/
@PropertySource({"classpath:application.properties" })
@EnableJpaRepositories({"pt.ulisboa.tecnico.socialsoftware.tutor"})
@EnableTransactionManagement
@EnableJpaAuditing
@EnableScheduling
@Import({TutorModuleConfiguration.class})
@SpringBootApplication
public class TutorServiceApplication extends SpringBootServletInitializer implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(TutorServiceApplication.class, args);
    }

    @Autowired
    private AnswerService answerService;

    @Autowired
    private TutorDemoUtils demoUtils;

    @Bean
    EventBus eventBus() {
        return new EventBus();
    }

    @Override
    public void afterPropertiesSet() {
        JwtTokenProvider.generateKeys();
        answerService.writeQuizAnswersAndCalculateStatistics();

        demoUtils.resetDemoInfo();
    }
}
