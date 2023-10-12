package buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo4 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("D:\\TmpDir\\channel\\a.txt", "rw");
        FileChannel channel = raf.getChannel();

        // 内存映射 IO
        MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
        mapBuffer.put(0, (byte) 1);
        mapBuffer.put(1023, (byte) 222);

        raf.close();
    }
}
