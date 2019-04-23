package Library.view;

import Library.model.Book;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.sql.Date;

public class BookViewHolder {

    private GridPane pane;
    private Label nameLabel;
    private Label writerLabel;
    private Label ownerLabel;
    private Label dateLabel;
    private Book book;

    public BookViewHolder(Book book) {
        this.book = book;
        pane = new GridPane();
        nameLabel = new Label("name :" + book.getName());
        writerLabel = new Label("writer :" + book.getWriter());
        ownerLabel = new Label();
        dateLabel = new Label();
        if (book.getOwner() != null && !book.getOwner().isEmpty()) {
            ownerLabel.setText("owner :" + book.getOwner());
            Date date = new Date(book.getFreeDate().getTime());
            dateLabel.setText("until :" + date.toString());
        }
        pane.add(writerLabel, 0, 1);
        pane.add(nameLabel, 0, 0);
        pane.add(ownerLabel, 1, 0);
        pane.add(dateLabel, 1, 1);
        nameLabel.setPrefSize(100, 40);
        writerLabel.setPrefSize(100, 40);
        ownerLabel.setPrefSize(150, 40);
        dateLabel.setPrefSize(150, 40);
        pane.setStyle("-fx-background-color: #ecff99");
        nameLabel.setStyle("-fx-alignment: center;-fx-text-alignment: center");
        writerLabel.setStyle("-fx-alignment: center;-fx-text-alignment: center");
        ownerLabel.setStyle("-fx-alignment: center;-fx-text-alignment: center");
        dateLabel.setStyle("-fx-alignment: center;-fx-text-alignment: center");
        pane.setPrefWidth(nameLabel.getPrefWidth() + dateLabel.getPrefWidth());
        pane.setPrefHeight(nameLabel.getPrefHeight() + writerLabel.getPrefHeight());
    }

    public void setOnClick(EventHandler<? super MouseEvent> event) {
        pane.setOnMouseClicked(event);
    }

    public void setName(String name) {
        nameLabel.setText(name);
    }

    public void setWriter(String writer) {
        writerLabel.setText(writer);
    }

    public void setOwner(String owner) {
        ownerLabel.setText(owner);
    }

    public void setDate(Date date) {
        dateLabel.setText(date.toString());
    }

    public GridPane getPane() {
        return pane;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public Label getWriterLabel() {
        return writerLabel;
    }

    public Label getOwnerLabel() {
        return ownerLabel;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public Book getBook() {
        return book;
    }
}
