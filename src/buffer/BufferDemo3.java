package buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo3 {
    public static void main(String[] args) throws IOException {
        // read
        FileInputStream fis = new FileInputStream("D:\\TmpDir\\channel\\a.txt");
        FileChannel fisChannel = fis.getChannel();

        // write
        FileOutputStream fos = new FileOutputStream("D:\\TmpDir\\channel\\b.txt");
        FileChannel fosChannel = fos.getChannel();

        // 分配一个 10 个容量的直接缓冲区
        // ByteBuffer buffer = ByteBuffer.allocate(2048);
        ByteBuffer buffer = ByteBuffer.allocateDirect(2048);

        // 将 a 文件的内容
        long startTime = System.currentTimeMillis();
        while (true) {
            buffer.clear();
            int length = fisChannel.read(buffer);
            if (length == -1) {
                break;
            }

            // 将 Buffer 转换为读模式
            buffer.flip();
            fosChannel.write(buffer);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));

        fisChannel.close();
        fosChannel.close();
    }
}
