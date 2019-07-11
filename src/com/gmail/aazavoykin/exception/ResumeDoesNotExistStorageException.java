package com.gmail.aazavoykin.exception;

public class ResumeDoesNotExistStorageException extends StorageException{

    public ResumeDoesNotExistStorageException(String uuid) {
        super("Resume " + uuid + " doesn`t exist", uuid);
    }
}
