package files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesDemo {
    public static void main(String[] args) throws IOException {
        // 创建目录
//        Path path = Paths.get("D:\\TmpDir\\channel\\tmp");
//        Path directory = Files.createDirectory(path);
//        System.out.println(directory);

        // 复制文件
//        Path path1 = Paths.get("D:\\TmpDir\\channel\\a.txt");
//        Path path2 = Paths.get("D:\\TmpDir\\channel\\tmp\\c.txt");
//        // Path copy = Files.copy(path1, path2); // 目标文件存在则报错
//        Path copy = Files.copy(path1, path2, StandardCopyOption.REPLACE_EXISTING); // 覆盖原有文件
//        System.out.println(copy);

        // 移动文件（可重命名）
//        Path path3 = Paths.get("D:\\TmpDir\\channel\\tmp\\c.txt");
//        Path path4 = Paths.get("D:\\TmpDir\\channel\\tmp\\c_1.txt");
//        // Path move = Files.move(path3, path4); // 目标文件存在则报错
//        Path move = Files.move(path3, path4, StandardCopyOption.REPLACE_EXISTING); // 覆盖原有文件
//        System.out.println(move);

        // 删除文件
//        Path path5 = Paths.get("D:\\TmpDir\\channel\\tmp\\c_1.txt");
//        // Files.delete(path5); // 文件不存在会报错
//        Files.deleteIfExists(path5); // 如果存在则删除

        // 递归遍历目录树，查找文件
        Path root = Paths.get("D:\\TmpDir");
        String target = File.separator + "a.txt";
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String filepath = file.toAbsolutePath().toString();
                if (filepath.endsWith(target)) {
                    System.out.println("findFile: " + filepath);
                    return FileVisitResult.TERMINATE;
                } else {
                    return FileVisitResult.CONTINUE;
                }
            }
        });
    }
}
