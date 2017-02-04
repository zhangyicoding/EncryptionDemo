package org.mobiletrain.encryptiondemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.mobiletrain.encryptiondemo.R;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * des/3des/aes对称加密：只有一个密钥，加密解密。密钥长度8为。3DES长度24位。AES长度32位
 *
 * 3des分成3个des密钥。使用第一个密钥加密，使用第二个密钥解密，得到新的byte数组，再用第三位密钥加密。三段des密钥必须不同
 *
 * 致命问题：手机端必须有密钥。有合作关系时也是。拿到密钥解析另一个人的数据。所以使用非对称加密
 *
 * 撕咬不屑露。弓腰是社会上所有人使用的。申请弓腰，并加密，传给支付宝，支付宝用自己的撕咬解密。
 *
 * des只能对8位byte[]加密。将多数据分为8位一段的块数据。结尾不足8位，不足补0.正好8位的补8个0.一定以0结尾
 */
public class DESFragment extends Fragment implements View.OnClickListener {

    private EditText originalET, encryptionET, keyET;
    private Button encryptionBtn, descryptionBtn;
    private String algorithm = "DESede";// 算法名DES、DESede、AES
    private final int KEY_LENGTH = 24;// 8、24、32

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_des, container, false);
        originalET = (EditText) rootView.findViewById(R.id.original_et);
        encryptionET = (EditText) rootView.findViewById(R.id.encryption_et);
        keyET = (EditText) rootView.findViewById(R.id.key_et);
        encryptionBtn = (Button) rootView.findViewById(R.id.encryption_btn);
        descryptionBtn = (Button) rootView.findViewById(R.id.descryption_btn);
        encryptionBtn.setOnClickListener(this);
        descryptionBtn.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View view) {
        String keyString = keyET.getText().toString();
        if (!TextUtils.isEmpty(keyString)) {
            // 处理成8位密钥（des ）
            byte[] key = Arrays.copyOf(keyString.getBytes(), KEY_LENGTH);
            algorithm = "DES";
            SecretKeySpec desKey = new SecretKeySpec(key, algorithm);
            try {
                // 加密对象
                Cipher cipher = Cipher.getInstance("DES");
                switch (view.getId()) {
                    case R.id.encryption_btn:// 获取原文对应的byte数组，获取密文后转成base64编码格式
                        cipher.init(Cipher.ENCRYPT_MODE, desKey);
                        String originalString = originalET.getText().toString();
                        byte[] encryptBytes = cipher.doFinal(originalString.getBytes());
                        String encryptString = Base64.encodeToString(encryptBytes, Base64.NO_WRAP);
                        encryptionET.setText(encryptString);
                        break;
                    case R.id.descryption_btn:// 获取密文并使用base64解码，解为原文数组并转为文本
                        cipher.init(Cipher.DECRYPT_MODE, desKey);
                        String encryptString2 = encryptionET.getText().toString();
                        byte[] originalByte2 = cipher.doFinal(Base64.decode(encryptString2, Base64.NO_WRAP));
                        originalET.setText(new String(originalByte2 ));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
