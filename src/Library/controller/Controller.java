package Library.controller;

import Library.Main;
import Library.database.Database;
import Library.model.User;
import Library.users.UserController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Controller {
    @FXML
    private AnchorPane mainPane;
    private int screenWidth, screenHeight;
    private CallBacks callBacks;
    private Main main;
    Dialog<String> signInDialog, signUpDialog, adminSignInDialog;
    TextField usernameSignInTextField, passwordSignInTextField;
    TextField usernameSignUpTextField, passwordSignUpTextField, emailSignUpTextField;
    TextField adminSignInPassword;
    private Database database;
    private Text appTitle;
    private HBox startButtonBox;

    public Controller(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        database = new Database("root", "Ashkan007", "java_library");
        database.connectDatabase();
    }

    void enterUserMode(User user) {
        UserController userController = new UserController(screenWidth, screenHeight, user, this);
        main.getLoader().setController(userController);
    }

    public void initialize() {
        callBacks = new CallBacks(this);
        setupModeButtons();
        setupAppTitle();
        setupSignInDialog();
        setupSignUpDialog();
        setupAdminSignInDialog();
    }

    private void setupAppTitle() {
        appTitle = new Text("Welcome to library manager application!!!");
        appTitle.setText("Welcome to library manager application!!!");
        appTitle.setStyle("-fx-fill: #F00;-fx-font-family: 'DejaVu Serif';-fx-font-size: 30");
        appTitle.setWrappingWidth(1000);
        appTitle.setTextAlignment(TextAlignment.CENTER);
        mainPane.getChildren().add(appTitle);
        AnchorPane.setTopAnchor(appTitle, screenHeight / 2.0 - 100);
        AnchorPane.setLeftAnchor(appTitle, (screenWidth / 2.0) - appTitle.getWrappingWidth() / 2);
    }

    private void setupModeButtons() {
        startButtonBox = new HBox();
        Button signUpBtn = new Button("Sign up");
        signUpBtn.setPrefSize(100, 30);
        signUpBtn.setOnMouseClicked(callBacks::signUpBtnClicked);
        Button signInBtn = new Button("Sign in");
        signInBtn.setPrefSize(100, 30);
        signInBtn.setOnMouseClicked(callBacks::signInBtnClicked);
        Button adminSignInBtn = new Button("Admin");
        adminSignInBtn.setPrefSize(100, 30);
        adminSignInBtn.setOnMouseClicked(callBacks::adminSignInBtnClicked);
        startButtonBox.getChildren().addAll(signInBtn, signUpBtn, adminSignInBtn);
        HBox.setMargin(signUpBtn, new Insets(0, 0, 0, 50));
        HBox.setMargin(adminSignInBtn, new Insets(0, 0, 0, 50));
        startButtonBox.setPrefWidth(signInBtn.getPrefWidth() + signUpBtn.getPrefWidth() + HBox.getMargin(signUpBtn).getLeft()
                + adminSignInBtn.getPrefWidth() + HBox.getMargin(adminSignInBtn).getLeft());
        mainPane.getChildren().add(startButtonBox);
        AnchorPane.setTopAnchor(startButtonBox, screenHeight / 2.0 + 100);
        AnchorPane.setLeftAnchor(startButtonBox, (screenWidth / 2.0) - startButtonBox.prefWidthProperty().doubleValue() / 2);
    }

    private void setupSignInDialog() {
        usernameSignInTextField = new TextField();
        passwordSignInTextField = new PasswordField();
        Text dialogTitle = new Text("Enter username and password");
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        usernameSignInTextField.setPrefWidth(310);
        passwordSignInTextField.setPrefWidth(310);
        okButton.setPrefWidth(70);
        okButton.setOnMouseClicked(callBacks::signInOkBtnClicked);
        cancelButton.setPrefWidth(70);
        cancelButton.setOnMouseClicked(callBacks::signInCancelBtnClicked);
        dialogTitle.setStyle("-fx-font-family: 'DejaVu Serif';-fx-font-size: 20");
        signInDialog = new Dialog<>();
        signInDialog.setResizable(false);
        signInDialog.setTitle("Sign In");
        DialogPane dialogPane = new DialogPane();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(dialogTitle);
        AnchorPane.setTopAnchor(dialogTitle, 30.0);
        AnchorPane.setLeftAnchor(dialogTitle, 20.0);
        anchorPane.getChildren().add(usernameSignInTextField);
        AnchorPane.setTopAnchor(usernameSignInTextField, 100.0);
        AnchorPane.setLeftAnchor(usernameSignInTextField, 20.0);
        anchorPane.getChildren().add(passwordSignInTextField);
        AnchorPane.setLeftAnchor(passwordSignInTextField, 20.0);
        AnchorPane.setTopAnchor(passwordSignInTextField, 140.0);
        anchorPane.getChildren().add(cancelButton);
        AnchorPane.setTopAnchor(cancelButton, 190.0);
        AnchorPane.setLeftAnchor(cancelButton, 180.0);
        anchorPane.getChildren().add(okButton);
        AnchorPane.setTopAnchor(okButton, 190.0);
        AnchorPane.setLeftAnchor(okButton, 260.0);
        dialogPane.getChildren().add(anchorPane);
        dialogPane.setPrefSize(350, 230);
        signInDialog.setDialogPane(dialogPane);
        signInDialog.setOnShowing(_1 -> callBacks.resizeSignInDialog());
    }

    private void setupSignUpDialog() {
        usernameSignUpTextField = new TextField();
        passwordSignUpTextField = new PasswordField();
        emailSignUpTextField = new TextField();
        Text dialogTitle = new Text("Enter your information");
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        usernameSignUpTextField.setPrefWidth(310);
        passwordSignUpTextField.setPrefWidth(310);
        emailSignUpTextField.setPrefWidth(310);
        cancelButton.setOnMouseClicked(callBacks::signUpCancelBtnClicked);
        okButton.setOnMouseClicked(callBacks::signUpOkBtnClicked);
        okButton.setPrefWidth(70);
        cancelButton.setPrefWidth(70);
        dialogTitle.setStyle("-fx-font-family: 'DejaVu Serif';-fx-font-size: 20");
        signUpDialog = new Dialog<>();
        signUpDialog.setResizable(true);
        signUpDialog.setTitle("Sign up");
        signUpDialog.setResizable(false);
        DialogPane dialogPane = new DialogPane();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(dialogTitle);
        AnchorPane.setTopAnchor(dialogTitle, 30.0);
        AnchorPane.setLeftAnchor(dialogTitle, 20.0);
        anchorPane.getChildren().add(usernameSignUpTextField);
        AnchorPane.setTopAnchor(usernameSignUpTextField, 100.0);
        AnchorPane.setLeftAnchor(usernameSignUpTextField, 20.0);
        anchorPane.getChildren().add(emailSignUpTextField);
        AnchorPane.setTopAnchor(emailSignUpTextField, 140.0);
        AnchorPane.setLeftAnchor(emailSignUpTextField, 20.0);
        anchorPane.getChildren().add(passwordSignUpTextField);
        AnchorPane.setTopAnchor(passwordSignUpTextField, 180.0);
        AnchorPane.setLeftAnchor(passwordSignUpTextField, 20.0);
        anchorPane.getChildren().add(cancelButton);
        AnchorPane.setTopAnchor(cancelButton, 230.0);
        AnchorPane.setLeftAnchor(cancelButton, 180.0);
        anchorPane.getChildren().add(okButton);
        AnchorPane.setTopAnchor(okButton, 230.0);
        AnchorPane.setLeftAnchor(okButton, 260.0);
        dialogPane.getChildren().add(anchorPane);
        dialogPane.setPrefSize(350, 270);
        signUpDialog.setDialogPane(dialogPane);
        signUpDialog.setOnShowing(_1 -> callBacks.resizeSignUpDialog());
    }

    private void setupAdminSignInDialog() {
        adminSignInPassword = new PasswordField();
        Text dialogTitle = new Text("Enter admin password");
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        adminSignInPassword.setPrefWidth(310);
        okButton.setPrefWidth(70);
        okButton.setOnMouseClicked(callBacks::adminSignInOkBtnClicked);
        cancelButton.setPrefWidth(70);
        cancelButton.setOnMouseClicked(callBacks::adminSignInCancelBtnClicked);
        dialogTitle.setStyle("-fx-font-family: 'DejaVu Serif';-fx-font-size: 20");
        adminSignInDialog = new Dialog<>();
        adminSignInDialog.setResizable(true);
        adminSignInDialog.setTitle("Admin sign in");
        adminSignInDialog.setResizable(false);
        DialogPane dialogPane = new DialogPane();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(dialogTitle);
        AnchorPane.setTopAnchor(dialogTitle, 30.0);
        AnchorPane.setLeftAnchor(dialogTitle, 20.0);
        anchorPane.getChildren().add(adminSignInPassword);
        AnchorPane.setTopAnchor(adminSignInPassword, 100.0);
        AnchorPane.setLeftAnchor(adminSignInPassword, 20.0);
        anchorPane.getChildren().add(cancelButton);
        AnchorPane.setTopAnchor(cancelButton, 150.0);
        AnchorPane.setLeftAnchor(cancelButton, 180.0);
        anchorPane.getChildren().add(okButton);
        AnchorPane.setTopAnchor(okButton, 150.0);
        AnchorPane.setLeftAnchor(okButton, 260.0);
        dialogPane.getChildren().add(anchorPane);
        dialogPane.setPrefSize(350, 190);
        adminSignInDialog.setDialogPane(dialogPane);
        adminSignInDialog.setOnShowing(_1 -> callBacks.resizeAdminSignInDialog());
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Text getAppTitle() {
        return appTitle;
    }

    public HBox getStartButtonBox() {
        return startButtonBox;
    }

    public AnchorPane getMainPane() {
        return mainPane;
    }

    public Database getDatabase() {
        return database;
    }

    public Main getMain() {
        return main;
    }
}
