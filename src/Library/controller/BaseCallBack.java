package Library.controller;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class BaseCallBack {

    protected boolean invalidClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return true;
        return mouseEvent.getClickCount() != 1;
    }
}
