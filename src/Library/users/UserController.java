package Library.users;

import Library.controller.Controller;
import Library.model.User;

public class UserController extends Controller {
    private User user;
    private Controller preController;

    public UserController(int screenWidth, int screenHeight, User user, Controller preController) {
        super(screenWidth, screenHeight);
        this.user = user;
        this.preController = preController;
        removePreView();
    }

    private void removePreView() {
        preController.getStartButtonBox().setVisible(false);
        preController.getAppTitle().setVisible(false);
    }
}
