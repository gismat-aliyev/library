package az.aist.library.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transactions {
    private Long trId;
    private Book books;
    private User user;
    private int status;
    private Date trDate;
}
