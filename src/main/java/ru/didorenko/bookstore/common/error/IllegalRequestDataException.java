package ru.didorenko.bookstore.common.error;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg, ErrorType.BAD_REQUEST);
    }
}