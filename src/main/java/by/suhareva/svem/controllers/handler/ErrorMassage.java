package by.suhareva.svem.controllers.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@XmlRootElement(name  = "ErrorMassage")
@ToString
public class ErrorMassage {
    @Schema(description = "The full date the error occurred", example = "2023-03-15 11:36:24.07")
    private Date date;

    @Schema(description = "HTTP status", example = "404")
    private Integer status;

    @Schema(description = "ErrorMassage", example = "Trying to get response by uuid_request = 3027ac9e-42cf-433a-8f02-2ecde80d352e")
    private String massage;

    @Schema(description = "Error reason", example = "ResponseNotFoundException")
    private String cause;
}
