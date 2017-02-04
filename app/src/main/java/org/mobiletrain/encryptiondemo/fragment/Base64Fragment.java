package org.mobiletrain.encryptiondemo.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.mobiletrain.encryptiondemo.R;

import java.io.ByteArrayOutputStream;

/**
 * base64编码，64进制，实现非文本数据和文本数据的转换
 */
public class Base64Fragment extends Fragment implements View.OnClickListener {
    private Button encodeBtn;
    private Button decodeBtn;
    private TextView base64TextView;
    private ImageView iv;
    private String base64Code;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base64, container, false);
        encodeBtn = (Button) rootView.findViewById(R.id.encode_btn);
        decodeBtn = (Button) rootView.findViewById(R.id.decode_btn);
        base64TextView = (TextView) rootView.findViewById(R.id.base64_tv);
        iv = (ImageView) rootView.findViewById(R.id.iv);
        encodeBtn.setOnClickListener(this);
        decodeBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.encode_btn:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes = baos.toByteArray();
                // 将数据转换为文本
                base64Code = Base64.encodeToString(bytes, Base64.NO_WRAP);
                base64TextView.setText(base64Code);
                break;
            case R.id.decode_btn:
                // 讲文本解码为数据
                byte[] bytes1 = Base64.decode(base64Code, Base64.NO_WRAP);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
                iv.setImageBitmap(bitmap1);
                break;
        }
    }
}
