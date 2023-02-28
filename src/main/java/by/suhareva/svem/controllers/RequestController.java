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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/request/")
public class RequestController {

    private final RequestService requestService;
    private final ResponseService responseService;

    @PostMapping("save/")
    public ResponseEntity<UUID> save(@Valid  @RequestBody GetRequest request) throws DAOException {
        UUID uuid = requestService.save(request);
        return new ResponseEntity<>(uuid, HttpStatus.ACCEPTED);
    }

    @PostMapping("getResponse/{id}")
    public ResponseEntity<SendResponse> getResponse(@PathVariable("id") UUID uuid) throws DAOException {
        SendResponse response = responseService.getResponse(uuid);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<String> deleteResponse(@PathVariable("id") UUID id) throws DAOException {
        responseService.deleteResponse(id);
        String message = "Response id=" + id + " deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}