package by.suhareva.svem.integrationTest;

import by.suhareva.svem.controllers.handler.ErrorMassage;
import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.db.model.SendResponse;
import by.suhareva.svem.integrationTest.MaperXmlJAXB.MapperXmlJAXB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RequestControllerXmlTest extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private XmlMapper mapperXml = new XmlMapper();

    @Test
    @Sql(statements = CLEAN_REQUEST, executionPhase = AFTER_TEST_METHOD)
    @Sql(statements = CLEAN_RESPONSES, executionPhase = AFTER_TEST_METHOD)
    public void saveValidRequestTestShouldReturnId() throws Exception {
        GetRequest requestExcept = new GetRequest(null, "12AA123456", INDIVIDUAL, new Date(), NEW);
        MvcResult mvcResult = mockMvc.perform(post("/request/save")
                        .accept(MediaType.TEXT_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(MapperXmlJAXB.writeValueAsString(requestExcept)))
                .andExpect(status().is(HttpStatus.ACCEPTED.value()))
                .andExpect(content().contentType("text/xml;charset=UTF-8"))
                .andDo(print())
                .andReturn();
        GetRequest requestActual = MapperXmlJAXB.readValueAsString(
                mvcResult.getResponse().getContentAsString(),
                GetRequest.class);
        RowMapper<GetRequest> rowMapper = new BeanPropertyRowMapper<>(GetRequest.class);
        GetRequest request = jdbcTemplate.query(SELECT_REQUEST_BY_ID, rowMapper, requestActual.getUuid()).get(0);
        assertEquals(requestActual.getUuid(), request.getUuid());
    }

    @Test()
    public void saveInvalidRequestShouldReturnCauseException() throws Exception {
        UUID uuid = UUID.randomUUID();
        GetRequest request = new GetRequest(uuid, "12AA1234566", INDIVIDUAL, new Date(), NEW);
        MvcResult mvcResult = mockMvc.perform(post("/request/save")
                        .accept(MediaType.TEXT_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(MapperXmlJAXB.writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType("text/xml;charset=UTF-8"))
                .andDo(print())
                .andReturn();
        ErrorMassage massageError = MapperXmlJAXB.readValueAsString(
                mvcResult.getResponse().getContentAsString(),
                ErrorMassage.class);
        System.out.println(massageError);
        assertEquals("MethodArgumentNotValidException", massageError.getCause());
    }

    @Test
    @Sql(statements = SAVE_RESPONSE, executionPhase = BEFORE_TEST_METHOD)
    @Sql(statements = CLEAN_RESPONSES, executionPhase = AFTER_TEST_METHOD)
    public void getResponseByIdRequest() throws Exception {
        UUID uuidRequest = UUID.fromString("f36cfbe4-98ca-4de5-8388-fad16d6796ba");
        GetRequest request = new GetRequest();
        request.setUuid(uuidRequest);
        MvcResult mvcResult = mockMvc.perform(post("/request/getResponse")
                        .accept(MediaType.TEXT_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(MapperXmlJAXB.writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentType("text/xml;charset=UTF-8"))
                .andDo(print())
                .andReturn();
        SendResponse response = MapperXmlJAXB.readValueAsString(
                mvcResult.getResponse().getContentAsString(),
                SendResponse.class);
        assertEquals(uuidRequest, response.getUuid_request());
        assertEquals(NEW, response.getStatus());
    }


    @Test
    @Sql(statements = SAVE_RESPONSE, executionPhase = BEFORE_TEST_METHOD)
    @Sql(statements = CLEAN_RESPONSES, executionPhase = AFTER_TEST_METHOD)
    public void deleteResponseShouldReturnMessage() throws Exception {
        UUID uuid = UUID.fromString("f36cfbe4-98ca-4de5-8388-fad16d6796ba");
        SendResponse sendResponse = new SendResponse();
        sendResponse.setUuid(uuid);
        mockMvc.perform(post("/request/delete")
                        .accept(MediaType.TEXT_XML_VALUE)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(MapperXmlJAXB.writeValueAsString(sendResponse)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$").value("Response id=" + uuid + " deleted"))
                .andDo(print())
                .andReturn();
        RowMapper<SendResponse> rowMapper = new BeanPropertyRowMapper<>(SendResponse.class);
        SendResponse response = jdbcTemplate.query(SELECT_RESPONSE_BY_ID, rowMapper, uuid).get(0);
        assertEquals(uuid, response.getUuid());
        assertEquals(DELETE, response.getStatus());
    }

}
