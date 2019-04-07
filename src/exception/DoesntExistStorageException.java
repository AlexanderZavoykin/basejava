package exception;

public class DoesntExistStorageException extends StorageException{

    public DoesntExistStorageException(String uuid) {
        super("Resume " + uuid + " doesn`t exist", uuid);
    }
}
