package by.suhareva.svem.service;

import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.exceptions.DAOException;

import java.util.UUID;

public interface RequestService {
    UUID save(GetRequest request) throws DAOException;


}
