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

import client.Message;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by philippkogler on 14/12/16.
 */
public class AESDecorator extends StreamDecorator {
    private IvParameterSpec iv;
    private SecretKeySpec secretKey;
    private ChatStream inner;

    /**
     * {@link StreamDecorator} Constructor
     *
     * @param inner
     */
    public AESDecorator(ChatStream inner, SecretKeySpec secretKey, IvParameterSpec iv) {
        super(inner);
        this.inner = inner;
        this.secretKey = secretKey;
        this.iv = iv;
    }

    /**
     * @param o @{@link Object} Object to write
     * @throws IOException
     * @see ChatStream
     *
     * Write to inner {@link java.io.OutputStream} with encryption
     * AES
     */
    @Override
    public void write(Object o) throws IOException {
        Message<String> temp = (Message) o;
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, iv);

            byte[] encrypted = cipher.doFinal(temp.getMessage().getBytes());
            System.out.println("encrypted string AES: "+ Base64.encodeBase64String(encrypted));

            this.inner.write(new Message<String>(Base64.encodeBase64String(encrypted)));

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

    /**
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @see ChatStream
     */
    @Override
    public Object read() throws IOException, ClassNotFoundException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.iv);
            String enc = ((Message<String>)super.read()).getMessage();
            byte[] original = cipher.doFinal(Base64.decodeBase64(enc));

            return  (new String(original));
        } catch (Exception ex) {
            System.out.println("Client has disconnected");
        }
        return null;
    }
}
