package channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo2 {
    public static void main(String[] args) throws IOException {
        // 创建并打开 FileChannel 通道
        RandomAccessFile accessFile = new RandomAccessFile("D:\\TmpDir\\channel\\b.txt", "rw");
        FileChannel channel = accessFile.getChannel();

        // 分配缓冲区，参数为缓冲区的容量
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 向 Buffer 中写入数据
        String data = "test2";
        buffer.clear();
        buffer.put(data.getBytes());

        // 将 Buffer 从写模式切换到读模式（限制位置为当前位置，当前位置置为 0）
        buffer.flip();

        // 通过通道将缓冲区的内容写入到文件中
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }

        // 关闭 RandomAccessFile
        accessFile.close();
    }
}
