package Library.view;

import javafx.scene.layout.AnchorPane;

public class Divider extends AnchorPane {

    public Divider(String color, double width, double height) {
        setStyle("-fx-background-color: " + color);
        setMaxWidth(width);
        setPrefWidth(width);
        setMaxHeight(height);
        setPrefHeight(height);
    }
}
