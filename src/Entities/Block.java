package Entities;

public class Block {
    private boolean isAvailable;
    private int  startIndex;

    public Block(boolean isAvailable, int startIndex) {
        this.isAvailable = isAvailable;
        this.startIndex = startIndex;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
