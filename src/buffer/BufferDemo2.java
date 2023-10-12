package buffer;

import java.nio.ByteBuffer;

public class BufferDemo2 {
    public static void main(String[] args) {
        // 分配一个 10 个容量的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // 依次写入 0 - 9 十个数字 {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        // 创建只读缓冲区
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        // 修改原缓冲区的数据，新建的只读缓冲区的内容也会改变（内存共享）
        buffer.position(0);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i, (byte) (buffer.get() * 10));
        }

        // 打印只读缓冲区的内容 {0, 10, 20, 30, 40, 50, 60, 70, 80, 90}
        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(readOnlyBuffer.capacity());
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
    }
}
