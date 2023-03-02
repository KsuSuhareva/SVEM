package by.suhareva.svem.exception;

import by.suhareva.svem.exceptions.ResponseNotFoundException;
import by.suhareva.svem.repositories.ResponseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class ResponseServiceExceptions extends SvemExceptionsTest {
    @MockBean
    private ResponseRepository repository;

    @Test
    public void getResponseByIdRequestThrowResponseNotFoundException() throws Exception {
        UUID uuidRequest = UUID.randomUUID();
        Mockito.when(repository.getByUuidRequest(any(UUID.class))).thenThrow(ResponseNotFoundException.class);
        assertThrows(ResponseNotFoundException.class, () -> repository.getByUuidRequest(uuidRequest));
    }
}
