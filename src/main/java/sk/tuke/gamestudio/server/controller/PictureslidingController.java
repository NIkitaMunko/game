package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.picturesliding.core.Direction;
import sk.tuke.gamestudio.picturesliding.core.GameField;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PictureslidingController {

    private GameField field = new GameField(2, 2);

    @RequestMapping("/picturesliding")
    public String picturesliding(@RequestParam(value = "direction", required = false) String direction, Model model) {
        if (direction != null && !direction.isEmpty()) {
            Direction dir = Direction.getDirection(direction.toLowerCase());
            if (dir != null) field.moveTile(dir);
        }

        // Добавляем данные в модель для шаблона
        model.addAttribute("field", field.getFieldArray());
        model.addAttribute("gameState", field.getState());
        model.addAttribute("isSolved", field.isSolved());

        return "Thymeleaf"; // Имя Thymeleaf-шаблона
    }
}