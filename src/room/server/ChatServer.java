package room.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

// 服务端
public class ChatServer {
    private static Selector selector;

    private static ServerSocketChannel server;

    private static final Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) throws Exception {
        startServer();
    }

    // 启动服务器
    public static void startServer() throws Exception {
        // 创建 Selector 选择器
        selector = Selector.open();

        // 打开 ServerSocketChannel 通道
        server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(9999));
        server.configureBlocking(false);

        // 注册通道
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已成功启动！！！");

        // 循环等待连接
        for (;;) {
            int readChannels = selector.select();
            if (readChannels == 0) {
                System.out.println("暂时没有连接接入");
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                // 业务逻辑处理
                if (selectionKey.isAcceptable()) {
                    acceptableEvent();
                }
                if (selectionKey.isConnectable()) {
                    // TODO
                }
                if (selectionKey.isReadable()) {
                    readEvent(selectionKey);
                }
                if (selectionKey.isWritable()) {
                    // TODO
                }

                iterator.remove();
            }
        }
    }

    // 处理接收状态
    private static void acceptableEvent() throws IOException {
        // 获取连接对象
        SocketChannel socketChannel = server.accept();
        socketChannel.configureBlocking(false);
        System.out.println("有新的用户加入聊天室：" + socketChannel.getLocalAddress());

        // 将连接对象注册到选择器中，并监听读取状态
        socketChannel.register(selector, SelectionKey.OP_READ);

        // 回复客户端
        socketChannel.write(charset.encode("欢迎进入聊天室！"));
    }

    // 处理读取状态
    private static void readEvent(SelectionKey selectionKey) throws IOException {
        // 获取已经就绪的通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        // 创建 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 读取用户的信息
        int readByte = socketChannel.read(buffer);
        StringBuilder message = new StringBuilder();
        while (readByte > 0) {
            buffer.flip();
            message.append(charset.decode(buffer));
            buffer.clear();
            readByte = socketChannel.read(buffer);
        }

        // 将通道注册到连接器上，监听后续可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);

        // 把客户端发送的消息广播到其他客户端
        if (message.length() > 0) {
            System.out.println(message);
            castMessage(message.toString(), socketChannel);
        }
    }

    // 客户端消息广播
    private static void castMessage(String message, SocketChannel socketChannel) throws IOException {
        // 获取所有已连接的客户端
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            SelectableChannel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != socketChannel) {
                // 发送消息
                ((SocketChannel) targetChannel).write(charset.encode(message));
            }
        }
    }
}
