package by.suhareva.svem.service.responseSericeImpl;

import by.suhareva.svem.db.model.SendResponse;
import by.suhareva.svem.exceptions.DAOException;
import by.suhareva.svem.repositories.ResponseRepository;
import by.suhareva.svem.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private  final ResponseRepository responseRepository;

    @Override
    public SendResponse getResponse(UUID uuid) throws DAOException {
        SendResponse response = responseRepository.getByUuidRequest(uuid);
        log.info("Response id={} received", response.getUuid());
        return response;
    }

    @Override
    public void deleteResponse(UUID uuid) throws DAOException {
        log.info("Response id={} deleted", uuid);
        responseRepository.delete(uuid);
    }
}
