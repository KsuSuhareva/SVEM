package by.suhareva.svem.repositories;

import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.exceptions.DAOException;

import java.util.Optional;
import java.util.UUID;


public interface RequestRepository {
    GetRequest save(GetRequest request) throws DAOException;

    Optional<GetRequest> getByMinDate() throws DAOException;

    void delete(UUID uuid) throws DAOException;
}
