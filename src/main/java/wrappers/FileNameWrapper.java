package wrappers;

public class FileNameWrapper {

    public String fileName;

    public FileNameWrapper() {
    }

    public FileNameWrapper(String fileName) {
        super();
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "FileNameWrapper [fileName=" + fileName + "]";
    }
}
