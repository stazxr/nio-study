package async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// 通过 CompletionHandler 异步读取数据
public class AsyncFileChannelDemo2 {
    public static void main(String[] args) throws IOException {
        // 打开 AsynchronousFileChannel 通道
        Path path = Paths.get("D:\\TmpDir\\channel\\b.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        // 创建 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 将 Channel 中的数据读取到 Buffer 中
        channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result: " + result);

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
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
