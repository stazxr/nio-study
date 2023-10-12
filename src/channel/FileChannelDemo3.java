package channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo3 {
    public static void main(String[] args) throws IOException {
        // 创建并打开 FileChannel 通道
        RandomAccessFile accessFile1 = new RandomAccessFile("D:\\TmpDir\\channel\\a.txt", "rw");
        FileChannel fromChannel = accessFile1.getChannel();

        RandomAccessFile accessFile2 = new RandomAccessFile("D:\\TmpDir\\channel\\b.txt", "rw");
        FileChannel toChannel = accessFile2.getChannel();

        // fromChannel 的数据传输到 toChannel
        toChannel.transferFrom(fromChannel, 0, fromChannel.size());

        // 关闭 RandomAccessFile
        accessFile1.close();
        accessFile2.close();
    }
}
