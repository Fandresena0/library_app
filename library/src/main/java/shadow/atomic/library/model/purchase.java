package shadow.atomic.library.model;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class purchase {
    private int idPurchase;
    private Date dateOfPurchase;
    private int price;
    private int idClient;

    @Override
    public String toString() {
        return "=============purchase==========" +"\n"+
                " idPurchase = " + idPurchase +"\n"+
                " dateOfPurchase=" + dateOfPurchase + "\n"+
                " idClient=" + idClient + '\n';
    }
}
