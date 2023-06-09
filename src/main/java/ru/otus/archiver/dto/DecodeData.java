package ru.otus.archiver.dto;

import lombok.Data;

@Data
public class DecodeData {

    private String fileName;
    private byte[] data;
}
