package shadow.atomic.library.model;

import lombok.*;

import java.io.Serializable;
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class client {
    private Integer idClient;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String CIN;

    @Override
    public String toString() {
        return "=============client==============" +"\n"+
                " idClient = " + idClient +"\n"+
                " firstName= " + firstName + '\n' +
                " lastName= " + lastName + '\n' +
                " email= " + email + '\n' +
                " phone = " + phone + '\n' +
                " CIN = " + CIN + "\n" ;
    }
}
