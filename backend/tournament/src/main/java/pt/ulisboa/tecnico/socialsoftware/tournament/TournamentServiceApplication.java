package pt.ulisboa.tecnico.socialsoftware.tournament;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pt.ulisboa.tecnico.socialsoftware.tournament.config.TournamentModuleConfiguration;

@Configuration
@EnableAutoConfiguration
@Import({TournamentModuleConfiguration.class})
public class TournamentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TournamentServiceApplication.class, args);
    }
}
