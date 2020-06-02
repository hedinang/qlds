package vn.byt.qlds.model.book;

import javax.validation.constraints.NotEmpty;

public class BookRequest {
    @NotEmpty(message = "Title must not be empty!")
    public String title;
    @NotEmpty(message = "Auth must not be empty!")
    public String author;
}
