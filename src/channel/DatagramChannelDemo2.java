package channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class DatagramChannelDemo2 {
    public static void main(String[] args) throws Exception {
        // 创建 DatagramChannel 对象，并监听指定端口接收 UDP 数据包
        DatagramChannel clientChannel = DatagramChannel.open();

        // 创建 InetSocketAddress
        int port = 8080; // 监听的端口号
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", port);

        // 发送数据
        while (true) {
            ByteBuffer sendBuffer = ByteBuffer.wrap("你好".getBytes(StandardCharsets.UTF_8));
            clientChannel.send(sendBuffer, address);
            System.out.println("已发送一条数据");
            Thread.sleep(3000);
        }
    }
}
