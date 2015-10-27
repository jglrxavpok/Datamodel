package org.jglr.dmx.utils;

import java.io.*;

public final class IOUtils {

    public static String readNullTerminated(Reader in, ByteArrayOutputStream out) throws IOException {
        byte b;
        while((b = readByte(in)) != 0) {
            out.write(b);
        }
        out.flush();
        String read = new String(out.toByteArray());
        out.reset();
        return read;
    }

    public static float readFloat(Reader in) throws IOException {
        return Float.intBitsToFloat(readBigEndianInt(in));
    }

    public static boolean readBool(Reader in) throws IOException {
        return readByte(in) != 0;
    }

    public static byte readByte(Reader in) throws IOException {
        int val = in.read();
        if(val < 0)
            throw new EOFException();
        return (byte)val;
    }

    public static int readBigEndianInt(Reader in) throws IOException {
        byte a = readByte(in);
        byte b = readByte(in);
        byte c = readByte(in);
        byte d = readByte(in);
        //return (d << 24) | (c << 16) | (b << 8) | a;
        return a << 24 | b << 16 | c << 8 | d;
    }

    public static int readLittleEndianInt(Reader in) throws IOException {
        byte a = readByte(in);
        byte b = readByte(in);
        byte c = readByte(in);
        byte d = readByte(in);
        return (d << 24) | (c << 16) | (b << 8) | a;
        //return a << 24 | b << 16 | c << 8 | d;
    }

    public static byte[] readLittleEndianIntBytes(Reader in) throws IOException {
        byte a = readByte(in);
        byte b = readByte(in);
        byte c = readByte(in);
        byte d = readByte(in);
        return new byte[] { d, c, b, a};
    }

    public static byte[] readLittleEndianUUID(Reader in) throws IOException {
        byte[] a = readLittleEndianIntBytes(in);
        byte[] b = readLittleEndianIntBytes(in);
        byte[] c = readLittleEndianIntBytes(in);
        byte[] d = readLittleEndianIntBytes(in);
        byte[] uuid = new byte[16];
        for(int i = 0;i<uuid.length;i++) {
            int index = i % 4;
            byte[] arrayToRead;
            if(i < 4) {
                arrayToRead = d;
            } else if(i < 8) {
                arrayToRead = c;
            } else if(i < 12) {
                arrayToRead = b;
            } else {
                arrayToRead = a;
            }
            uuid[i] = arrayToRead[index];
        }
        return uuid;
    }
}
