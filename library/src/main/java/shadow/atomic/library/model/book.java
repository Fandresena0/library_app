package shadow.atomic.library.model;

import lombok.*;

import java.io.Serializable;
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class book {
    private Integer idBook;
    private String title;
    private String description;
    private String autor;

    @Override
    public String toString() {
        return "============book===========" +"\n"+
                " idBook = " + idBook +"\n"+
                " title = " + title + "\n" +
                " description = " + description + "\n" +
                " autor = " + autor + "\n";
    }
}
