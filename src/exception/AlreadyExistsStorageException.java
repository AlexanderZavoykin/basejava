package exception;

public class AlreadyExistsStorageException extends StorageException {

    public AlreadyExistsStorageException(String uuid) {
        super("Resume " + uuid + " already exists", uuid);
    }
}
