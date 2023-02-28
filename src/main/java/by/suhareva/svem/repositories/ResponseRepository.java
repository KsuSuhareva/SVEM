package by.suhareva.svem.repositories;

import by.suhareva.svem.db.model.SendResponse;
import by.suhareva.svem.exceptions.DAOException;

import java.util.UUID;

public interface ResponseRepository {
    SendResponse save(SendResponse response) throws DAOException;
    SendResponse getByUuidRequest(UUID uuid_request) throws DAOException;
    void delete(UUID uuid) throws DAOException;
}
