package ru.otus.archiver.coder;

import java.util.ArrayDeque;
import java.util.Queue;

public class SimpleCoder implements Coder {

    public byte[] encode(byte[] arr) {

        final Queue<Byte> queue = new ArrayDeque<>();
        Byte prevByte = null;
        byte count = 0;

        for (byte curByte : arr) {
            if ((count == 127) || (prevByte != null && prevByte != curByte)) {
                queue.add(count);
                queue.add((byte) prevByte);

                count = 0;
            }

            count++;

            prevByte = curByte;
        }

        if (prevByte != null) {
            queue.add(count);
            queue.add(prevByte);
        }

        byte[] result = new byte[queue.size()];
        int i = 0;

        while (!queue.isEmpty())
            result[i++] = queue.poll();

        return result;
    }

    public byte[] decode(byte[] arr, int skip) {

        final Queue<Byte> queue = new ArrayDeque<>();

        System.out.println();
        for (int i = skip; i < arr.length; ) {
            int count = arr[i++];
            byte cur = arr[i++];

            for (int j = 1; j <= count; j++)
                queue.add(cur);
        }

        byte[] result = new byte[queue.size()];
        int i = 0;

        while (!queue.isEmpty())
            result[i++] = queue.poll();

        return result;
    }
}
