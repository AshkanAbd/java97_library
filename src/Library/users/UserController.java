package Library.users;

import Library.controller.Controller;
import Library.model.Book;
import Library.model.User;
import Library.view.BookViewHolder;
import Library.view.Divider;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class UserController {
    private User user;
    private Controller controller;
    private int screenWidth, screenHeight;
    private UserDatabase database;
    private ScrollPane rentedScrollPane, booksScrollPane;

    public UserController(int screenWidth, int screenHeight, User user, Controller preController) {
        this.user = user;
        this.controller = preController;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        database = new UserDatabase("root", "Ashkan007", "java_library");
        database.connectDatabase();
        removePreView();
        setupRentedBooks();
        setupAllBooks();
        controller.getMainPane().getChildren().addAll(rentedScrollPane, booksScrollPane);
        AnchorPane.setLeftAnchor(rentedScrollPane, 30.0);
        AnchorPane.setTopAnchor(rentedScrollPane, 20.0);
        AnchorPane.setLeftAnchor(booksScrollPane, 300.0);
        AnchorPane.setTopAnchor(booksScrollPane, 20.0);
    }

    private void setupAllBooks() {
        List<Book> bookList = database.getBooks();
        Label booksLabel = new Label("All books");
        booksLabel.setStyle("-fx-background-color: #F4F4F4;-fx-text-fill: #F00;-fx-text-alignment: center;-fx-alignment: center;" +
                "-fx-font-family: 'DejaVu Serif';-fx-font-size: 20;-fx-padding: 10 0 10 0");
        VBox vBox = createBookVBox(bookList, booksLabel);
        booksLabel.setPrefWidth(vBox.getPrefWidth());
        booksScrollPane = createScrollPane(vBox);
    }

    private void setupRentedBooks() {
        List<Book> rentedBookList = database.rentedBooks(user);
        Label rentedBooksLabel = new Label("Rented books");
        rentedBooksLabel.setStyle("-fx-background-color: #F4F4F4;-fx-text-fill: #F00;-fx-text-alignment: center;-fx-alignment: center;" +
                "-fx-font-family: 'DejaVu Serif';-fx-font-size: 20;-fx-padding: 10 0 10 0");
        VBox vBox = createBookVBox(rentedBookList, rentedBooksLabel);
        rentedBooksLabel.setPrefWidth(vBox.getPrefWidth());
        rentedScrollPane = createScrollPane(vBox);
    }

    private ScrollPane createScrollPane(VBox vBox) {
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(screenHeight - 40);
        return scrollPane;
    }

    private VBox createBookVBox(List<Book> books, Label header) {
        VBox vBox = new VBox();
        vBox.getChildren().add(header);
        vBox.setPrefHeight(50);
        for (Book book : books) {
            BookViewHolder viewHolder = new BookViewHolder(book);
            viewHolder.setOnClick(e -> System.out.println(book.getName()));
            Divider divider = new Divider("#f00", viewHolder.getPane().getPrefWidth() + 10, 5);
            vBox.getChildren().addAll(viewHolder.getPane(), divider);
            vBox.setPrefHeight(vBox.getPrefHeight() + viewHolder.getPane().getPrefHeight() + 5);
        }
        vBox.setPrefWidth(250);
        return vBox;
    }

    private void removePreView() {
        controller.getStartButtonBox().setVisible(false);
        controller.getAppTitle().setVisible(false);
    }
}
