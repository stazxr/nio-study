package async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// 通过 CompletionHandler 异步写入数据
public class AsyncFileChannelDemo4 {
    public static void main(String[] args) throws IOException {
        // 打开 AsynchronousFileChannel 通道
        Path path = Paths.get("D:\\TmpDir\\channel\\a.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        // 创建 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("b".getBytes());
        buffer.flip();

        // 将 Channel 中的数据写入到 Buffer 中：这里不会清空原有文件写入字节后的内容！！！
        channel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result: " + result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });

        // 关闭 AsynchronousFileChannel 通道
        channel.close();
    }
}
