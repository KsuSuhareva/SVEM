package by.suhareva.svem.db.model;

import by.suhareva.svem.enums.ClientType;
import by.suhareva.svem.enums.StatusInBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@XmlRootElement(name  = "GetRequest")
@Schema(description = "The entity of the request to get the fine")
public class GetRequest {
    @Schema(description = "Universally unique identifier", example = "3027ac9e-42cf-433a-8f02-2ecde80d352e")
    private UUID uuid;

    @NotEmpty
    @Pattern(regexp = "[0-9]{2}[A-z,0-9]{2}[0-9]{6}|[0-9]{10}")
    @Schema(description = "Number STS or  identification number of taxpayer", example = "12AB123456")
    private String number;

    @Schema(description = "Сlient type ", example = "INDIVIDUAL")
    private ClientType type;

    @Schema(description = "The date the request was saved to the database", example = "2023-03-15 11:36:24.07")
    private Date date;

    @Schema(description = "Database request status ", example = "NEW")
    private StatusInBase status;


}
