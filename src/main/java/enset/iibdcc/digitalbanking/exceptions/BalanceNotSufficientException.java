package enset.iibdcc.digitalbanking.exceptions;

public class BalanceNotSufficientException extends Exception{

    public BalanceNotSufficientException(String message){
        super(message);
    }
}
