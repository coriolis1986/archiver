package ru.otus.archiver.coder;

import java.util.ArrayDeque;
import java.util.Queue;

public class ExtendedCoder implements Coder {

    @Override
    public byte[] encode(byte[] arr) {
        final Queue<Byte> queue = new ArrayDeque<>();

        if (arr.length == 0)
            return new byte[0];

        if (arr.length == 1)
            return new byte[] { 1, arr[0]};

        int count = 1;
        Queue<Byte> nonRepeatsQueue = new ArrayDeque<>();

        byte next = 0;

        for (int i = 1; i < arr.length; i++) {
            byte cur = arr[i - 1];
            next = arr[i];

            if (cur == next) {
                if (!nonRepeatsQueue.isEmpty()) {
                    int size = nonRepeatsQueue.size();
                    queue.add((byte) -size);

                    while (!nonRepeatsQueue.isEmpty())
                        queue.add(nonRepeatsQueue.poll());

                    count = 0;
                }

                count++;
            }

            if (cur != next) {
                if (count > 0) {
                    queue.add((byte) count);
                    queue.add(cur);
                    count = 0;
                }

                nonRepeatsQueue.add((byte) next);

                count--;
            }

            if (count == 127 || count == -127) {
                if (!nonRepeatsQueue.isEmpty()) {
                    int size = nonRepeatsQueue.size();
                    queue.add((byte) -size);

                    while (!nonRepeatsQueue.isEmpty())
                        queue.add(nonRepeatsQueue.poll());
                }

                else {
                    queue.add((byte) count);
                    queue.add(cur);
                }

                count = 0;
            }
        }

        if (count != 0) {
            if (!nonRepeatsQueue.isEmpty()) {
                int size = nonRepeatsQueue.size();
                queue.add((byte) -size);

                while (!nonRepeatsQueue.isEmpty())
                    queue.add(nonRepeatsQueue.poll());
            }

            else {
                queue.add((byte) count);
                queue.add(next);
            }
        }

        byte[] result = new byte[queue.size()];
        int i = 0;

        while (!queue.isEmpty())
            result[i++] = queue.poll();

        return result;
    }

    @Override
    public byte[] decode(byte[] arr, int skip) {
        Queue<Byte> queue = new ArrayDeque<>();
        int i = skip;

        while (i < arr.length) {

            if (arr[i] > 0) {
                int cnt = arr[i++];
                byte cur = arr[i++];

                for (int j = 1; j <= cnt; j++)
                    queue.add(cur);
            }

            else {
                int cnt = -arr[i++];

                for (int j = 1; j <= cnt; j++)
                    queue.add(arr[i++]);
            }
        }

        byte[] result = new byte[queue.size()];
        i = 0;

        while (!queue.isEmpty())
            result[i++] = queue.poll();

        return result;
    }
}
