package com.example.myapplication.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class CreateBarActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivqr;
    private TextView tvread;
    private EditText etinput;
    private TextView tvcreate;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bar);
        mContext = CreateBarActivity.this;
        tvcreate = (TextView) findViewById(R.id.tv_create);
        etinput = (EditText) findViewById(R.id.et_input);

        tvread = (TextView) findViewById(R.id.tv_read);
        ivqr = (ImageView) findViewById(R.id.iv_qr);

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
     * 创建条形码
     */
    private void createQRCode() {
        //生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成回调
        Bitmap bitmap = null;
        try {
            bitmap = createBarCode(etinput.getText().toString().trim(), 600, 300);
            if (bitmap != null) {
                ivqr.setImageBitmap(bitmap);
            } else {
                Toast.makeText(mContext, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     * 解析条形码
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


    /**
     * 生成条形码图片
     * @param str 要往二维码中写入的内容,需要utf-8格式
     * @param width 图片的宽
     * @param height 图片的高
     * @return 返回一个条形bitmap
     * @throws Exception
     */
    public  Bitmap createBarCode(String str, Integer width, Integer height) throws Exception{
        BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width,
                height, getEncodeHintMap());
        return BitMatrixToBitmap(bitMatrix);
    }

    /**
     * 获得设置好的编码参数
     * @return 编码参数
     */
    private static Hashtable<EncodeHintType, Object> getEncodeHintMap() {
        Hashtable<EncodeHintType, Object> hints= new Hashtable<EncodeHintType, Object>();
        //设置编码为utf-8
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 设置QR二维码的纠错级别——这里选择最高H级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        return hints;
    }

    /**
     * BitMatrix转换成Bitmap
     *
     * @param matrix
     * @return
     */
    private static Bitmap BitMatrixToBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[offset + x] = BLACK; //上面图案的颜色
                } else {
                    pixels[offset + x] = WHITE;//底色
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        Log.e("hongliang", "width:" + bitmap.getWidth() + " height:" + bitmap.getHeight());
        return bitmap;
    }


}








