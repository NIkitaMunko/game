package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.picturesliding.core.Direction;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.picturesliding.core.Tile;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PictureslidingController {

    private GameField field = new GameField(3, 3);
    private String playerName;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/picturesliding")
    public String picturesliding(
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "reset", required = false) boolean reset,
            @RequestParam(value = "playerName", required = false) String inputPlayerName,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "rating", required = false) String rating,
            Model model
    ) {

        if (inputPlayerName != null && !inputPlayerName.isBlank()) this.playerName = inputPlayerName;

        if (comment != null && playerName != null)
            commentService.addComment(new Comment("picture_sliding", playerName, comment, new Date()));

        if (rating != null && playerName != null) {
            try {
                int gameRate = Integer.parseInt(rating);
                ratingService.setRating(new Rating("picture_sliding", playerName, gameRate, new Date()));
            } catch (NumberFormatException e) {
                model.addAttribute("error", "Invalid rating value.");
            }
        }

        if (reset) {
            field = new GameField(3, 3);
        } else if (direction != null && !direction.isEmpty()) {
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

        model.addAttribute("field", values);
        model.addAttribute("frameNumbers", frameNumbers);
        model.addAttribute("isSolved", field.isSolved());
        model.addAttribute("comments", commentService.getComments("picture_sliding"));
        model.addAttribute("rating", ratingService.getAverageRating("picture_sliding"));
        model.addAttribute("playerName", this.playerName);

        return "ps"; // todo сделать чтобы сайт каждый раз сам не перерисовывался
    }
}