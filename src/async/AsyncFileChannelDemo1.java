package async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

// 通过 Future 异步读取数据
public class AsyncFileChannelDemo1 {
    public static void main(String[] args) throws IOException {
        // 打开 AsynchronousFileChannel 通道
        Path path = Paths.get("D:\\TmpDir\\channel\\b.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        // 创建 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 将 Channel 中的数据读取到 Buffer 中，获取异步处理的 Future 对象
        Future<Integer> future = channel.read(buffer, 0);

        // 判断是否读取完成
        while (!future.isDone());

        // 已经读取完成，打印 Buffer 中的数据
        buffer.flip();
        // while (buffer.hasRemaining()) {
        //     System.out.println((char) buffer.get());
        // }
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));

        // 关闭 AsynchronousFileChannel 通道
        channel.close();
    }
}
