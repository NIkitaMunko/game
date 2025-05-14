package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.picturesliding.consoleui.ConsoleUI;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
@Service
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.play();
    }

    @Bean
    public ConsoleUI consoleUI(GameField field, ScoreService scoreService, CommentService commentService, RatingService ratingService) {
        ConsoleUI consoleUI = new ConsoleUI(field);
        consoleUI.setScoreService(scoreService);
        consoleUI.setCommentService(commentService);
        consoleUI.setRatingService(ratingService);
        return consoleUI;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public GameField field() {
        return new GameField(2, 2);
    }

    @Bean
    public ScoreService scoreService() {
//        return new ScoreServiceJPA();
        return new ScoreServiceRestClient();
    }

    @Bean
    public CommentService commentService() {
//        return new CommentServiceJPA();
        return new CommentServiceRestClient();
    }

    @Bean
    public RatingService ratingService() {
//        return new RatingServiceJPA();
        return new RatingServiceRestClient();
    }

    @Bean
    public PlayerService playerService() {
//        return new RatingServiceJPA();
        return new PlayerServiceRestClient();
    }
}