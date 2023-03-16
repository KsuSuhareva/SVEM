package by.suhareva.svem.db.model;

import by.suhareva.svem.enums.ClientType;
import by.suhareva.svem.enums.StatusInBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@XmlRootElement(name  = "SendResponse")
public class SendResponse {

    @Schema(description = "Universally unique identifier for response", example = "3027ac9e-42cf-433a-8f02-2ecde80d352e")
    private UUID uuid;

    @Schema(description = "Universally unique identifier for request", example = "3027ac9e-42cf-433a-8f02-2ecde80d352e")
    private UUID uuid_request;

    @Schema(description = "Universally unique identifier for fine", example = "3027ac9e-42cf-433a-8f02-2ecde80d352e")
    private UUID id_fine;


    @NotEmpty
    @Pattern(regexp = "[0-9]{2}[A-z,0-9]{2}[0-9]{6}|[0-9]{10}")
    @Schema(description = "Number STS or  identification number of taxpayer", example = "12AB123456")
    private String number;

    @Schema(description = "Resolution number", example = "12345678")
    private Integer resolution_num;

    @Schema(description = "Сlient type", example = "INDIVIDUAL")
    private ClientType type;

    @Schema(description = "Resolution date", example = "2022-12-17 00:00:00")
    private Date resolution_date;

    @Schema(description = "The amount of the fine in Russian rubles", example = "1200")
    private BigDecimal accrued;

    @Schema(description = "The amount of the fine paid in Russian rubles", example = "1200")
    private BigDecimal paid;

    @Schema(description = "The date the request was saved to the database", example = "2023-03-15 11:36:24.07")
    private Date date;

    @Schema(description = "Database request status ", example = "NEW")
    private StatusInBase status;

}
