package app.utils;

import cn.hutool.core.codec.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description：用于密码加密的工具类
 * <p>Date: 2023/12/1
 * <p>Time: 23:37
 *
 * @Author SillyBaka
 **/
public class PwdUtil {

    public static String encodePwd(String password) {
        // 将密码经过md5加密
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update(password.getBytes());
        byte[] bytes = digest.digest();

        // 再对bytes进行base64加密
        return Base64.encode(bytes);
    }
}
