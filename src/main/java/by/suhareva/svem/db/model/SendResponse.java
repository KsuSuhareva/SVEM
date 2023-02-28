package by.suhareva.svem.db.model;

import by.suhareva.svem.enums.StatusInBase;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SendResponse {

    private UUID uuid;
    private UUID uuid_request;
    private UUID id_fine;
    @NotEmpty
    @Pattern(regexp = "[0-9]{2}[A-z,0-9]{2}[0-9]{6}|[0-9]{10}")
    private String number;
    private Integer resolution_num;
    private Date resolution_date;
    private BigDecimal accrued;
    private  BigDecimal paid;
    private Date date;
    private StatusInBase status;

}
