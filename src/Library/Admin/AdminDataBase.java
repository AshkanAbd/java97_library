package Library.Admin;

import Library.database.DataBase;
import Library.model.Book;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class AdminDataBase extends DataBase {

    public AdminDataBase(String username, String password, String database) {
        super(username, password, database);
    }

    public boolean insertBook(Book book) {
        try {
            String query = "INSERT INTO books(book_name, writer) VALUE(" + book.getName() + "," + book.getWriter() + ");";
            runQuery(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void verifyBooks() throws SQLException {
        String query = "SELECT * FROM books;";
        ResultSet result = runQuery(query);
        Date sqlDate = null;
        java.util.Date today = Calendar.getInstance().getTime();
        while (result.next()) {
            sqlDate = result.getDate("free_date");
            if (sqlDate.before(today)) {
                String bookName = result.getString("book_name");
                String writer = result.getString("writer");
                String owner = result.getString("owner");
                freeBook(bookName, writer, owner);
            }
        }
    }

    public void freeBook(String bookName, String writer, String owner) {
        Book book = new Book(bookName, writer);
        try {
            updateBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateBook(Book book) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO books(book_name, writer");
        if (book.getOwner() != null) {
            queryBuilder.append(", owner");
        }
        if (book.getFreeDate() != null) {
            queryBuilder.append(", free_date");
        }
        queryBuilder.append(") VALUES(").append(book.getName()).append(", ").append(book.getWriter());
        if (book.getOwner() != null) {
            queryBuilder.append(", ").append(book.getOwner());
        }
        if (book.getFreeDate() != null) {
            queryBuilder.append(", ").append(book.getFreeDate());
        }
        queryBuilder.append(");");
        String query = queryBuilder.toString();
        runQuery(query);
    }
}
