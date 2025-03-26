package sk.tuke.gamestudio.picturesliding;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.picturesliding.consoleui.ConsoleUI;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.service.CommentServiceJDBC;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.security.Provider;
import java.util.Date;

public class PictureSliding {

    public static void main(String[] args) {
//        var gameField = new GameField(2, 2);
//        var consoleUI = new ConsoleUI(gameField);
//        consoleUI.play();
//        ConsoleUI.closeScanner();

        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
        scoreServiceJDBC.addScore(new Score("picture_sliding", "Nikita Munko", 445, new Date()));

        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
        commentServiceJDBC.addComment(new Comment("picture_sliding", "HGC", "lol)))", new Date()));
    }
}
