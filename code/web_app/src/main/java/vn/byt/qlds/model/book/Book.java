package vn.byt.qlds.model.book;

import vn.byt.qlds.model.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Book extends Entity {
    public Book(Long id, String title, String author) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
    }

    private long id;
    private String title;
    private String author;
}