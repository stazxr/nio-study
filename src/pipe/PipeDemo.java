package pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {
    public static void main(String[] args) throws Exception {
        // 打开管道
        Pipe pipe = Pipe.open();

        // 写入数据
        Pipe.SinkChannel sink = pipe.sink();
        ByteBuffer writeBuffer = ByteBuffer.allocate(64);
        writeBuffer.clear();
        writeBuffer.put("hello".getBytes());
        writeBuffer.flip();
        while (writeBuffer.hasRemaining()) {
            sink.write(writeBuffer);
        }

        // 读取数据
        Pipe.SourceChannel source = pipe.source();
        ByteBuffer readBuffer = ByteBuffer.allocate(64);
        int readByte = source.read(readBuffer);
        System.out.println("读取了：" + new String(readBuffer.array(), 0, readByte));

        // 关闭通道
        sink.close();
        source.close();
    }
}
