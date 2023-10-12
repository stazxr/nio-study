package room.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

// 客户端
public class ChatClient {
    private static Selector selector;

    private static final Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) throws Exception {
        startClient("张三");
    }

    // 启动客户端
    public static void startClient(String name) throws Exception {
        // 连接服务器
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        socketChannel.configureBlocking(false);

        // 接收服务器响应的数据
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        Thread clientThread = new Thread(() -> {
            try {
                for (;;) {
                    int readChannels = selector.select();
                    if (readChannels == 0) {
                        continue;
                    }

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();

                        // 业务逻辑处理
                        if (selectionKey.isReadable()) {
                            readEvent(selectionKey);
                        }

                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        clientThread.start();

        // 向服务器发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            if (message.length() > 0) {
                socketChannel.write(charset.encode(name.concat(": ").concat(message)));
            }
        }
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
        }
    }
}
