package by.suhareva.svem.service;

import by.suhareva.svem.db.model.SendResponse;
import by.suhareva.svem.exceptions.DAOException;

import java.util.UUID;

public interface ResponseService {
    SendResponse getResponse(UUID uuid) throws DAOException;
    void deleteResponse(UUID id) throws DAOException;
}
