package sk.tuke.gamestudio.picturesliding;

import sk.tuke.gamestudio.picturesliding.consoleui.ConsoleUI;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.picturesliding.entity.Score;
import sk.tuke.gamestudio.picturesliding.service.ScoreServiceJDBC;

import java.util.Date;

public class PictureSliding {

    public static void main(String[] args) {
//        var gameField = new GameField(2, 2);
//        var consoleUI = new ConsoleUI(gameField);
//        consoleUI.play();
//        ConsoleUI.closeScanner();

        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
        scoreServiceJDBC.addScore(new Score("picture_sliding", "Nikita Munko", 445, new Date()));
//
//        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
//        commentServiceJDBC.addComment(new Comment("picture_sliding", "HGC", "lol)))", new Date()));

//        RatingServiceJDBC ratingServiceJDBC = new RatingServiceJDBC();
//        ratingServiceJDBC.setRating(new Rating("picture_sliding", "Nikita Muno", 999, new Date()));
    }
}
