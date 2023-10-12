package channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo1 {
    public static void main(String[] args) throws IOException {

        /**

         // 写模式：读取文件的内容写入 Buffer 中
         FileInputStream fis = new FileInputStream("D:\\TmpDir\\channel\\a.txt");
         FileChannel fisChannel = fis.getChannel();
         int length = fisChannel.read(buffer);

         // 读模式：读取 Buffer 的内容写入文件中
         FileOutputStream fos = new FileOutputStream("D:\\TmpDir\\channel\\a.txt");
         FileChannel fosChannel = fos.getChannel();
         fosChannel.write(buffer);

         */

        // 创建并打开 FileChannel 通道
        RandomAccessFile accessFile = new RandomAccessFile("D:\\TmpDir\\channel\\a.txt", "rw");
        FileChannel channel = accessFile.getChannel();

        // 分配缓冲区，参数为缓冲区的容量
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 读取数据到缓冲区中，read 方法返回的结果表示有多少个字节的数据被读取到了缓冲区中
        int length = channel.read(buffer);
        System.out.println("读取的数据长度为（字节）：" + length);

        // 当读取到缓存区的数据长度为 -1 时，代表读取到了文件的末尾
        while (length != -1) {
            // 将 Buffer 从写模式切换到读模式（限制位置为当前位置，当前位置置为 0）
            buffer.flip();

            // 判断是否还有剩余元素
            while (buffer.hasRemaining()) {
                char c = (char) buffer.get();
                System.out.println(c);
            }

            // 清除 Buffer 的内容（将 Buffer 转为写模式），重新向 Buffer 写入内容
            buffer.clear();
            length = channel.read(buffer);
            System.out.println("读取的数据长度为（字节）：" + length);
        }

        // 关闭 RandomAccessFile
        accessFile.close();
    }
}
