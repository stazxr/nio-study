package channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class DatagramChannelDemo4 {
    public static void main(String[] args) throws Exception {
        // 创建 DatagramChannel 对象，并监听指定端口接收 UDP 数据包
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(9999));

        // 连接
        channel.connect(new InetSocketAddress("127.0.0.1", 9999));

        // write
        channel.write(ByteBuffer.wrap("你好".getBytes(StandardCharsets.UTF_8)));

        // read
        ByteBuffer readBuffer = ByteBuffer.allocate(64);
        readBuffer.clear();
        channel.read(readBuffer);
        readBuffer.flip();
        System.out.println(StandardCharsets.UTF_8.decode(readBuffer));

        // close
        channel.close();
    }
}
