package by.suhareva.svem.integrationTest;

import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.db.model.SendResponse;
import by.suhareva.svem.repositories.ResponseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.UUID;

import static by.suhareva.svem.enums.ClientType.INDIVIDUAL;
import static by.suhareva.svem.enums.StatusInBase.DELETE;
import static by.suhareva.svem.enums.StatusInBase.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class WorkerThreadTest extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<GetRequest> rowMapper = new BeanPropertyRowMapper<>(GetRequest.class);

    @Test
    @Sql(statements = CLEAN_REQUEST, executionPhase = AFTER_TEST_METHOD)
    @Sql(statements = CLEAN_RESPONSES, executionPhase = AFTER_TEST_METHOD)
    public void workerRunTest() throws Exception {
        GetRequest requestExpect = new GetRequest(null, "12AB123456", INDIVIDUAL,new Date(), NEW);
        MvcResult mvcResult = mockMvc.perform(post("/request/save/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestExpect)))
                .andExpect(status().is(HttpStatus.ACCEPTED.value()))
                .andDo(print())
                .andReturn();
        UUID uuid = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                UUID.class);
        Thread.sleep(100);
        SendResponse response = responseRepository.getByUuidRequest(uuid);
        assertEquals(uuid, response.getUuid_request());
        assertEquals(requestExpect.getNumber(), response.getNumber());
        assertEquals(requestExpect.getStatus(), response.getStatus());

        GetRequest requestActual = jdbcTemplate.query(SELECT_REQUEST_BY_ID, rowMapper, uuid).get(0);
        assertEquals(DELETE, requestActual.getStatus());
        assertEquals(requestExpect.getNumber(), requestActual.getNumber());
        assertEquals(uuid, requestActual.getUuid());
    }
}
