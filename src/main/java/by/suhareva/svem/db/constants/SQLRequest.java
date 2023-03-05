package by.suhareva.svem.db.constants;

public class SQLRequest {

    public static final String SELECT_REQUEST_BY_MIN_DATE =
            "SELECT UUID, DATE, NUMBER, TYPE, STATUS FROM REQUESTS WHERE DATE =(SELECT MIN(DATE) from REQUESTS WHERE REQUESTS.STATUS ='NEW')";
    public static final String UPDATE_REQUEST_STATUS =
            "UPDATE REQUESTS SET STATUS =? WHERE UUID=?";
    public static final String INSERT_INTO_REQUEST =
            "INSERT INTO REQUESTS (UUID ,NUMBER , TYPE, DATE ,STATUS ) VALUES (?,?,?,?,?)";
    public static final String SELECT_FINE_BY_NUMBER =
            "select fines.id, fines.number, fines.type, fines.resolution_num, fines.resolution_date, fines.accrued, fines.paid from fines where fines.number=? and fines.type=?";
    public static final String SELECT_RESPONSE_BY_ID_REQ =
            "SELECT RESPONSES.UUID, RESPONSES.UUID_REQUEST, RESPONSES.ID_FINE, RESPONSES.NUMBER, RESPONSES.TYPE, RESPONSES.RESOLUTION_DATE , RESPONSES.RESOLUTION_NUM, RESPONSES.ACCRUED , RESPONSES.PAID, RESPONSES.DATE, RESPONSES.STATUS, FROM RESPONSES WHERE RESPONSES.UUID_REQUEST=?";
    public static final String UPDATE_RESPONSE_STATUS =
            "UPDATE RESPONSES SET STATUS =? WHERE UUID=?";
    public static final String INSERT_INTO_RESPONSE =
            "INSERT INTO RESPONSES (UUID, UUID_REQUEST, ID_FINE, NUMBER, TYPE, RESOLUTION_NUM, RESOLUTION_DATE, ACCRUED, PAID, DATE, STATUS) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    public static final String SELECT_REQUEST_BY_UUID = "SELECT UUID, NUMBER, DATE, STATUS  FROM REQUESTS WHERE UUID =?";
}
