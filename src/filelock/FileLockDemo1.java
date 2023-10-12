package filelock;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLockDemo1 {
    public static void main(String[] args) throws Exception {
        // 获取 FileChannel
        FileChannel fileChannel = new FileOutputStream("D:\\TmpDir\\channel\\a.txt").getChannel();

        // 对文件加锁的方法，
        FileLock lock = fileChannel.lock(); // 阻塞，对整个文件加锁，默认为排他锁
        // FileLock lock = fileChannel.lock(0, 128, false); // 阻塞，对文件部分内容加锁，第三个参数代表是否为共享锁
        // FileLock lock = fileChannel.tryLock(); // 非阻塞，对整个文件加锁，默认为排他锁
        // FileLock lock = fileChannel.tryLock(0, 128, false); // 非阻塞，对文件部分内容加锁，第三个参数代表是否为共享锁

        // 判断方法
        // boolean shared = lock.isShared(); // 判断当前锁是否为共享锁
        // boolean valid = lock.isValid(); // 判断当前锁是否还有效

        Thread.sleep(1000000);

        // 释放锁
        lock.release();

        // 关闭 FileChannel
        fileChannel.close();
    }
}
