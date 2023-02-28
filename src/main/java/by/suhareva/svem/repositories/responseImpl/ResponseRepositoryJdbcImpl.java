package by.suhareva.svem.repositories.responseImpl;

import by.suhareva.svem.db.constants.SQLRequest;
import by.suhareva.svem.db.model.SendResponse;
import by.suhareva.svem.exceptions.DAOException;
import by.suhareva.svem.exceptions.ResponseNotFoundException;
import by.suhareva.svem.repositories.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static by.suhareva.svem.enums.StatusInBase.DELETE;

@Repository
@RequiredArgsConstructor
public class ResponseRepositoryJdbcImpl implements ResponseRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<SendResponse> rowMapper = new BeanPropertyRowMapper<>(SendResponse.class);

    @Override
    public SendResponse save(SendResponse response) throws DAOException {
        try {
            if (jdbcTemplate.update(SQLRequest.INSERT_INTO_RESPONSE, response.getUuid(), response.getUuid_request(),
                    response.getId_fine(), response.getNumber(), response.getType().toString(),response.getResolution_num(),
                    response.getResolution_date(), response.getAccrued(), response.getPaid(),
                    response.getDate(), response.getStatus().toString()) == 0)
                throw new DAOException("Not save response in table responses");
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
        return response;
    }

    @Override
    public SendResponse getByUuidRequest(UUID uuid_request) throws DAOException, ResponseNotFoundException {
        SendResponse response = null;
        try {
            response = jdbcTemplate.query(SQLRequest.SELECT_RESPONSE_BY_ID_REQ, rowMapper, uuid_request)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new ResponseNotFoundException("Trying to get response by uuid_request=" + uuid_request));
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
        return response;
    }

    @Override
    public void delete(UUID uuid) throws DAOException {
        try {
            if (jdbcTemplate.update(SQLRequest.UPDATE_RESPONSE_STATUS, DELETE.toString(), uuid) == 0)
                throw new DAOException("Not delete response in table responses");
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }
}
