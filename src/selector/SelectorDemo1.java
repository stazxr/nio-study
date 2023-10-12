package selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo1 {
    public static void main(String[] args) throws IOException {
        // 创建选择器
        Selector selector = Selector.open();

        // 创建通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9999));
        ssc.configureBlocking(false); // 注册选择器，必须位非阻塞模式，否则调用 register 方法会抛出异常

        // 将通道注册到选择器上，并指定监听事件位：接收事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 就绪状态查询方法
        // selector.select(); // 阻塞到至少有一个通道你在注册的事件上就绪了，可以使用 wakeup() 方法唤醒，立即返回
        // selector.select(1000); // 最长阻塞事件位 1000 毫秒
        // selector.selectNow(); // 非阻塞，只要有通道就绪，就立即返回

        // 查询就绪的通道操作（选择键集合）
        Set<SelectionKey> selectionKeys = selector.selectedKeys();

        // 遍历选择键集合
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            // 判断选择键的就绪状态
            SelectionKey selectionKey = iterator.next();
            if (selectionKey.isAcceptable()) {
                // 可接收
                // TODO 业务逻辑
            } else if (selectionKey.isConnectable()) {
                // 可连接
                // TODO 业务逻辑
            } else if (selectionKey.isReadable()) {
                // 可读取
                // TODO 业务逻辑
            } else if (selectionKey.isWritable()) {
                // 可写入
                // TODO 业务逻辑
            }

            iterator.remove();
        }

        // 关闭选择器
        selector.close();
    }
}
