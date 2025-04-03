package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.picturesliding.consoleui.ConsoleUI;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
@Service
public class SpringClient {

    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class, args);
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
    public GameField field() {
        return new GameField(2, 2);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
}