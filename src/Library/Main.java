package Library;

import Library.database.DataBase;
import Library.controller.Controller;
import Library.database.QueryBuilder;
import Library.model.Book;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;

public class Main extends Application {

    private DataBase mySQL;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/sample.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        Parent rootParent = loader.load();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        Scene mainScene = new Scene(rootParent, dimension.width, dimension.height);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
