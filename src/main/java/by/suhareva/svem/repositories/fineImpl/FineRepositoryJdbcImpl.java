package by.suhareva.svem.repositories.fineImpl;

import by.suhareva.svem.db.constants.SQLRequest;
import by.suhareva.svem.db.model.Fine;
import by.suhareva.svem.db.model.GetRequest;
import by.suhareva.svem.enums.ClientType;
import by.suhareva.svem.exceptions.DAOException;
import by.suhareva.svem.repositories.FineRepository;
import liquibase.pro.packaged.F;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FineRepositoryJdbcImpl implements FineRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Fine> rowMapper = new BeanPropertyRowMapper<>(Fine.class);

    @Override
    public Optional<Fine> getByNumber(GetRequest request) throws DAOException {
        List<Fine> fines = null;
        try {
            fines = jdbcTemplate.query(SQLRequest.SELECT_FINE_BY_NUMBER, rowMapper, request.getNumber(), request.getType().toString());
        } catch (DataAccessException e) {
            throw  new DAOException(e);
        }
        return fines.size() == 0 ? Optional.empty() : Optional.of(fines.get(0));
    }
}
