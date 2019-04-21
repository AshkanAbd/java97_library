package Library;

import Library.database.DataBase;
import Library.controller.Controller;
import Library.database.QureyBuilder;
import Library.model.Book;
import Library.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private DataBase mySQL;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Library/view/sample.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        Parent rootParent = loader.load();
        Scene mainScene = new Scene(rootParent, 640, 480);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
