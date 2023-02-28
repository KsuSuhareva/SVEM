package by.suhareva.svem.integrationTest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrationTest {


    public static final String SELECT_REQUEST_BY_ID = "SELECT UUID,NUMBER,DATE,STATUS FROM REQUESTS WHERE UUID =?";
    public static final String CLEAN_REQUEST = "DELETE FROM REQUESTS";
    public static final String CLEAN_RESPONSES = "DELETE FROM RESPONSES";


}
