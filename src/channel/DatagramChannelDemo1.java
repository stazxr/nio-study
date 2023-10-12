package channel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class DatagramChannelDemo1 {
    public static void main(String[] args) throws Exception {
        // 创建 InetSocketAddress
        int port = 8080; // 监听的端口号
        InetSocketAddress address = new InetSocketAddress(port);

        // 创建 DatagramChannel 对象，并监听指定端口接收 UDP 数据包
        DatagramChannel serverChannel = DatagramChannel.open();
        serverChannel.socket().bind(address);

        // 设置阻塞模式：true 代表为阻塞模式，false 代表为非阻塞模式
        serverChannel.configureBlocking(false);

        // 创建 Buffer
        ByteBuffer revBuffer = ByteBuffer.allocate(64);

        // 接收数据
        while (true) {
            revBuffer.clear();
            SocketAddress receiveAdr = serverChannel.receive(revBuffer);
            System.out.print(Calendar.getInstance().get(Calendar.SECOND) + "\t");
            if (receiveAdr == null) {
                System.out.println("暂时没有接收到数据");
            } else {
                revBuffer.flip();
                System.out.println("接收到数据【" + receiveAdr + "】，内容为：" + StandardCharsets.UTF_8.decode(revBuffer));
            }

            Thread.sleep(3000);
        }
    }
}
