package Library.model;

import java.util.ArrayList;
import java.util.List;

public class Writers {
    private String name;
    private List<Book> books;

    public Writers(String name) {
        this.name = name;
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "{ name: " + name + ", books: " + books.toString() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Writers)) return false;
        Writers writers = (Writers) o;
        return writers.name.equals(name);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode();
    }
}
