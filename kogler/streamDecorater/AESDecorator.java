package streamDecorater;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Collection;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by philippkogler on 14/12/16.
 */
public class AESDecorator extends StreamDecorator {
    private IvParameterSpec iv;
    private SecretKeySpec secretKey;
    private CoreChatStream inner;

    /**
     * {@link StreamDecorator} Constructor
     *
     * @param inner
     */
    public AESDecorator(CoreChatStream inner, SecretKeySpec secretKey, IvParameterSpec iv) {
        super(inner);
        this.inner = inner;
        this.secretKey = secretKey;
        this.iv = iv;
    }

    /**
     * @param o @{@link Object} Object to write
     * @throws IOException
     * @see ChatStream
     */
    @Override
    public void write(Object o) throws IOException {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, iv);

            byte[] encrypted = cipher.doFinal("hallo".getBytes());

            //System.out.println("encrypted string: "+ Base64.encodeBase64String(encrypted));

            this.inner.getOut().writeBytes(Base64.encodeBase64String(encrypted));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
