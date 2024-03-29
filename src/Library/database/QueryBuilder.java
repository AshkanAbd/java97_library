package Library.database;

import Library.model.Book;
import Library.model.User;

import java.sql.Date;
import java.util.Map;

public final class QueryBuilder {
    private QueryBuilder() {

    }

    public static String insertBook(Book book) {
        StringBuilder builder = new StringBuilder("INSERT INTO books");
        builder.append("(book_name, writer");
        if (book.getOwner() != null && !book.getOwner().isEmpty()) {
            builder.append(", owner, free_Date");
        }
        builder.append(") VALUES('").append(book.getName()).append("', '").append(book.getWriter());
        if (book.getOwner() != null && !book.getOwner().isEmpty()) {
            builder.append("', '").append(book.getOwner()).append("', '").append(book.getFreeDate().toString());
        }
        builder.append("');");
        return builder.toString();
    }

    public static String insertUser(User user) {
        StringBuilder builder = new StringBuilder("INSERT INTO users");
        builder.append("(username, email, password");
        builder.append(") VALUES('").append(user.getUsername());
        builder.append("', '").append(user.getEmail()).append("', '");
        builder.append(user.getPassword()).append("');");
        return builder.toString();
    }

    public static String selectBook(Map<String, String> cause) {
        StringBuilder builder = new StringBuilder("SELECT * FROM books");
        if (cause == null || cause.isEmpty()) {
            builder.append(";");
            return builder.toString();
        }
        builder.append(" WHERE ").append(createCause(cause)).append(";");
        return builder.toString();
    }

    public static String selectUser(String username) {
        StringBuilder builder = new StringBuilder("SELECT * FROM users");
        if (username == null) {
            builder.append(";");
            return builder.toString();
        }
        builder.append(" WHERE username LIKE '").append(username).append("';");
        return builder.toString();
    }

    public static String updateBook(Book book, String owner, Date date) {
        StringBuilder builder = new StringBuilder("UPDATE books SET ");
        if (owner == null || owner.isEmpty()) {
            builder.append("owner = '' , free_date = NULL");
        } else {
            builder.append("owner = '").append(owner).append("' , ");
            builder.append("free_date = '").append(date.toString()).append("'");
        }
        builder.append(" WHERE book_name LIKE '").append(book.getName()).append("' AND ");
        builder.append(" writer LIKE '").append(book.getWriter()).append("';");
        return builder.toString();
    }

    public static String updateUser(User user) {
        StringBuilder builder = new StringBuilder("UPDATE users SET ");
        builder.append("books = '").append(user.getBooksJSON()).append("' WHERE username LIKE ");
        builder.append("'").append(user.getUsername()).append("';");
        return builder.toString();
    }

    public static String deleteBook(Book book) {
        return "DELETE FROM books WHERE book_name LIKE '" + book.getName() + "' AND writer LIKE '" + book.getWriter() + "';";
    }

    public static String deleteBook(Map<String, String> cause) {
        StringBuilder builder = new StringBuilder("DELETE FROM books WHERE ");
        builder.append(createCause(cause)).append(";");
        return builder.toString();
    }

    public static String deleteUser(User user) {
        return "DELETE FROM users WHERE username LIKE '" + user.getUsername() + "';";
    }

    private static String createCause(Map<String, String> cause) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : cause.entrySet()) {
            builder.append(entry.getKey()).append(" LIKE '").append(entry.getValue()).append("' AND ");
        }
        builder.delete(builder.lastIndexOf(" AND"), builder.length());
        return builder.toString();
    }
}
