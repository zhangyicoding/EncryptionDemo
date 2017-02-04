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
import android.widget.Toast;

import org.mobiletrain.encryptiondemo.R;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SignFragment extends Fragment implements View.OnClickListener {

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

    EditText signET;
    private Button signBtn, verifyBtn;

    private EditText originalET;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String originalString;
    private byte[] encryptionBytes;
    private byte[] signBytes;
    private String signString;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign, container, false);
        originalET = (EditText) rootView.findViewById(R.id.original_et);
        signET = (EditText) rootView.findViewById(R.id.sign_et);
        signBtn = (Button) rootView.findViewById(R.id.sign_btn);
        verifyBtn = (Button) rootView.findViewById(R.id.ver_btn);
        signBtn.setOnClickListener(this);
        verifyBtn.setOnClickListener(this);
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
        originalString = originalET.getText().toString();
        try {
            Signature signature = Signature.getInstance("MD5WithRSA");
            switch (view.getId()) {
                case R.id.sign_btn:
                    signature.initSign(privateKey);
                    signature.update(originalString.getBytes());
                    signBytes = signature.sign();
                    signET.setText(Base64.encodeToString(signBytes, Base64.NO_WRAP));
                    break;
                case R.id.ver_btn:
                    signString = signET.getText().toString();
                    signature.initVerify(publicKey);
                    signature.update(originalString.getBytes());
                    if (signature.verify(Base64.decode(signString, Base64.NO_WRAP))) {
                        Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
