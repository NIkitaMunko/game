package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.picturesliding.core.Direction;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.picturesliding.core.Tile;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.PlayerService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/picturesliding")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PictureslidingControllerAngular {

    private String playerName;
    private String playerPass;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private PlayerService playerService;

    @PostMapping
    public Map<String, Object> getGameState(
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "reset", required = false) boolean reset,
            @RequestParam(value = "playerName", required = false) String inputPlayerName,
            @RequestParam(value = "playerPass", required = false) String inputPlayerPass,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "rating", required = false) String rating,
            @RequestParam(value = "score", required = false) String score,
            @RequestBody(required = false) Map<String, String[][]> body
    ) {

        if (inputPlayerName != null && !inputPlayerName.isBlank()) {
            if (inputPlayerName.equals("guest")) {
                playerName = inputPlayerName;
            } else {
                List<Player> players = playerService.getPlayers();
                boolean playerFound = false;

                for (Player player : players) {
                    if (player.getName().equals(inputPlayerName)) {
                        playerFound = true;

                        if (player.getPassword().equals(inputPlayerPass)) {
                            playerName = inputPlayerName;
                            playerPass = inputPlayerPass;
                        }
                        break;
                    }
                }

                if (!playerFound) {
                    playerService.addPlayer(new Player(inputPlayerName, inputPlayerPass));
                    playerName = inputPlayerName;
                    playerPass = inputPlayerPass;
                }
            }
        }

        GameField field;
        if (reset || body == null || body.get("field") == null) {
            field = new GameField(3, 3);
        } else {
            field = createGameFieldFromJson(body.get("field"));
        }

        if (comment != null && !comment.isBlank() && playerName != null && !playerName.equals("guest"))
            commentService.addComment(new Comment("picture_sliding", playerName, comment, new Date()));

        if (rating != null && playerName != null && !playerName.equals("guest")) {
            try {
                int gameRate = Integer.parseInt(rating);
                ratingService.setRating(new Rating("picture_sliding", playerName, gameRate, new Date()));
            } catch (NumberFormatException ignored) {
            }
        }

        if (score != null && playerName != null && !playerName.equals("guest")) {
            try {
                int playerScore = Integer.parseInt(score);
                if (playerScore != 1000)
                    scoreService.addScore(new Score("picture_sliding", playerName, playerScore, new Date()));
            } catch (NumberFormatException ignored) {
            }
        }

        if (!reset && direction != null && !direction.isEmpty()) {
            Direction dir = Direction.getDirection(direction.toLowerCase());
            if (dir != null) field.moveTile(dir);
        }

        Tile[][] fieldArray = field.getFieldArray();
        int rows = fieldArray.length;
        int cols = fieldArray[0].length;

        String[][] values = new String[rows][cols];
        int[][] frameNumbers = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int pieceNumber = fieldArray[row][col].getPiece();

                values[row][col] = String.valueOf(pieceNumber);
                frameNumbers[row][col] = (pieceNumber - 1) % 8 + 1;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("field", values);
        response.put("frameNumbers", frameNumbers);
        response.put("isSolved", field.isSolved());
        response.put("comments", commentService.getComments("picture_sliding"));
        response.put("scores", scoreService.getTopScores("picture_sliding"));
        response.put("rating", ratingService.getAverageRating("picture_sliding"));
        response.put("player_rating", ratingService.getRating("picture_sliding", playerName));
        response.put("playerName", playerName);
        response.put("playerPass", playerPass);

        return response;
    }

    private GameField createGameFieldFromJson(String[][] fieldValues) {
        int rows = fieldValues.length;
        int cols = fieldValues[0].length;
        GameField field = new GameField(rows, cols);

        Tile[][] fieldArray = field.getFieldArray();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int pieceNumber = Integer.parseInt(fieldValues[row][col]);
                fieldArray[row][col].setPiece(pieceNumber);
            }
        }

        return field;
    }
}