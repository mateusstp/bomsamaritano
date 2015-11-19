package DAO;

public class BancoDAOException extends Exception  {
    
    public BancoDAOException( ) {
    }
    
    public BancoDAOException(String arg) {
        super(arg);
    }
    
    public BancoDAOException(Throwable arg) {
        super(arg);
    }
    
    public BancoDAOException(String arg, Throwable arg1) {
        super(arg, arg1);
    }
}
