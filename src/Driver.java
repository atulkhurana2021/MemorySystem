import Repository.Repository;
import Service.IContentManager;
import Service.IEncryptDecryptService;
import ServiceImpl.ContentManagerServiceImpl;
import ServiceImpl.EncryptionDecryptionServiceImpl;

public class Driver {

    public static void main(String[] args) throws Exception {

        // Sanity  testcases
        Repository repository = new Repository(100, 5);
        IEncryptDecryptService encryptDecryptService = new EncryptionDecryptionServiceImpl();
        IContentManager iContentManager = new ContentManagerServiceImpl(encryptDecryptService, repository);

        iContentManager.createFile("ABC", "HelloWord");

        //iContentManager.createFile("ABC", "HelloWord"); //File Already exists

        System.out.println(iContentManager.readFile("ABC")); // prints HelloWord

        iContentManager.deleteFile("ABC");
        //System.out.println(iContentManager.readFile("ABC")); // File does not exists

        iContentManager.createFile("ABC", "HelloWord");

        iContentManager.updateFile("ABC", "HelloWord123");

        iContentManager.createFile("ABCE", "HelloWord");

        System.out.println(iContentManager.readFile("ABC")); // prints HelloWord123

        iContentManager.readFile("ABCE");


        //Memory validation cases
        System.out.println("Memory validation testcases 1");

         repository = new Repository(10, 5);
        encryptDecryptService = new EncryptionDecryptionServiceImpl();
        iContentManager = new ContentManagerServiceImpl(encryptDecryptService, repository);

       //iContentManager.createFile("ABC", "HelloWord");
        // gives Memory Full error reason being the system saves encryped form of string  which will be of length 16

        /*
        e.g.

        HelloWord > byte conversion  [72, 101, 108, 108, 111, 87, 111, 114, 100]
        [72, 101, 108, 108, 111, 87, 111, 114, 100] > encrypted version > [-58, 120, -89, 38, 113, 71, 62, 33, -27, -9, 120, -57, 74, -89, -53, 70]
         hence encrypted version of HelloWord byte array is 16 length
         */

        System.out.println("Memory validation testcases 2");

        repository = new Repository(20, 5); //since the block is 5 memory needs to be a multiple of block
        encryptDecryptService = new EncryptionDecryptionServiceImpl();
        iContentManager = new ContentManagerServiceImpl(encryptDecryptService, repository);
        iContentManager.createFile("ABC", "HelloWord");
        System.out.println(iContentManager.readFile("ABC"));







    }
}
