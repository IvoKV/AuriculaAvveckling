package auxilliary;

public class FileOperations {

    private String rawFilename;

    public FileOperations(String absolutePath){
        this.rawFilename = absolutePath;
    }

    public String getFilenameWithoutExtension(){
        String fileNameWithoutExtension = rawFilename.replaceAll("\\.\\w+$", "");
        return fileNameWithoutExtension;
    }
}
