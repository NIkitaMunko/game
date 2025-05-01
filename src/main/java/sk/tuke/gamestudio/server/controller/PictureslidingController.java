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
        String[][] values = new String[fieldArray.length][fieldArray[0].length];
        int[][] frameNumbers = new int[fieldArray.length][fieldArray[0].length];

        for (int i = 0; i < fieldArray.length; i++) {
            for (int j = 0; j < fieldArray[i].length; j++) {
                String piece = String.valueOf(fieldArray[i][j].getPiece());
                values[i][j] = piece;
                int pieceNumber = Integer.parseInt(piece);
                frameNumbers[i][j] = (pieceNumber - 1) % 8 + 1;
            }
        }

        model.addAttribute("field", values);
        model.addAttribute("frameNumbers", frameNumbers);
        model.addAttribute("gameState", field.getState());
        model.addAttribute("isSolved", field.isSolved());

        return "ps";
    }
}