package com.example.myapplication.activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends AppCompatActivity implements QRCodeView.Delegate{
    private ZXingView mQR;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        textView = (TextView) findViewById(R.id.text_view);

        //设置结果处理
        mQR = (ZXingView) findViewById(R.id.zx_view);

        //设置结果处理
        mQR.setDelegate(this);

        //开始读取二维码
        mQR.startSpot();


    }

    /**
     * 扫描二维码方法大全（已知）
     * <p>
     * mQR.startCamera();               开启预览，但是并未开始识别
     * mQR.stopCamera();                停止预览，并且隐藏扫描框
     * mQR.startSpot();                 开始识别二维码
     * mQR.stopSpot();                  停止识别
     * mQR.startSpotAndShowRect();      开始识别并显示扫描框
     * mQR.stopSpotAndHiddenRect();     停止识别并隐藏扫描框
     * mQR.showScanRect();              显示扫描框
     * mQR.hiddenScanRect();            隐藏扫描框
     * mQR.openFlashlight();            开启闪光灯
     * mQR.closeFlashlight();           关闭闪光灯
     * <p>
     * mQR.startSpotDelay(ms)           延迟ms毫秒后开始识别
     */

    /**
     * 扫描二维码成功
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {

        Toast.makeText(ScanActivity.this, result, Toast.LENGTH_SHORT).show();
        textView.setText(result);

        //震动
        vibrate();
        //停止预览
        mQR.stopCamera();

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(ScanActivity.this, "打开相机出错！请检查是否开启权限！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mQR.startCamera();
        mQR.startSpotAndShowRect();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mQR.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

















