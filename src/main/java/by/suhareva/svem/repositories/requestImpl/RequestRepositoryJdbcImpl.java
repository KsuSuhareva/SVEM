package by.suhareva.svem.repositories.requestImpl;

import by.suhareva.svem.db.constants.SQLRequest;
import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.exceptions.DAOException;
import by.suhareva.svem.repositories.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static by.suhareva.svem.enums.StatusInBase.DELETE;

@Repository
@RequiredArgsConstructor
public class RequestRepositoryJdbcImpl implements RequestRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<GetRequest> rowMapper = new BeanPropertyRowMapper<>(GetRequest.class);

    @Override
    public GetRequest save(GetRequest request) throws DAOException {
        try {
            jdbcTemplate.update(SQLRequest.INSERT_INTO_REQUEST, request.getUuid(), request.getNumber(),
                    request.getType().toString(), request.getDate(), request.getStatus().toString());
            jdbcTemplate.queryForObject(SQLRequest.SELECT_REQUEST_BY_UUID, rowMapper, request.getUuid());
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
        return request;
    }

    @Override
    public Optional<GetRequest> getByMinDate() throws DAOException {
        List<GetRequest> requests = null;
        try {
            requests = jdbcTemplate.query(SQLRequest.SELECT_REQUEST_BY_MIN_DATE, rowMapper);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
        return requests.size() == 0 ? Optional.empty() : Optional.of(requests.get(0));
    }

    @Override
    public void delete(UUID uuid) throws DAOException {
        try {
            if (jdbcTemplate.update(SQLRequest.UPDATE_REQUEST_STATUS, DELETE.toString(), uuid) == 0)
                throw new DAOException("Not delete request in table requests");
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }
}
