package selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

public class SelectorDemo2 {
    public static void main(String[] args) throws IOException {
        // 创建通道
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));

        // 切换通道为非阻塞模式
        channel.configureBlocking(false);

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();

            // 向缓冲区写入数据
            buffer.put(new Date().toString().concat("\t: ").concat(str).getBytes());

            // 切换缓冲区为读模式
            buffer.flip();

            // 写入通道
            channel.write(buffer);

            // 清除缓冲区
            buffer.clear();
        }
    }
}
