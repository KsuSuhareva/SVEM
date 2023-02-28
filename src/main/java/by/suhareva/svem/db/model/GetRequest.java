package by.suhareva.svem.db.model;

import by.suhareva.svem.enums.ClientType;
import by.suhareva.svem.enums.StatusInBase;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GetRequest {

    private UUID uuid;
    @NotEmpty
    @Pattern(regexp = "[0-9]{2}[A-z,0-9]{2}[0-9]{6}|[0-9]{10}")
    private String number;
    private ClientType type;
    private Date date;
    private StatusInBase status;


}
