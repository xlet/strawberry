package cn.w.im.web.exceptions;

/**
 * to id is empty exception.
 */
public class ToIdIsEmptyException extends RuntimeException{
    public ToIdIsEmptyException(){
        super("to id is empty!");
    }
}
