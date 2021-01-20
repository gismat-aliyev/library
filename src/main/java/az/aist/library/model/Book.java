package az.aist.library.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book {
    private Long bookId;
    private String bookName;
    private String author;
    private String about;
    private Date addedDate;
    private Date createDate;
    private BookStatus status;
}
