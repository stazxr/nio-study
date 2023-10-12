package async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

// 通过 Future 异步写入数据
public class AsyncFileChannelDemo3 {
    public static void main(String[] args) throws IOException {
        // 打开 AsynchronousFileChannel 通道
        Path path = Paths.get("D:\\TmpDir\\channel\\a.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        // 创建 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello".getBytes());
        buffer.flip();

        // 将 Channel 中的数据读取到 Buffer 中，获取异步处理的 Future 对象
        Future<Integer> future = channel.write(buffer, 0);

        // 判断是否读取完成
        while (!future.isDone());
        System.out.println("write over!");

        // 关闭 AsynchronousFileChannel 通道
        channel.close();
    }
}
