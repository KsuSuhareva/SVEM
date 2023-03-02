package by.suhareva.svem.integrationTest;

import liquibase.pro.packaged.S;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrationTest {


    public static final String SELECT_REQUEST_BY_ID = "SELECT UUID,NUMBER,DATE,STATUS FROM REQUESTS WHERE UUID =?";
    public static final String SAVE_RESPONSE="INSERT INTO RESPONSES (UUID, UUID_REQUEST, ID_FINE, NUMBER,TYPE, RESOLUTION_NUM, RESOLUTION_DATE, ACCRUED, PAID, DATE, STATUS )" +
            " VALUES('f36cfbe4-98ca-4de5-8388-fad16d6796ba','f36cfbe4-98ca-4de5-8388-fad16d6796ba','f36cfbe4-98ca-4de5-8388-fad16d6796ba','12AA123455','INDIVIDUAL',123456,'2022-12-17',1200,1200,'2023-02-02','NEW')";
    public static final String SELECT_RESPONSE_BY_ID="SELECT  UUID ,UUID_REQUEST ,ID_FINE ,NUMBER ,TYPE ,RESOLUTION_NUM ,RESOLUTION_DATE ,ACCRUED ,PAID ,DATE ,STATUS , FROM RESPONSES WHERE  UUID=?";
    public static final String CLEAN_REQUEST = "DELETE FROM REQUESTS";
    public static final String CLEAN_RESPONSES = "DELETE FROM RESPONSES";


}
