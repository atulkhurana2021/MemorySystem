package Service;

public interface IContentManager {

    public void createFile(String filename, String content) throws Exception;

    public String readFile(String filename) throws Exception;

    public void updateFile(String filename, String content) throws Exception;

    public void deleteFile(String filename) throws Exception;

}
