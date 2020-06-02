package vn.byt.qlds.repository;

import vn.byt.qlds.model.book.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepository {
    private static Map<Long, Book> books = new HashMap<>();

    static {
        books.put(1L, new Book(1L, "Hệ điều hành", "Phạm Đăng Hải"));
        books.put(2L, new Book(2L, "Toán rời rạc", "Phạm Thành Trung"));
        books.put(3L, new Book(3L, "Cơ sở dữ liệu", "Nguyễn Văn Hiệp"));
        books.put(4L, new Book(4L, "Lập trình java", "Trịnh Đức Trung"));
    }

    public Optional<Book> findById(long id) {
        return Optional.ofNullable(books.get(id));
    }

    public void add(Book book) {
        books.put(book.getId(), book);
    }

    public Collection<Book> getBooks() {
        return books.values();
    }

    public int size() {
        return books.size();
    }
}