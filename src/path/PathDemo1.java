package path;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo1 {
    public static void main(String[] args) {
        // 绝对路径
        Path path1 = Paths.get("D:\\TmpDir\\channel\\a.txt");
        System.out.println(path1);

        // 相对路径
        Path path2 = Paths.get("D:\\TmpDir\\", "..\\channel\\a.txt");
        System.out.println(path2);

        // 路径标准化处理
        Path path3 = path2.normalize();
        System.out.println(path3);
    }
}
