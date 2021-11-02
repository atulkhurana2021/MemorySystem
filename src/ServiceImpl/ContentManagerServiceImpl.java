package ServiceImpl;

import Entities.Block;
import Entities.FileMetadata;
import Repository.Repository;
import Service.IContentManager;
import Service.IEncryptDecryptService;

import java.util.List;

public class ContentManagerServiceImpl implements IContentManager {
    private IEncryptDecryptService iEncryptDecryptService;
    private Repository repository;
    private ContentManagerHelper contentManagerHelper;

    public ContentManagerServiceImpl(IEncryptDecryptService iEncryptDecryptService, Repository repository) {
        this.iEncryptDecryptService = iEncryptDecryptService;
        this.repository = repository;
        this.contentManagerHelper = new ContentManagerHelper(repository);
    }

    public void createFile(String filename, String content) throws Exception {
        if (repository.getFileMetadata(filename) != null)
            throw new Exception("File already exists");
        byte[] encryptedData = iEncryptDecryptService.encryptFileData(content.getBytes());
        int size = encryptedData.length;
        List<Block> blocks = contentManagerHelper.getBlocksForSize(size);
        if (blocks.size() == 0) {
            throw new Exception("Memory Full");
        } else {
            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.setSize(size);
            fileMetadata.setBlockList(blocks);
            contentManagerHelper.initializeByteArray(blocks, encryptedData);
            repository.addFileMetadata(filename, fileMetadata);
        }
    }

    public String readFile(String filename) throws Exception {
        if (repository.getFileMetadata(filename) == null)
            throw new Exception("File does not exists");
        FileMetadata fileMetadata = repository.getFileMetadata(filename);
        byte encryptedData[] = contentManagerHelper.readFromByteArray(fileMetadata.getBlockList(), fileMetadata.getSize());
        byte decryptedData[] = iEncryptDecryptService.decryptFileData(encryptedData);
        return new String(decryptedData);
    }

    public void updateFile(String filename, String content) throws Exception {
        if (repository.getFileMetadata(filename) == null)
            throw new Exception("File does not exists");
        FileMetadata fileMetadata = repository.getFileMetadata(filename);
        byte[] encryptedData = iEncryptDecryptService.encryptFileData(content.getBytes());
        int size = encryptedData.length;
        List<Block> extraBlocks = null;
        if (size > fileMetadata.getSize()) {
            extraBlocks = contentManagerHelper.getBlocksForSize(size - fileMetadata.getSize());
            if (extraBlocks.size() == 0) {
                throw new Exception("Memory Full");
            }
            for (Block block : extraBlocks) {
                fileMetadata.getBlockList().add(block);
            }
        }
        fileMetadata.setSize(size);
        contentManagerHelper.initializeByteArray(fileMetadata.getBlockList(), encryptedData);
    }


    public void deleteFile(String filename) throws Exception {
        if (repository.getFileMetadata(filename) == null)
            throw new Exception("File does not exists");
        FileMetadata fileMetadata = repository.getFileMetadata(filename);
        contentManagerHelper.freeBlocks(fileMetadata.getBlockList());
        repository.removeFileMetadata(filename);
    }
}
