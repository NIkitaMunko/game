package sk.tuke.gamestudio.picturesliding;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.picturesliding.consoleui.ConsoleUI;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.security.Provider;
import java.util.Date;

public class PictureSliding {

    public static void main(String[] args) {
        var gameField = new GameField(2, 2);
        var consoleUI = new ConsoleUI(gameField);
        consoleUI.play();
        ConsoleUI.closeScanner();
        // 4,3/10 - когда фиксану генерацию поля и мелкие туду, оценка выше будет
        // 2 - сервисы
        // 0,7 - тесты к сервисам
//        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
//        scoreServiceJDBC.addScore(new Score("picture_sliding", "Nikita Munko", 445, new Date()));
    }
}
