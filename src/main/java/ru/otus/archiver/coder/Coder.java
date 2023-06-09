package ru.otus.archiver.coder;

public interface Coder {

    byte[] encode(byte[] arr);

    byte[] decode(byte[] arr, int skip);
}
