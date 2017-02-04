package org.mobiletrain.encryptiondemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mobiletrain.encryptiondemo.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5校验
 */
public class MD5Fragment extends Fragment implements View.OnClickListener {

    private EditText editText;
    private TextView textView;
    private Button md5Btn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_md5, container, false);
        TextInputLayout textInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout);
        editText = textInputLayout.getEditText();
        textView = (TextView) rootView.findViewById(R.id.result_tv);
        md5Btn = (Button) rootView.findViewById(R.id.md5_btn);
        md5Btn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] digest = md5.digest(editText.getText().toString().getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02X", b));
            }
            textView.setText(sb.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
