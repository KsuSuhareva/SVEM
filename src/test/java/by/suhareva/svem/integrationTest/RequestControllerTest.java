package by.suhareva.svem.integrationTest;

import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.db.model.SendResponse;
import by.suhareva.svem.exceptions.ResponseNotFoundException;
import by.suhareva.svem.repositories.RequestRepository;
import by.suhareva.svem.repositories.ResponseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static by.suhareva.svem.enums.ClientType.INDIVIDUAL;
import static by.suhareva.svem.enums.StatusInBase.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RequestControllerTest extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RequestRepository requestRepository;
    @MockBean
    private ResponseRepository repository;

    @Test
    public void saveValidRequestTestShouldReturnId() throws Exception {
        UUID uuid = UUID.randomUUID();
        GetRequest request = new GetRequest(uuid, "12AA123456", INDIVIDUAL, new Date(), NEW);
        Mockito.when(requestRepository.save(any(GetRequest.class))).thenReturn(request);
        MvcResult mvcResult = mockMvc.perform(post("/request/save/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.ACCEPTED.value()))
                .andDo(print())
                .andReturn();
        UUID id = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                UUID.class);
        assertEquals(uuid, id);
    }

    @Test()
    public void saveInvalidRequestShouldReturnCauseException() throws Exception {
        UUID uuid = UUID.randomUUID();
        GetRequest request = new GetRequest(uuid, "12AA1234566", INDIVIDUAL, new Date(), NEW);

        Mockito.when(requestRepository.save(request)).thenReturn(request);
        mockMvc.perform(post("/request/save/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.cause").value("MethodArgumentNotValidException"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void getResponseByIdRequest() throws Exception {
        UUID uuidRequest = UUID.randomUUID();
        SendResponse responseTest = new SendResponse(UUID.randomUUID(), uuidRequest, UUID.randomUUID(), "12AA123455",INDIVIDUAL, 123456, new Date(), new BigDecimal(2000.0), new BigDecimal(2000.0), new Date(), NEW);
        Mockito.when(repository.getByUuidRequest(any(UUID.class))).thenReturn(responseTest);

        MvcResult mvcResult = mockMvc.perform(post("/request/getResponse/{id}", uuidRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(uuidRequest)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andDo(print())
                .andReturn();
        SendResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                SendResponse.class);
        assertEquals(uuidRequest, response.getUuid_request());
        assertEquals(NEW, response.getStatus());
    }

    @Test
    public void getResponseByIdRequestThrowResponseNotFoundException() throws Exception {
        UUID uuidRequest = UUID.randomUUID();
        Mockito.when(repository.getByUuidRequest(any(UUID.class))).thenThrow(ResponseNotFoundException.class);
        assertThrows(ResponseNotFoundException.class, () -> repository.getByUuidRequest(uuidRequest));

    }

    @Test
    public void deleteResponseShouldReturnMessage() throws Exception {
        UUID uuid = UUID.randomUUID();
        Mockito.doNothing().when(repository).delete(any(UUID.class));
        mockMvc.perform(post("/request/delete/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(uuid)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$").value("Response id=" + uuid + " deleted"))
                .andDo(print())
                .andReturn();
    }

}
