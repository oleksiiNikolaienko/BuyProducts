package exception;

public class JdbcDaoException extends RuntimeException {

    public JdbcDaoException(String errorMessage) {
        super(errorMessage);
    }

    public JdbcDaoException(Exception e) {
        super(e);
    }
}
