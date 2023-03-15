package by.suhareva.svem.controllers;

import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.db.model.SendResponse;
import by.suhareva.svem.exceptions.DAOException;
import by.suhareva.svem.service.RequestService;
import by.suhareva.svem.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/request/")
public class RequestController {

    private final RequestService requestService;
    private final ResponseService responseService;

    @PostMapping("save")
    public ResponseEntity<GetRequest> save(@Valid  @RequestBody GetRequest request) throws DAOException {
        GetRequest getRequest = requestService.save(request);
        return new ResponseEntity<>(getRequest, HttpStatus.ACCEPTED);
    }

    @PostMapping("getResponse")
    public ResponseEntity<SendResponse> getResponse(@RequestBody GetRequest request) throws DAOException {
        SendResponse response = responseService.getResponse(request.getUuid());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<String> deleteResponse(@RequestBody SendResponse response) throws DAOException {
        responseService.deleteResponse(response.getUuid());
        String message = "Response id=" + response.getUuid() + " deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}