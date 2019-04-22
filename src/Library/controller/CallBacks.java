package Library.controller;

import Library.database.Database;
import Library.model.User;
import Library.users.UserController;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

class CallBacks {
    private Controller controller;

    CallBacks(Controller controller) {
        this.controller = controller;
    }

    void signInBtnClicked(MouseEvent mouseEvent) {
        if (invalidClick(mouseEvent)) return;
        controller.usernameSignInTextField.setText("Username");
        controller.passwordSignInTextField.setText("Password");
        controller.signInDialog.show();
    }

    void signUpBtnClicked(MouseEvent mouseEvent) {
        if (invalidClick(mouseEvent)) return;
        controller.usernameSignUpTextField.setText("Username");
        controller.emailSignUpTextField.setText("Email");
        controller.passwordSignUpTextField.setText("Password");
        controller.signUpDialog.show();
    }

    void signInOkBtnClicked(MouseEvent mouseEvent) {
        if (invalidClick(mouseEvent)) return;
        String username = controller.usernameSignInTextField.getText();
        String password = controller.passwordSignInTextField.getText();
        if (username.isEmpty() || password.isEmpty()) return;
        controller.signInDialog.setResult("");
        controller.signInDialog.hide();
        if (controller.database.signIn(username, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Sign in successfully");
            alert.setContentText(null);
            alert.show();
            controller.enterUserMode(new User(username));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Username or password is wrong!!!");
            alert.setContentText(null);
            alert.show();
        }
    }

    void signUpOkBtnClicked(MouseEvent mouseEvent) {
        if (invalidClick(mouseEvent)) return;
        String username = controller.usernameSignUpTextField.getText();
        String password = controller.passwordSignUpTextField.getText();
        String email = controller.emailSignUpTextField.getText();
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) return;
        controller.signUpDialog.setResult("");
        controller.signUpDialog.hide();
        User user = new User(username, email, password);
        Object[] result = controller.database.signUp(user);
        if ((boolean) result[0]) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Sign up successfully\nNow you can login");
            alert.setContentText(null);
            alert.show();
        } else {
            int errorCode = (int) result[1];
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (errorCode == Database.EXIST_USERNAME) {
                alert.setHeaderText("Username exist,please use an other username");
            } else if (errorCode == Database.EXIST_EMAIL) {
                alert.setHeaderText("Email exist in database");
            } else {
                alert.setHeaderText("Unknown!!!");
            }
            alert.setContentText(null);
            alert.show();
        }
    }

    void signInCancelBtnClicked(MouseEvent mouseEvent) {
        if (invalidClick(mouseEvent)) return;
        controller.signInDialog.setResult("");
        controller.signInDialog.hide();
    }

    void signUpCancelBtnClicked(MouseEvent mouseEvent) {
        if (invalidClick(mouseEvent)) return;
        controller.signUpDialog.setResult("");
        controller.signUpDialog.hide();
    }

    void resizeSignInDialog() {
        controller.signInDialog.setHeight(260);
        controller.signInDialog.setWidth(350);
    }

    void resizeSignUpDialog() {
        controller.signUpDialog.setHeight(300);
        controller.signUpDialog.setWidth(350);
    }

    private boolean invalidClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return true;
        return mouseEvent.getClickCount() != 1;
    }
}
