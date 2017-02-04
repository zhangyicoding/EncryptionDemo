package org.mobiletrain.encryptiondemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.mobiletrain.encryptiondemo.R;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 弓腰撕咬互为加解密,一般情况使用弓腰加密，撕咬解密
 * 多个公司合作场景，不能使用对称加密
 * 撕咬加密弓腰解密，用于确定消息来源验证
 * <p>
 * 签名
 * A向B发送数据。A和B都用各自的弓腰撕咬。A发送的原文使用B的弓腰加密后，B可使用B的撕咬解密获取内容
 * A将自己的MD5摘要使用A的撕咬加密，B使用A的弓腰解密获取到A得MD5摘要，确认数据来自A
 * C接活，只能获取A的摘要，无法伪造MD5，因为没有A的撕咬
 */
public class RSAFragment extends Fragment implements View.OnClickListener {

    // Base64 -> bytes -> gognyaosiyao
    private static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXZXXVOZEHITKyuJGWKYMjRB5RwFL/qnpKgkoKxRGcl5bNbxQEswgv7eFH2NeYksAVumzjcBY/wlTc0yDqOHzqdIoyMesDPf3PS64Gb97aJLbvsXVNX3fJ+pePfpmykSfi7IuLjURCoo5YlWcCNzAKs0KrZSaeIZySfs+TPigTLQIDAQAB";
    private static final String PRI_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJdlddU5kQchMrK4kZYpgyNEHlHAUv+qekqCSgrFEZyXls1vFASzCC/t4UfY15iSwBW6bONwFj/CVNzTIOo4fOp0ijIx6wM9/c9LrgZv3toktu+xdU1fd8n6l49+mbKRJ+Lsi4uNREKijliVZwI3MAqzQqtlJp4hnJJ+z5M+KBMtAgMBAAEC" +
            "gYB5wW0sWCmclERTmz/qheQjHotLr+KQHqhO981YL/jj+L2c5bIxvVHeSe8devSYjZzRXKZpmyEp" +
            "kb3yAhG8jEVYgM7htoAgzviKrYhS4RQoo18/OzsbfajLwLF5bUng3k53NPBpR4AS4bYUNXIqeaQY" +
            "dCASt9OK6G6JKVSZIDgUeQJBAORc5MKV4Dl08M/Pju1KvPUsPbA0SrUFktFa2d01RkZKCvmXtr5V" +
            "0DesKpXnzasYnH5jKPgDWBDcfOssajM2A8cCQQCpuAAN10q27jg+SRG1HemHDGWycMMhe70Btqvn" +
            "Gu8Qof8PwdI5ifXzHxQrjJyAIWgV4cxGTwD1Ueg2NOuu+YlrAkBujIG5qTBo4DGLiGEagmnDDm22" +
            "lTntNAuFCcQaECY69LEnbshqj8RCjzf2ZcbsovdvP7Wfio7haruWZWPr8qQVAkBZluKYa9Rp2jhg" +
            "UKwDQoX6nwWz+yhWLX5pQCdgOGyICCufAr5ZV0U4kGhSUMhwLrJGEsqFNsEXvqPosWjUFa9/AkA3" +
            "8736pqkKnOrefX8p2wZOobpu46FVHT3S8ZIpFel10AbRrnMyVO/0TKyXUh29ZMTIHj6SN5qpc5tG" +
            "hddsw0Yj";


    private EditText originalET, encryptionET;
    private Button encryptBtn, decryptBtn;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String originalString;
    private byte[] encryptionBytes;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rsa, container, false);

        originalET = (EditText) rootView.findViewById(R.id.original_et);
        encryptionET = (EditText) rootView.findViewById(R.id.encryption_et);
        encryptBtn = (Button) rootView.findViewById(R.id.encrypt_btn);
        decryptBtn = (Button) rootView.findViewById(R.id.decrypt_btn);
        encryptBtn.setOnClickListener(this);
        decryptBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            KeyFactory factory = KeyFactory.getInstance("rsa");
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(Base64.decode(PUB_KEY, Base64.NO_WRAP));
            publicKey = factory.generatePublic(x509);
            PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(Base64.decode(PRI_KEY, Base64.NO_WRAP));
            privateKey = factory.generatePrivate(pkcs8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            switch (view.getId()) {
                case R.id.encrypt_btn:
                    originalString = originalET.getText().toString();
                    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                    encryptionBytes = cipher.doFinal(originalString.getBytes());
                    encryptionET.setText(Base64.encodeToString(encryptionBytes, Base64.NO_WRAP));
                    break;
                case R.id.decrypt_btn:
                    cipher.init(Cipher.DECRYPT_MODE, privateKey);
                    String encryption = encryptionET.getText().toString();
                    byte[] originalBytes = cipher.doFinal(Base64.decode(encryption, Base64.NO_WRAP));
//                originalET.setText(new String{originalBytes, 0, originalBytes.length});
                    originalET.setText(new String(originalBytes));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
