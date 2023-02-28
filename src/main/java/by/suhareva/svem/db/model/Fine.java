package by.suhareva.svem.db.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Fine {

    private UUID id;
    @NotEmpty
    @Pattern(regexp = "[0-9]{2}[A-z,0-9]{2}[0-9]{6}|[0-9]{9}")
    private String number;
    private int resolution_num;
    private Date resolution_date;
    private BigDecimal accrued;
    private  BigDecimal paid;

}
