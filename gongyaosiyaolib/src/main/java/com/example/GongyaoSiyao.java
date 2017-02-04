package com.example;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import sun.misc.BASE64Encoder;

// 弓腰撕咬的生成
public class GongyaoSiyao {
    public static void main(String[] args) {
        try {
            // 秘钥对生成器
            KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
//            rsa.initialize(4096);// 安全性级别默认1024-4096
            KeyPair keyPair = rsa.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println(new BASE64Encoder().encode(publicKey.getEncoded()));
            System.out.println(publicKey.getFormat());// 类型弓腰模式：x.509


            System.out.println("---------");
            // 撕咬
            System.out.println(new BASE64Encoder().encode(privateKey.getEncoded()));
            System.out.println(privateKey.getFormat());// 类型弓腰模式：PKCS#8
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
