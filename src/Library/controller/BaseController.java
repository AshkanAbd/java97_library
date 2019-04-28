package Library.controller;

import Library.database.Database;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;


public abstract class BaseController {
    protected int screenWidth, screenHeight;

    protected ScrollPane createScrollPane(VBox vBox) {
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(screenHeight - 40);
        return scrollPane;
    }

    public void closeDatabase(Database database) {
        database.close();
    }

}
