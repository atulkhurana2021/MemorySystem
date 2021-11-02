package ServiceImpl;

import Entities.Block;
import Repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ContentManagerHelper {

    private Repository repository;

    public ContentManagerHelper(Repository repository) {
        this.repository = repository;
    }

    public List<Block> getBlocksForSize(int contentLength) {
        int size = contentLength;
        List<Block> blocks = new ArrayList<>();
        for (Block block : repository.getDataBlocks()) {
            if (size <= 0)
                break;
            if (block.isAvailable()) {
                synchronized (block) {
                    block.setAvailable(false);
                    blocks.add(block);
                    size -= repository.getSizeOfBlock();
                }

            }

        }
        if (size > 0) {
            freeBlocks(blocks);
            return new ArrayList<>();
        } else
            return blocks;
    }

    public void freeBlocks(List<Block> blocks) {
        if (blocks != null) {
            for (Block block :
                    blocks) {
                block.setAvailable(true);
            }
        }
    }

    public void initializeByteArray(List<Block> blocks, byte[] data) {
        int len = data.length;
        int index = 0;
        if (blocks != null) {
            for (Block block : blocks) {

                for (int i = 0; i < repository.getSizeOfBlock(); i++) {
                    if (index >= len) {
                        return;
                    }
                    repository.getMemorySpace()[block.getStartIndex() + i] = data[index++];
                }

            }

        }

    }

    public byte[] readFromByteArray(List<Block> blocks, int size) {
        int index = 0;
        byte[] data = new byte[size];
        if (blocks != null) {
            for (Block block : blocks) {

                for (int i = 0; i < repository.getSizeOfBlock(); i++) {
                    if (index >= size) {
                        break;
                    }
                    data[index++] = repository.getMemorySpace()[block.getStartIndex() + i];
                }

            }

        }
        return data;
    }
}
