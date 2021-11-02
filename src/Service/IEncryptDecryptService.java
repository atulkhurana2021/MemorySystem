package Service;

public interface IEncryptDecryptService {

    public byte[] encryptFileData(byte[] data) throws Exception;

    public byte[] decryptFileData(byte[] data) throws Exception;
}
