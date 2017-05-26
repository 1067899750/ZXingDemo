package com.example.myapplication.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.until.DisplayUtil;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

import static cn.bingoogolapple.qrcode.zxing.QRCodeEncoder.syncEncodeQRCode;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivqr;
    private TextView tvread;
    private EditText etinput;
    private TextView tvcreate;
    private TextView tvcreatelogo;
    private ImageView ivlogo;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mContext = CreateActivity.this;
        ivlogo = (ImageView) findViewById(R.id.iv_logo);
        tvcreatelogo = (TextView) findViewById(R.id.tv_createlogo);
        tvcreate = (TextView) findViewById(R.id.tv_create);
        etinput = (EditText) findViewById(R.id.et_input);

        tvread = (TextView) findViewById(R.id.tv_read);
        ivqr = (ImageView) findViewById(R.id.iv_qr);

        tvcreatelogo.setOnClickListener(this);
        tvcreate.setOnClickListener(this);
        tvread.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_create: {  //创建二维码
                if (!checkIsEmpty()) {
                    createQRCode();
                } else {
                    Toast.makeText(mContext, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.tv_createlogo: {       //创建带logo
                if (!checkIsEmpty()) {
                    createQRCodeWithLogo();
                } else {
                    Toast.makeText(mContext, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.tv_read: {    //识别
                decodeQRCode();
                break;
            }
        }
    }


    /**
     * 校验输入框是否有内容
     * 没有内容返回true，有内容返回false
     *
     * @return
     */
    private boolean checkIsEmpty() {
        return TextUtils.isEmpty(etinput.getText().toString().trim());
    }

    /**
     * 创建二维码
     */
    private void createQRCode() {
        //生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成回调
        Bitmap bitmap = syncEncodeQRCode(etinput.getText().toString().trim(), DisplayUtil.dip2px(mContext, 60));
        if (bitmap != null) {
            ivqr.setImageBitmap(bitmap);
        } else {
            Toast.makeText(mContext, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 创建带logo二维码
     */
    private void createQRCodeWithLogo() {
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(etinput.getText().toString().trim(), DisplayUtil.dip2px(mContext, 160),
                Color.parseColor("#000000"), ((BitmapDrawable) ivlogo.getDrawable()).getBitmap());
        if (bitmap != null) {
            ivqr.setImageBitmap(bitmap);
        } else {
            Toast.makeText(mContext, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 解析
     */
    public void decodeQRCode() {
        Bitmap bitmap = ((BitmapDrawable) ivqr.getDrawable()).getBitmap();
        decode(bitmap, "解析二维码失败");
    }

    /**
     * 解析二维码,可以解析二维码、带logo二维码、条形码
     *
     * @param bitmap
     * @param err
     */
    private void decode(Bitmap bitmap, final String err) {
        String result = QRCodeDecoder.syncDecodeQRCode(bitmap);
        if (result != null) {
            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, err, Toast.LENGTH_SHORT).show();
        }
    }

}

























