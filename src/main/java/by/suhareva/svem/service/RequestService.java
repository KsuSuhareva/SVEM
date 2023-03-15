package by.suhareva.svem.service;

import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.exceptions.DAOException;

public interface RequestService {
    GetRequest save(GetRequest request) throws DAOException;


}
