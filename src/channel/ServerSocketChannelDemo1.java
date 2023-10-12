package channel;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo1 {
    public static void main(String[] args) throws Exception {
        int port = 8080; // 监听的端口号

        // 创建 ServerSocketChannel 对象，绑定监听的端口，并设置为非阻塞模式
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);

        // 创建 Buffer
        // ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());

        while (true) {
            System.out.println("等待连接...");
            // 获取连接，如果为阻塞模式，这里会阻塞
            SocketChannel accept = ssc.accept();
            if (accept == null) {
                System.out.println("没有连接");
                Thread.sleep(2000);
            } else {
                System.out.println("存在连接：" + accept.socket().getLocalSocketAddress());

                // buffer.rewind(); // 将 position 置为 0
                // accept.write(buffer);
                accept.close();
            }
        }

        // ssc.close();
    }
}
