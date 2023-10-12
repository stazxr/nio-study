package channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo1 {
    public static void main(String[] args) throws Exception {
        int port = 80; // 监听的端口号

        // 创建 InetSocketAddress
        InetSocketAddress address = new InetSocketAddress("www.suntaoblog.com", port);

        // 创建 SocketChannel 对象
        // SocketChannel sc = SocketChannel.open(address); // 方式一
        // 方式二: 无参数传递时，只是创建了一个 SocketChannel 对象，并没有进行 TCP 连接
        SocketChannel sc = SocketChannel.open();
        sc.connect(address);

        // 常用连接校验方法
        System.out.println(sc.isOpen()); // 判断通道是否为 open 状态
        System.out.println(sc.isConnected()); // 判断通道是否为已连接状态
        System.out.println(sc.isConnectionPending()); // 判断通道是否正在进行连接
        System.out.println(sc.finishConnect()); // 判断正在进行连接的套接字是否已经完成连接

        // 设置阻塞模式：true 代表为阻塞模式，false 代表为非阻塞模式
        sc.configureBlocking(false);

        // write
        ByteBuffer data = ByteBuffer.allocate(10);
        data.put("hello".getBytes());
        data.flip();
        while (data.hasRemaining()) {
            sc.write(data); // 阻塞模式，这里会阻塞
        }
        System.out.println("write over");

        // read
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int length = sc.read(buffer); // 阻塞模式，这里会阻塞
        System.out.println("读取的数据长度为（字节）：" + length);
        while (length > 0) { // != -1 ?
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }

            buffer.clear();
            length = sc.read(buffer);
            System.out.println("读取的数据长度为（字节）：" + length);
        }
        System.out.println("read over");

        // 关闭 SocketChannel
        sc.close();
    }
}
