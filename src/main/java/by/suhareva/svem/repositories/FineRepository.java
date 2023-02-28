package by.suhareva.svem.repositories;

import by.suhareva.svem.db.model.Fine;
import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.exceptions.DAOException;

import java.util.Optional;

public interface FineRepository {
    Optional<Fine> getByNumber(GetRequest request) throws DAOException;
}
