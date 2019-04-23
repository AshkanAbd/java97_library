package Library.users;

import Library.controller.BaseCallBack;
import Library.model.Book;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UserCallBacks extends BaseCallBack {
    private UserController userController;

    UserCallBacks(UserController controller) {
        this.userController = controller;
    }

    void rentedBookFilter(MouseEvent mouseEvent, String list, String name, String writer, String owner, String date, boolean isRent) {
        if (invalidClick(mouseEvent)) return;
        Map<String, String> cause = new HashMap<>();
        if (!name.isEmpty()) cause.put("book_name", name);
        if (!writer.isEmpty()) cause.put("writer", writer);
        if (isRent) {
            cause.put("owner", "");
        } else {
            if (!owner.isEmpty()) cause.put("owner", owner);
            if (!date.isEmpty()) cause.put("free_date", date);
        }
        List<Book> bookList = userController.database.getBooks(cause);
        if (list.equalsIgnoreCase("All books")) {
            VBox vBox = userController.createBookVBox(bookList, userController.booksLabel);
            userController.booksScrollPane.setContent(vBox);
        } else {
            VBox vBox = userController.createBookVBox(bookList, userController.rentedBooksLabel);
            userController.rentedScrollPane.setContent(vBox);
        }
    }

    void changeController(MouseEvent mouseEvent) {
        if (invalidClick(mouseEvent)) return;
        userController.booksScrollPane.setVisible(false);
        userController.rentedScrollPane.setVisible(false);
        userController.signOut.setVisible(false);
        userController.searchBox.setVisible(false);
        userController.controller.getStartButtonBox().setVisible(true);
        userController.controller.getAppTitle().setVisible(true);
        userController.controller.getMain().getLoader().setController(userController.controller);
        userController.database.close();
    }
}
