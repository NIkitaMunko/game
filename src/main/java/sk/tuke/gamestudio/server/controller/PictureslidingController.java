package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.picturesliding.core.Direction;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.picturesliding.core.Tile;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PictureslidingController {

    private GameField field = new GameField(3, 3);

    @RequestMapping("/picturesliding")
    public String picturesliding(@RequestParam(value = "direction", required = false) String direction, Model model) {
        if (direction != null && !direction.isEmpty()) {
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

        return "ps";
    }
}