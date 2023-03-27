package project.exception.handler;

public class NoValidException extends RuntimeException{
    public NoValidException(){
    }
    public NoValidException (String message){
        super(message);
    }
}
