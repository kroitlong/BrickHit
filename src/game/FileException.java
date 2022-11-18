package game;

public class FileException extends Exception {
    public FileException() {
        super();
    }

    public FileException(String message) {
        super(message);
    }

    public String getMessage(String fileName) {
        return fileName + "的路径不存在";
    }
}
