package Library.view;

import Library.model.Book;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.sql.Date;

public class BookPane {

    private Book book;
    private VBox pane;
    private Button button;
    private String style;
    private DatePicker datePicker;

    public BookPane(Book book) {
        this.book = book;
        style = "-fx-alignment: CENTER_LEFT;-fx-padding: 12 10 12 10; -fx-font-family: 'DejaVu Serif';-fx-font-size: 20;-fx-text-fill: #FFF";
        Label nameLabel = new Label("Book name : " + book.getName());
        Label writerLabel = new Label("Writer : " + book.getWriter());
        nameLabel.setPrefWidth(Integer.MAX_VALUE);
        writerLabel.setPrefWidth(Integer.MAX_VALUE);
        nameLabel.setStyle(style);
        writerLabel.setStyle(style);
        button = new Button();
        button.setPrefWidth(Integer.MAX_VALUE);
        pane = new VBox(nameLabel, writerLabel);
        pane.setStyle("-fx-background-color: #2c70ff");
        if (book.getOwner() != null && !book.getOwner().isEmpty()) {
            setupOwnedBook();
        } else {
            setupFreeBook();
        }
        VBox.setMargin(button, new Insets(0, 10, 10, 10));
    }

    private void setupFreeBook() {
        datePicker = new DatePicker();
        datePicker.setEditable(false);
        datePicker.setConverter(new DateConverter());
        datePicker.setPrefWidth(Integer.MAX_VALUE);
        pane.getChildren().addAll(datePicker, button);
        VBox.setMargin(datePicker, new Insets(12, 10, 12, 10));
    }

    private void setupOwnedBook() {
        Label ownerLabel = new Label("Owner : " + book.getOwner());
        Date date = new Date(book.getFreeDate().getTime());
        Label dateLabel = new Label("Date : " + date.toString());
        ownerLabel.setStyle(style);
        dateLabel.setStyle(style);
        ownerLabel.setPrefWidth(Integer.MAX_VALUE);
        dateLabel.setPrefWidth(Integer.MAX_VALUE);
        pane.getChildren().addAll(ownerLabel, dateLabel, button);
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public VBox getPane() {
        return pane;
    }

    public Button getButton() {
        return button;
    }

    public void setOnClick(EventHandler<? super MouseEvent> event) {
        button.setOnMouseClicked(event);
    }
}
