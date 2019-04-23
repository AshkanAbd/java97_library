package Library;

import Library.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.awt.Dimension;

public class Main extends Application {

    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("UI.fxml"));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        Controller controller = new Controller(dimension.width / 2, dimension.height / 2);
        controller.setMain(this);
        loader.setController(controller);
        Parent rootParent = loader.load();
        Scene mainScene = new Scene(rootParent, dimension.width / 2.0, dimension.height / 2.0);
        primaryStage.setTitle("Library manager");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
