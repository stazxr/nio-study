package charset;

import java.nio.charset.Charset;

public class CharsetDemo1 {
    public static void main(String[] args) {
        // 获取虚拟机默认的编码方式
        System.out.println(Charset.defaultCharset());

        // 获取系统支持的所有的编码方式
        System.out.println(Charset.availableCharsets());
    }
}
