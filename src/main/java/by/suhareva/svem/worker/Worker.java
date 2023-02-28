package by.suhareva.svem.worker;

import by.suhareva.svem.db.model.Fine;
import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.db.model.SendResponse;
import by.suhareva.svem.exceptions.DAOException;
import by.suhareva.svem.repositories.FineRepository;
import by.suhareva.svem.repositories.RequestRepository;
import by.suhareva.svem.repositories.ResponseRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static by.suhareva.svem.enums.StatusInBase.NEW;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public class Worker implements Runnable {

    private final RequestRepository requestRepository;
    private final FineRepository fineRepository;
    private final ResponseRepository responseRepository;


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        while (true) {
            try {
                Optional<GetRequest> optionalRequest = requestRepository.getByMinDate();
                if (optionalRequest.isPresent()) {
                    GetRequest request = optionalRequest.get();
                    Optional<Fine> fine = fineRepository.getByNumber(request);

                    SendResponse response = getResponse(optionalRequest, fine);
                    responseRepository.save(response);

                    requestRepository.delete(request.getUuid());
                }
                Thread.sleep(100);
            } catch (DAOException e) {
                log.error("DAOException with cause: {}", e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private SendResponse getResponse(Optional<GetRequest> request, Optional<Fine> fine) {
        if (fine.isPresent()) {
            return createResponse(request, fine);
        } else {
            return createResponse(request);
        }
    }

    private SendResponse createResponse(Optional<GetRequest> optionalRequest, Optional<Fine> optionalFine) {
        GetRequest request = optionalRequest.get();
        Fine fine = optionalFine.get();
        SendResponse response = SendResponse.builder()
                .uuid(UUID.randomUUID())
                .uuid_request(request.getUuid())
                .id_fine(fine.getId())
                .number(fine.getNumber())
                .type(fine.getType())
                .resolution_num(fine.getResolution_num())
                .resolution_date(fine.getResolution_date())
                .accrued(fine.getAccrued())
                .paid(fine.getPaid())
                .date(new Date())
                .status(NEW).build();
        return response;
    }

    private SendResponse createResponse(Optional<GetRequest> optionalRequest) {
        GetRequest request = optionalRequest.get();
        SendResponse response = SendResponse.builder()
                .uuid(UUID.randomUUID())
                .uuid_request(request.getUuid())
                .date(new Date())
                .status(NEW).build();
        return response;
    }
}