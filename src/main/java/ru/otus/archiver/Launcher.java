package ru.otus.archiver;

import lombok.SneakyThrows;
import ru.otus.archiver.dto.DecodeData;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Launcher {

    private static final Archiver ARCHIVER = new Archiver();

    @SneakyThrows
    public static void main(String... args) {

        String algo = System.getProperty("algo");
        String mode = System.getProperty("mode");
        String file = System.getProperty("file");

        if (algo == null)
            algo = "simple";

        if (file == null || file.isEmpty() || mode == null || (!mode.equals("encode") && !mode.equals("decode"))) {
            System.out.println("Usage: java [-Dalgo=simple/extended] -Dmode=encode/decode -Dfile=filename -jar archiver.jar");
            System.exit(0);
        }

        byte[] data = getBytes(file);

        if (mode.equals("encode")) {
            byte[] result = ARCHIVER.encode(file, algo, data);

            final String archiveName = Paths.get(file).getFileName().toString() + ".archive";
            com.google.common.io.Files.write(result, new File(archiveName));
        }

        else {
            DecodeData result = ARCHIVER.decode(algo, data);

            com.google.common.io.Files.write(result.getData(), new File(result.getFileName()));
        }
    }

    @SneakyThrows
    private static byte[] getBytes(String filename) {
        return Files.readAllBytes(Paths.get(filename));
    }

}
