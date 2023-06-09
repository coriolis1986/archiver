package ru.otus.archiver.coder;

public class ExtendedCoder implements Coder {

    @Override
    public byte[] encode(byte[] arr) {
        return new byte[0];
    }

    @Override
    public byte[] decode(byte[] arr, int skip) {
        return new byte[0];
    }
}
