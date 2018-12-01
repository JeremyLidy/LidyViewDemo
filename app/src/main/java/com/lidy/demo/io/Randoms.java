package com.lidy.demo.io;

import java.io.IOException;
import java.util.Random;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import okio.Timeout;
import org.jetbrains.annotations.NotNull;

/**
 * OKIO 操作类
 *
 * @author lideyou
 */
public final class Randoms {

    public static void main(String[] args) {
        try {

            new Randoms().run();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException, InterruptedException {
        Random random = new Random(3792615686L);
        BufferedSource source = Okio.buffer(new RandomSource(random, 5));
        System.out.println(source.readUtf8());
    }


    static final class RandomSource implements Source {

        private final Random random;
        private long bytesLeft;

        RandomSource(Random random, long bytesLeft) {
            this.random = random;
            this.bytesLeft = bytesLeft;
        }


        @Override
        public void close() throws IOException {
            bytesLeft = -1L;
        }

        @Override
        public long read(@NotNull Buffer sink, long byteCount) throws IOException {
            if (bytesLeft == -1L) {
                throw new IllegalStateException("closed");
            }
            if (bytesLeft == 0L) {
                return -1L;
            }
            if (byteCount > Integer.MAX_VALUE) {
                byteCount = Integer.MAX_VALUE;
            }
            if (byteCount > bytesLeft) {
                byteCount = bytesLeft;
            }

            // Random is most efficient when computing 32 bits of randomness. Start with that.
            int ints = (int) (byteCount / 4);
            for (int i = 0; i < ints; i++) {
                sink.writeInt(random.nextInt());
            }

            // If we need 1, 2, or 3 bytes more, keep going. We'll discard 24, 16 or 8 random bits!
            int bytes = (int) (byteCount - ints * 4);
            if (bytes > 0) {
                int bits = random.nextInt();
                for (int i = 0; i < bytes; i++) {
                    sink.writeByte(bits & 0xff);
                    bits >>>= 8;
                }
            }

            bytesLeft -= byteCount;
            return byteCount;
        }

        @NotNull
        @Override
        public Timeout timeout() {
            return Timeout.NONE;
        }


    }


}
