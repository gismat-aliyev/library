package az.aist.library.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookStatus {
    private Long statusId;
    private String statusName;
}
