package streamDecorater;

import client.Message;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by philippkogler on 14/12/16.
 */
public class AESDecorator extends StreamDecorator {
    private Cipher cipher;
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

        // Cretae Cipher
        try {
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param o @{@link Object} Object to write
     * @throws IOException
     * @see ChatStream
     * <p>
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
            System.out.println("encrypted string AES: " + Base64.encodeBase64String(encrypted));
            temp.setMessage(Base64.encodeBase64String(encrypted));
            this.inner.write(temp);
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
        Message<String> a = ((Message<String>) super.read());
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.iv);
            String enc = a.getMessage();
            byte[] original = this.cipher.doFinal(Base64.decodeBase64(enc));
            a.setMessage(new String(original));
            return a;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
