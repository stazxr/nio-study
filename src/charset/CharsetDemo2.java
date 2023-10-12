package charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

public class CharsetDemo2 {
    public static void main(String[] args) throws CharacterCodingException {
        // 获取 Charset 对象
        // Charset charset = StandardCharsets.UTF_8;
        Charset charset = Charset.forName("UTF-8");

        // 创建缓冲区
        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("你好世界。今年是 2023年");
        buffer.flip();

        // 编码
        CharsetEncoder charsetEncoder = charset.newEncoder();
        ByteBuffer byteBuffer = charsetEncoder.encode(buffer);
        System.out.println("编码后的结果：");
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println(byteBuffer.get());
        }

        // 解码
        byteBuffer.flip();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer charBuffer = charsetDecoder.decode(byteBuffer);
        System.out.println("解码后的结果：" + charBuffer);
    }
}
