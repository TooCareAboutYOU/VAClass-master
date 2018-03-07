package com.dts.vaclass.utils;

import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zs on 2018/2/8.
 * 对称Aes效率比较高
 */

public class AESUtils {
    private final static String HEX = "0123456789ABCDEF";
    private  static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private  static final String AES = "AES";//AES 加密
    private  static final String  SHA1PRNG="SHA1PRNG";//// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法

    /*
     * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
     */
    public static String generateKey() {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytes_key = new byte[20];
            localSecureRandom.nextBytes(bytes_key);
            String str_key = toHex(bytes_key);
            return str_key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 对密钥进行处理
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        //for android
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance(SHA1PRNG, "Crypto");
        } else {
            sr = SecureRandom.getInstance(SHA1PRNG);
        }
        // for Java
        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
        sr.setSeed(seed);
        kgen.init(128, sr); //256 bits or 128 bits,192bits
        //AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    /*
     * 加密
     */
    public static String encrypt(String key, String cleartext) {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext;
        }
        try {
            byte[] result = encrypt(key, cleartext.getBytes());
            return Base64Encoder.encode(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 加密
    */
    private static byte[] encrypt(String key, byte[] clear) throws Exception {
        byte[] raw = getRawKey(key.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }


    /*
     * 解密
     */
    public static String decrypt(String key, String encrypted) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            byte[] enc = Base64Decoder.decodeToBytes(encrypted);
            byte[] result = decrypt(key, enc);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 解密
     */
    private static byte[] decrypt(String key, byte[] encrypted) throws Exception {
        byte[] raw = getRawKey(key.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    //二进制转字符
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    static class Base64Decoder extends FilterInputStream {

        private static final char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
                'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

        // A mapping between char values and six-bit integers
        private static final int[] ints = new int[128];
        static {
            for (int i = 0; i < 64; i++) {
                ints[chars[i]] = i;
            }
        }

        private int charCount;
        private int carryOver;

        /***
         * Constructs a new Base64 decoder that reads input from the given
         * InputStream.
         *
         * @param in
         *            the input stream
         */
        private Base64Decoder(InputStream in) {
            super(in);
        }

        /***
         * Returns the next decoded character from the stream, or -1 if end of
         * stream was reached.
         *
         * @return the decoded character, or -1 if the end of the input stream is
         *         reached
         * @exception IOException
         *                if an I/O error occurs
         */
        public int read() throws IOException {
            // Read the next non-whitespace character
            int x;
            do {
                x = in.read();
                if (x == -1) {
                    return -1;
                }
            } while (Character.isWhitespace((char) x));
            charCount++;

            // The '=' sign is just padding
            if (x == '=') {
                return -1; // effective end of stream
            }

            // Convert from raw form to 6-bit form
            x = ints[x];

            // Calculate which character we're decoding now
            int mode = (charCount - 1) % 4;

            // First char save all six bits, go for another
            if (mode == 0) {
                carryOver = x & 63;
                return read();
            }
            // Second char use previous six bits and first two new bits,
            // save last four bits
            else if (mode == 1) {
                int decoded = ((carryOver << 2) + (x >> 4)) & 255;
                carryOver = x & 15;
                return decoded;
            }
            // Third char use previous four bits and first four new bits,
            // save last two bits
            else if (mode == 2) {
                int decoded = ((carryOver << 4) + (x >> 2)) & 255;
                carryOver = x & 3;
                return decoded;
            }
            // Fourth char use previous two bits and all six new bits
            else if (mode == 3) {
                int decoded = ((carryOver << 6) + x) & 255;
                return decoded;
            }
            return -1; // can't actually reach this line
        }

        /***
         * Reads decoded data into an array of bytes and returns the actual number
         * of bytes read, or -1 if end of stream was reached.
         *
         * @param buf
         *            the buffer into which the data is read
         * @param off
         *            the start offset of the data
         * @param len
         *            the maximum number of bytes to read
         * @return the actual number of bytes read, or -1 if the end of the input
         *         stream is reached
         * @exception IOException
         *                if an I/O error occurs
         */
        public int read(byte[] buf, int off, int len) throws IOException {
            if (buf.length < (len + off - 1)) {
                throw new IOException("The input buffer is too small: " + len + " bytes requested starting at offset " + off + " while the buffer " + " is only " + buf.length + " bytes long.");
            }

            // This could of course be optimized
            int i;
            for (i = 0; i < len; i++) {
                int x = read();
                if (x == -1 && i == 0) { // an immediate -1 returns -1
                    return -1;
                } else if (x == -1) { // a later -1 returns the chars read so far
                    break;
                }
                buf[off + i] = (byte) x;
            }
            return i;
        }

        /***
         * Returns the decoded form of the given encoded string, as a String. Note
         * that not all binary data can be represented as a String, so this method
         * should only be used for encoded String data. Use decodeToBytes()
         * otherwise.
         *
         * @param encoded
         *            the string to decode
         * @return the decoded form of the encoded string
         */
        public static String decode(String encoded) {
            if (TextUtils.isEmpty(encoded)) {
                return "";
            }
            return new String(decodeToBytes(encoded));
        }

        /***
         * Returns the decoded form of the given encoded string, as bytes.
         *
         * @param encoded
         *            the string to decode
         * @return the decoded form of the encoded string
         */
        public static byte[] decodeToBytes(String encoded) {
            byte[] bytes = encoded.getBytes();
            Base64Decoder in = new Base64Decoder(new ByteArrayInputStream(bytes));
            ByteArrayOutputStream out = new ByteArrayOutputStream((int) (bytes.length * 0.75));
            try {
                byte[] buf = new byte[4 * 1024]; // 4K buffer
                int bytesRead;
                while ((bytesRead = in.read(buf)) != -1) {
                    out.write(buf, 0, bytesRead);
                }
                return out.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class Base64Encoder extends FilterOutputStream {

        private static final char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
                'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

        private int charCount;
        private int carryOver;
        // 是否每76字节换行
        private boolean isWrapBreak = true;

        /***
         * Constructs a new Base64 encoder that writes output to the given
         * OutputStream.
         *
         * @param out
         *            the output stream
         */
        private Base64Encoder(OutputStream out) {
            super(out);
        }

        /***
         * Constructs a new Base64 encoder that writes output to the given
         * OutputStream.
         *
         * @param out
         *            the output stream
         */
        private Base64Encoder(OutputStream out, boolean isWrapBreak) {
            this(out);
            this.isWrapBreak = isWrapBreak;
        }

        /***
         * Writes the given byte to the output stream in an encoded form.
         *
         * @exception IOException
         *                if an I/O error occurs
         */
        public void write(int b) throws IOException {
            // Take 24-bits from three octets, translate into four encoded chars
            // Break lines at 76 chars
            // If necessary, pad with 0 bits on the right at the end
            // Use = signs as padding at the end to ensure encodedLength % 4 == 0

            // Remove the sign bit,
            // thanks to Christian Schweingruber <chrigu@lorraine.ch>
            if (b < 0) {
                b += 256;
            }

            // First byte use first six bits, save last two bits
            if (charCount % 3 == 0) {
                int lookup = b >> 2;
                carryOver = b & 3; // last two bits
                out.write(chars[lookup]);
            }
            // Second byte use previous two bits and first four new bits,
            // save last four bits
            else if (charCount % 3 == 1) {
                int lookup = ((carryOver << 4) + (b >> 4)) & 63;
                carryOver = b & 15; // last four bits
                out.write(chars[lookup]);
            }
            // Third byte use previous four bits and first two new bits,
            // then use last six new bits
            else if (charCount % 3 == 2) {
                int lookup = ((carryOver << 2) + (b >> 6)) & 63;
                out.write(chars[lookup]);
                lookup = b & 63; // last six bits
                out.write(chars[lookup]);
                carryOver = 0;
            }
            charCount++;

            // Add newline every 76 output chars (that's 57 input chars)
            if (this.isWrapBreak && charCount % 57 == 0) {
                out.write('\n');
            }
        }

        /***
         * Writes the given byte array to the output stream in an encoded form.
         *
         * @param buf
         *            the data to be written
         * @param off
         *            the start offset of the data
         * @param len
         *            the length of the data
         * @exception IOException
         *                if an I/O error occurs
         */
        public void write(byte[] buf, int off, int len) throws IOException {
            // This could of course be optimized
            for (int i = 0; i < len; i++) {
                write(buf[off + i]);
            }
        }

        /***
         * Closes the stream, this MUST be called to ensure proper padding is
         * written to the end of the output stream.
         *
         * @exception IOException
         *                if an I/O error occurs
         */
        public void close() throws IOException {
            // Handle leftover bytes
            if (charCount % 3 == 1) { // one leftover
                int lookup = (carryOver << 4) & 63;
                out.write(chars[lookup]);
                out.write('=');
                out.write('=');
            } else if (charCount % 3 == 2) { // two leftovers
                int lookup = (carryOver << 2) & 63;
                out.write(chars[lookup]);
                out.write('=');
            }
            super.close();
        }

        /***
         * Returns the encoded form of the given unencoded string.<br>
         * 默认是否每76字节换行
         *
         * @param bytes
         *            the bytes to encode
         * @return the encoded form of the unencoded string
         * @throws IOException
         */
        public static String encode(byte[] bytes) {
            return encode(bytes, true);
        }

        /***
         * Returns the encoded form of the given unencoded string.
         *
         * @param bytes
         *            the bytes to encode
         * @param isWrapBreak
         *            是否每76字节换行
         * @return the encoded form of the unencoded string
         * @throws IOException
         */
        public static String encode(byte[] bytes, boolean isWrapBreak) {
            ByteArrayOutputStream out = new ByteArrayOutputStream((int) (bytes.length * 1.4));
            Base64Encoder encodedOut = new Base64Encoder(out, isWrapBreak);
            try {
                encodedOut.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    encodedOut.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return out.toString();
        }

        // public static void main(String[] args) throws Exception {
        // if (args.length != 1) {
        // System.err
        // .println("Usage: java com.oreilly.servlet.Base64Encoder fileToEncode");
        // return;
        // }
        // Base64Encoder encoder = null;
        // BufferedInputStream in = null;
        // try {
        // encoder = new Base64Encoder(System.out);
        // in = new BufferedInputStream(new FileInputStream(args[0]));
        //
        // byte[] buf = new byte[4 * 1024]; // 4K buffer
        // int bytesRead;
        // while ((bytesRead = in.read(buf)) != -1) {
        // encoder.write(buf, 0, bytesRead);
        // }
        // } finally {
        // if (in != null)
        // in.close();
        // if (encoder != null)
        // encoder.close();
        // }
        // }
    }

}
