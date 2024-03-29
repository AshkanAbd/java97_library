package Library.users;

import Library.controller.BaseController;
import Library.controller.Controller;
import Library.model.Book;
import Library.view.DateConverter;
import Library.model.User;
import Library.view.BookViewHolder;
import Library.view.Divider;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class UserController extends BaseController {
    private User user;
    private Controller controller;
    private UserDatabase database;
    private ScrollPane rentedScrollPane, booksScrollPane;
    ScrollPane bookInfoPane;
    private VBox searchBox;
    private Button signOut;
    private Label booksLabel, rentedBooksLabel;
    private List<Book> rentedBookList;
    private UserCallBacks callBacks;

    public UserController(int screenWidth, int screenHeight, User user, Controller preController) {
        this.user = user;
        this.callBacks = new UserCallBacks(this);
        this.controller = preController;
        database = new UserDatabase("root", "Ashkan007", "java_library");
        database.connectDatabase();
        removePreView();
        setupRentedBooks();
        setupAllBooks();
        setupSignOutBtn();
        setupSearchPane();
        setupBookInfo();
        controller.getMainPane().getChildren().addAll(rentedScrollPane, booksScrollPane);
        AnchorPane.setLeftAnchor(rentedScrollPane, 15.0);
        AnchorPane.setTopAnchor(rentedScrollPane, 20.0);
        AnchorPane.setLeftAnchor(booksScrollPane, 280.0);
        AnchorPane.setTopAnchor(booksScrollPane, 20.0);
    }

    private void setupBookInfo() {
        bookInfoPane = new ScrollPane();
        bookInfoPane.setFitToWidth(true);
        bookInfoPane.setFitToHeight(true);
        controller.getMainPane().getChildren().add(bookInfoPane);
        AnchorPane.setLeftAnchor(bookInfoPane, 545.0);
        AnchorPane.setTopAnchor(bookInfoPane, 330.0);
        AnchorPane.setRightAnchor(bookInfoPane, 20.0);
        AnchorPane.setBottomAnchor(bookInfoPane, 20.0);
    }

    private void setupSignOutBtn() {
        signOut = new Button("Sign out");
        signOut.setOnMouseClicked(callBacks::changeController);
        AnchorPane.setTopAnchor(signOut, 20.0);
        AnchorPane.setRightAnchor(signOut, 20.0);
        controller.getMainPane().getChildren().add(signOut);
    }

    public void refreshAllBookList(List<Book> bookList) {
        VBox vBox = createBookVBox(bookList, booksLabel);
        booksScrollPane.setContent(vBox);
    }

    public void refreshRentedBookList(List<Book> bookList) {
        VBox vBox = createBookVBox(bookList, rentedBooksLabel);
        rentedScrollPane.setContent(vBox);
    }

    private void setupAllBooks() {
        List<Book> bookList = database.getBooks();
        booksLabel = new Label("All books");
        booksLabel.setStyle("-fx-background-color: #F4F4F4;-fx-text-fill: #F00;-fx-text-alignment: center;-fx-alignment: center;" +
                "-fx-font-family: 'DejaVu Serif';-fx-font-size: 20;-fx-padding: 10 0 10 0");
        VBox vBox = createBookVBox(bookList, booksLabel);
        booksLabel.setPrefWidth(vBox.getPrefWidth());
        booksScrollPane = createScrollPane(vBox);
    }

    private void setupRentedBooks() {
        rentedBookList = database.rentedBooks(user);
        rentedBooksLabel = new Label("Rented books");
        rentedBooksLabel.setStyle("-fx-background-color: #F4F4F4;-fx-text-fill: #F00;-fx-text-alignment: center;-fx-alignment: center;" +
                "-fx-font-family: 'DejaVu Serif';-fx-font-size: 20;-fx-padding: 10 0 10 0");
        VBox vBox = createBookVBox(rentedBookList, rentedBooksLabel);
        rentedBooksLabel.setPrefWidth(vBox.getPrefWidth());
        rentedScrollPane = createScrollPane(vBox);
    }

    private void setupSearchPane() {
        Label searchLabel = new Label("Search");
        searchLabel.setStyle(rentedBooksLabel.getStyle());
        searchLabel.setPrefWidth(Integer.MAX_VALUE);
        ChoiceBox<String> listChoiceBox = new ChoiceBox<>();
        listChoiceBox.getItems().addAll("All books", "Rented books");
        listChoiceBox.setPrefWidth(Integer.MAX_VALUE);
        listChoiceBox.setValue("All books");
        TextField nameSearchField = new TextField("Name");
        nameSearchField.setPrefWidth(Integer.MAX_VALUE);
        TextField writerSearchField = new TextField("Writer");
        writerSearchField.setPrefWidth(Integer.MAX_VALUE);
        TextField ownerSearchField = new TextField("Owner");
        ownerSearchField.setPrefWidth(Integer.MAX_VALUE);
        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setEditable(true);
        datePicker.setPrefWidth(Integer.MAX_VALUE);
        datePicker.setConverter(new DateConverter());
        CheckBox rentBox = new CheckBox("Can rent");
        rentBox.setSelected(false);
        Button searchBtn = new Button("Search");
        searchBtn.setPrefWidth(Integer.MAX_VALUE);
        searchBtn.setOnMouseClicked(e -> callBacks.rentedBookFilter(e, listChoiceBox.getValue(), nameSearchField.getText()
                , writerSearchField.getText(), ownerSearchField.getText(), datePicker.getEditor().getText(), rentBox.isSelected()));
        Insets insets = new Insets(0, 0, 10, 0);
        VBox.setMargin(listChoiceBox, new Insets(10, 0, 10, 0));
        VBox.setMargin(nameSearchField, insets);
        VBox.setMargin(writerSearchField, insets);
        VBox.setMargin(ownerSearchField, insets);
        VBox.setMargin(rentBox, insets);
        VBox.setMargin(datePicker, insets);
        VBox.setMargin(searchBtn, insets);
        searchBox = new VBox(searchLabel, listChoiceBox, nameSearchField, writerSearchField, ownerSearchField, datePicker
                , rentBox, searchBtn);
        searchBox.setStyle("-fx-background-color: #F4F4F4");
//        searchBox.setPrefHeight(screenHeight - 40);
        searchBox.setPrefWidth(200);
        controller.getMainPane().getChildren().add(searchBox);
        AnchorPane.setLeftAnchor(searchBox, 545.0);
        AnchorPane.setTopAnchor(searchBox, 20.0);
    }


    private VBox createBookVBox(List<Book> books, Label header) {
        VBox vBox = new VBox();
        vBox.getChildren().add(header);
        vBox.setPrefHeight(50);
        for (Book book : books) {
            BookViewHolder viewHolder = new BookViewHolder(book);
            viewHolder.setOnClick(e -> callBacks.onBookClick(e, viewHolder.getBook()));
            Divider divider = new Divider("#f00", viewHolder.getPane().getPrefWidth() + 10, 5);
            vBox.getChildren().addAll(divider, viewHolder.getPane());
            vBox.setPrefHeight(vBox.getPrefHeight() + viewHolder.getPane().getPrefHeight() + 5);
        }
        Divider divider = new Divider("#f00", vBox.getPrefWidth(), 5);
        vBox.getChildren().add(divider);
        vBox.setPrefWidth(250);
        return vBox;
    }

    private void removePreView() {
        controller.getStartButtonBox().setVisible(false);
        controller.getAppTitle().setVisible(false);
    }

    public void removeViews() {
        booksScrollPane.setVisible(false);
        rentedScrollPane.setVisible(false);
        signOut.setVisible(false);
        searchBox.setVisible(false);
        bookInfoPane.setVisible(false);
    }

    public User getUser() {
        return user;
    }

    public Controller getController() {
        return controller;
    }

    public UserDatabase getDatabase() {
        return database;
    }

    public List<Book> getRentedBookList() {
        return rentedBookList;
    }
}
