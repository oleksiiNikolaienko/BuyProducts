package exception;

public class PurchaseServiceException extends RuntimeException {
    public PurchaseServiceException(String errorMessage) {
        super(errorMessage);
    }

    public PurchaseServiceException(Exception e) {
        super(e);
    }
}
