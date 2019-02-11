package com.lidy.demo.io;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import javax.xml.transform.Source;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.HashingSink;
import okio.HashingSource;
import okio.Okio;
import okio.Sink;

/**
 * @author lideyou
 */
public class ExampleIOActivity {

    public static void main(String[] args) {
//        io1();
//        io2();
//        io3();
//        io4();
//        nio1();
//        okio1();
//        writeEnv();
//        okio2();
        hex1();
    }

    /**
     * 输入流
     */
    private static void io1() {
        try (InputStream stream = new FileInputStream("./text.txt")) {
            Reader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            System.out.println(bufferedReader.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出流
     */
    private static void io2() {
        try (OutputStream stream = new FileOutputStream("./text.txt")) {
            stream.write('a');
            stream.write('b');

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void io3() {
        try (OutputStream stream = new FileOutputStream("./text.txt");
                Writer writer = new OutputStreamWriter(stream);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write('q');
            bufferedWriter.write('b');
            // stream 关闭的时候会自动 flush
            //bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void io4() {
        try (OutputStream stream = new FileOutputStream("./text.txt");
                BufferedOutputStream outputStream = new BufferedOutputStream(stream)) {
            outputStream.write('q');
            outputStream.write('a');
            outputStream.write('c');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void io5() {
        try (InputStream inputStream = new FileInputStream("./text.txt");
                OutputStream outputStream = new FileOutputStream("./new_text.txt")) {
            byte[] data = new byte[1024];
            int read;
            while ((read = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void io6() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            String data;
            while (true) {
                data = reader.readLine();
                writer.write(data);
                writer.write("\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * NIO 用到 Buffer
     */
    private static void nio1() {
        try {
            RandomAccessFile file = new RandomAccessFile("./text.txt", "r");
            FileChannel channel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            byteBuffer.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void nio2() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            if (VERSION.SDK_INT >= VERSION_CODES.N) {
                serverSocketChannel.bind(new InetSocketAddress(8080));
                serverSocketChannel.configureBlocking(false);
                Selector selector = Selector.open();
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                SocketChannel socketChannel = serverSocketChannel.accept();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                while (socketChannel.read(byteBuffer) != -1) {
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    byteBuffer.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件
     */
    private static void okio1() {

        try (okio.Source source = Okio.source(new File("./text.txt"));
                BufferedSource buffer = Okio.buffer(source)) {

            System.out.println(buffer.readUtf8Line());
            System.out.println(buffer.readUtf8Line());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeEnv() {

        try (Sink sink = Okio.sink(new File("./text.txt"));
                BufferedSink bufferedSink = Okio.buffer(sink)) {

            bufferedSink.writeUtf8("条带_你");
            bufferedSink.writeUtf8("\n");
            bufferedSink.writeUtf8("条_带_你");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void okio2() {

        okio.Buffer buffer = new okio.Buffer();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(buffer.outputStream());
            objectOutputStream.writeUTF("abc");
            objectOutputStream.writeChar('0');
            objectOutputStream.writeBoolean(true);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(buffer.inputStream());
            System.out.println(objectInputStream.readUTF());
            System.out.println(objectInputStream.readBoolean());
            System.out.println(objectInputStream.readBoolean());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void hex1() {

        try {

            File file = new File("./README.md");

            System.out.println("ByteString");
            ByteString byteString = readByteString(file);
            System.out.println("       md5: " + byteString.md5().hex());
            System.out.println("      sha1: " + byteString.sha1().hex());
            System.out.println("    sha256: " + byteString.sha256().hex());
            System.out.println("    sha512: " + byteString.sha512().hex());
            System.out.println();

            System.out.println("Buffer");
            okio.Buffer buffer = readBuffer(file);
            System.out.println("       md5: " + buffer.md5().hex());
            System.out.println("      sha1: " + buffer.sha1().hex());
            System.out.println("    sha256: " + buffer.sha256().hex());
            System.out.println("    sha512: " + buffer.sha512().hex());
            System.out.println();

            System.out.println("HashingSource");
            try (HashingSource hashingSource = HashingSource.sha256(Okio.source(file));
                    BufferedSource source = Okio.buffer(hashingSource)) {
                source.readAll(Okio.blackhole());
                System.out.println("    sha256: " + hashingSource.hash().hex());
            }
            System.out.println();

            System.out.println("HashingSink");
            try (HashingSink hashingSink = HashingSink.sha256(Okio.blackhole());
                    BufferedSink sink = Okio.buffer(hashingSink);
                    okio.Source source = Okio.source(file)) {
                sink.writeAll(source);
                sink.close(); // Emit anything buffered.
                System.out.println("    sha256: " + hashingSink.hash().hex());
            }
            System.out.println();

            System.out.println("HMAC");
            ByteString secret = ByteString.decodeHex("7065616e7574627574746572");
            System.out.println("hmacSha256: " + byteString.hmacSha256(secret).hex());
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ByteString readByteString(File file) throws IOException {
        try (BufferedSource source = Okio.buffer(Okio.source(file))) {
            return source.readByteString();
        }
    }

    public static okio.Buffer readBuffer(File file) throws IOException {
        try (okio.Source source = Okio.source(file)) {
            okio.Buffer buffer = new okio.Buffer();
            buffer.writeAll(source);
            return buffer;
        }
    }
}
