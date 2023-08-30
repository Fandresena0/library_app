package shadow.atomic.library.model;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class borrowing {
    private int idBorrowing;
    private Date loanDate;
    private Date returnDate;
    private int idClient;

    @Override
    public String toString() {
        return "=============borrowing=============" +
                " idBorrowing = " + idBorrowing + "\n"+
                " loanDate=" + loanDate + "\n"+
                " returnDate=" + returnDate +"\n"+
                " idClient=" + idClient +"\n";
    }
}
