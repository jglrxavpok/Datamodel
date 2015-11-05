package org.jglr.dmx.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public final class IOUtils {

    private static final ByteArrayOutputStream out = new ByteArrayOutputStream();

    public static String readNullTerminated(InputStream in) throws IOException {
        byte b;
        while((b = readByte(in)) != 0) {
            out.write(b);
        }
        out.flush();
        String read = new String(out.toByteArray());
        out.reset();
        return read;
    }

    public static float readFloat(InputStream in) throws IOException {
        return Float.intBitsToFloat(readBigEndianInt(in));
    }

    public static boolean readBool(InputStream in) throws IOException {
        return readByte(in) != 0;
    }

    public static byte readByte(InputStream in) throws IOException {
        int val = in.read();
        if(val < 0)
            throw new EOFException();
        return (byte)val;
    }

    public static int readBigEndianInt(InputStream in) throws IOException {
        byte a = readByte(in);
        byte b = readByte(in);
        byte c = readByte(in);
        byte d = readByte(in);
        return (a << 24) | (b << 16) | (c << 8) | d;
    }

    public static int readLittleEndianInt(InputStream in) throws IOException {
        byte a = readByte(in);
        byte b = readByte(in);
        byte c = readByte(in);
        byte d = readByte(in);
        return (d << 24) | (c << 16) | (b << 8) | a;
    }

    public static byte[] readLittleEndianIntBytes(InputStream in) throws IOException {
        byte a = readByte(in);
        byte b = readByte(in);
        byte c = readByte(in);
        byte d = readByte(in);
        return new byte[] { d, c, b, a};
    }

    public static byte[] readLittleEndianUUID(InputStream in) throws IOException {
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

    public static void writeLittleEndianUUID(OutputStream out, UUID uuid) throws IOException {
        long idMostSigBytes = uuid.getMostSignificantBits();
        long idLeastSigBytes = uuid.getLeastSignificantBits();
        writeLittleEndianLong(out, idLeastSigBytes);
        writeLittleEndianLong(out, idMostSigBytes);
    }

    public static void writeLittleEndianLong(OutputStream out, long value) throws IOException {
        // Goes from the least significant bits to the most significants ones
        for(int i = 0;i<8;i++) {
            // Extracts a 8-bit segment from the 64 long value
            byte longExtract = (byte) ((value >> i*8) & 0xFF);
            writeByte(out, longExtract);
        }
    }

    public static void writeByte(OutputStream out, byte value) throws IOException {
        out.write(value);
    }

    public static void writeBigEndianInt(OutputStream out, int value) throws IOException {
        int a = (value >> 24) & 0xFF;
        int b = (value >> 16) & 0xFF;
        int c = (value >> 8) & 0xFF;
        int d = value & 0xFF;
        // result = (a << 24) | (b << 16) | (c << 8) | d
        out.write(a);
        out.write(b);
        out.write(c);
        out.write(d);
    }

    public static void writeLittleEndianInt(OutputStream out, int value) throws IOException {
        int a = (value >> 24) & 0xFF;
        int b = (value >> 16) & 0xFF;
        int c = (value >> 8) & 0xFF;
        int d = value & 0xFF;
        // result = (d << 24) | (c << 16) | (b << 8) | a
        out.write(d);
        out.write(c);
        out.write(b);
        out.write(a);
    }

    public static void writeLittleEndianFloat(OutputStream out, float value) throws IOException {
        writeLittleEndianInt(out, Float.floatToRawIntBits(value));
    }

    public static void writeNullTerminatedString(OutputStream out, String value) throws IOException {
        byte[] bytes = value.getBytes();
        out.write(bytes);
        out.write(0);
    }

    public static UUID getUUIDFromBytes(byte[] data) {
        assert data.length == 16 : "Data length must be 16";
        // Adapted from UUID's private constructor UUID(byte[] data)
        long mostSigBits = 0;
        long leastSigBits = 0;
        for (int i = 0;i<8;i++) {
            mostSigBits = (mostSigBits << 8) | (data[i] & 0xFF);
        }
        for (int i = 8; i<16; i++) {
            leastSigBits = (leastSigBits << 8) | (data[i] & 0xFF);
        }
        return new UUID(mostSigBits, leastSigBits);
    }
}
