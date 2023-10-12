package selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo3 {
    public static void main(String[] args) throws IOException {
        // 创建通道
        ServerSocketChannel channel = ServerSocketChannel.open();

        // 切换通道为非阻塞模式
        channel.configureBlocking(false);

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 绑定监听的端口号
        channel.socket().bind(new InetSocketAddress(9999));

        // 创建选择器
        Selector selector = Selector.open();

        // 将通道注册到选择器上，并指定监听事件位：接收事件
        channel.register(selector, SelectionKey.OP_ACCEPT);

        // 轮询查询就绪状态
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                // 判断选择键的就绪状态
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    // 获取连接
                    SocketChannel accept = channel.accept();

                    // 切换为非阻塞状态
                    accept.configureBlocking(false);

                    // 注册
                    accept.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isConnectable()) {
                    // 可连接
                    // TODO 业务逻辑
                } else if (selectionKey.isReadable()) {
                    // 获取连接
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    // 读取数据
                    int length = 0;
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    while ((length = socketChannel.read(readBuffer)) > 0) {
                        readBuffer.flip();
                        System.out.println("读取数据：" + new String(readBuffer.array(), 0 , length));
                        readBuffer.clear();
                    }
                } else if (selectionKey.isWritable()) {
                    // 可写入
                    // TODO 业务逻辑
                }

                iterator.remove();
            }
        }
    }
}
