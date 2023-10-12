package channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelDemo {
    public static void main(String[] args) throws Exception {
        // 创建 FileChannel 对象
        RandomAccessFile accessFile = new RandomAccessFile("D:\\TmpDir\\channel\\a.txt", "rw");
        FileChannel channel = accessFile.getChannel();

        // 分散
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);
        ByteBuffer[] buffers = {header, body};
        channel.read(buffers);

        // 聚合
        ByteBuffer header1 = ByteBuffer.allocate(128);
        ByteBuffer body1 = ByteBuffer.allocate(1024);
        ByteBuffer[] buffers1 = {header1, body1};
        channel.write(buffers1);

        // 关闭 RandomAccessFile
        accessFile.close();
    }
}
