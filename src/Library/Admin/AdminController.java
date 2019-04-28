package Library.Admin;

import Library.controller.BaseController;
import Library.controller.Controller;
import Library.model.User;
import javafx.scene.control.Label;

public class AdminController extends BaseController {
    private User admin;
    private Controller controller;
    private AdminDatabase database;
    private AdminCallbacks callBacks;
    private Label userLabel;

    public AdminController(Controller controller) {
        this.callBacks = new AdminCallbacks(this);
        this.controller = controller;
        database = new AdminDatabase("root", "Ashkan007", "java_library");
        database.connectDatabase();
        admin = database.getUsers("admin").get(0);
        removePreView();
        setupUserList();
    }

    private void setupUserList() {
        userLabel = new Label("Users");
        userLabel.setStyle("-fx-background-color: #F4F4F4;-fx-text-fill: #F00;-fx-text-alignment: center;-fx-alignment: center;" +
                "-fx-font-family: 'DejaVu Serif';-fx-font-size: 20;-fx-padding: 10 0 10 0");

    }

    private void removePreView() {
        controller.getStartButtonBox().setVisible(false);
        controller.getAppTitle().setVisible(false);
    }

}
