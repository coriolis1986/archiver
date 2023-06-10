package ru.otus.archiver.coder;

import ru.otus.archiver.dto.DecodeData;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Archiver {

    private final static char FILENAME_SEPARATOR = '#';

    public byte[] encode(String filename, String algo, byte[] bytes) {
        byte[] data = getCoder(algo).encode(bytes);

        Path pathLinux = Paths.get(filename);

        byte[] filenameInBytes = (pathLinux.getFileName().toString() + FILENAME_SEPARATOR).getBytes();
        byte[] merged = new byte[filenameInBytes.length + data.length];

        System.arraycopy(filenameInBytes, 0, merged, 0, filenameInBytes.length);
        System.arraycopy(data, 0, merged, filenameInBytes.length, data.length);

        return merged;
    }

    public DecodeData decode(String algo, byte[] bytes) {
        int i = 0;

        while (true)
            if ((char) bytes[++i] == FILENAME_SEPARATOR)
                break;

        String fileName = new String(Arrays.copyOfRange(bytes, 0, i));
        byte[] data = getCoder(algo).decode(bytes, i + 1);

        DecodeData result = new DecodeData();
        result.setFileName(fileName);
        result.setData(data);

        return result;
    }

    private Coder getCoder(String algo) {
        switch (algo) {
            case "simple":
                return new SimpleCoder();

            case "extended":
                return new ExtendedCoder();

            default:
                throw new RuntimeException(String.format("Unknown algo [%s]",algo));
        }
    }

}
