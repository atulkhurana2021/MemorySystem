package Repository;

import Entities.Block;
import Entities.FileMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Repository {


    // The approach used here is to divide given memory into blocks and use those blocks while assign/removing memory

    private byte[] memorySpace;
    private List<Block> dataBlocks = new ArrayList<>();
    private Map<String, FileMetadata> fileMetadataMap = new ConcurrentHashMap<>();
    private Integer sizeOfBlock;

    public Repository(int n, int sizeOfBlock) {
        this.sizeOfBlock = sizeOfBlock;
        memorySpace = new byte[n];
        int noOfBlocks = n / sizeOfBlock;
        for (int i = 0; i < noOfBlocks; i++) {
            dataBlocks.add(new Block(true, i * sizeOfBlock));
        }
    }

    public List<Block> getDataBlocks() {
        return dataBlocks;
    }

    public Integer getSizeOfBlock() {
        return sizeOfBlock;
    }

    public void addFileMetadata(String filename, FileMetadata metadata) {
        fileMetadataMap.put(filename, metadata);
    }

    public FileMetadata getFileMetadata(String fileName) {
        return fileMetadataMap.get(fileName);
    }

    public void removeFileMetadata(String fileName) {
        fileMetadataMap.remove(fileName);
    }

    public byte[] getMemorySpace() {
        return memorySpace;
    }

    public void setMemorySpace(byte[] memorySpace) {
        this.memorySpace = memorySpace;
    }
}
