package buffer;

import java.nio.ByteBuffer;

public class BufferDemo1 {
    public static void main(String[] args) {
        // 分配一个 10 个容量的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // 依次写入 0 - 9 十个数字 {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        // 分配子缓存区，切片索引为 [3, 7)，大小为 4
        buffer.position(3); // 切片开始位置
        buffer.limit(7); // 切片结束位置（不包括）
        ByteBuffer subBuffer = buffer.slice(); // 新的缓冲区内容为 {3, 4, 5, 6}

        // 修改子缓冲区的数据，原先的缓冲区的内容也会改变（内存共享）
        for (int i = 0; i < subBuffer.capacity(); i++) {
            subBuffer.put(i, (byte) (subBuffer.get() * 10));
        }

        // 打印原先缓冲区的内容 {0, 1, 2, 30, 40, 50, 60, 7, 8, 9}
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
