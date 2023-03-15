package by.suhareva.svem.service.requestServiceImpl;

import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.exceptions.DAOException;
import by.suhareva.svem.repositories.RequestRepository;
import by.suhareva.svem.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static by.suhareva.svem.enums.StatusInBase.NEW;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Override
    public GetRequest save(GetRequest request) throws DAOException {
        request.setUuid(UUID.randomUUID());
        request.setDate(new Date());
        request.setStatus(NEW);
        request = requestRepository.save(request);

        log.info("Request saved with uuid={}", request.getUuid());
        return request;
    }

}
