package com.example.utiltool2.signature;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;
import com.example.utiltool2.annotation.PermissionNeed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * author:lgh on 2019-12-19 10:31
 */
public class SignatureActivity extends BaseActivity {


    private SignaturePad mSignaturePad;
    private boolean isEmpty;//是否已签
    private boolean isEraser;//是否使用橡皮擦功能

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        setContentView(R.layout.activity_signature);
        mSignaturePad = findViewById(R.id.signature_sign);
        loadDefualtSet();//初始化画笔参数
        initSign();//签名监听
    }

    private void initSign() {
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
                isEmpty = false;
            }

            @Override
            public void onClear() {

            }
        });
    }

    //初始设置画笔参数
    private void loadDefualtSet() {
        mSignaturePad.setMinWidth(5);
        mSignaturePad.setMaxWidth(10);
        mSignaturePad.setPenColor(Color.BLACK);
    }

    //保存
    public void signSave(View view) {
        createDialogSave();
    }

    //清除上一步
    public void clearPreStep(View view) {

    }

    //清除
    public void clearAll(View view) {
        createDialogClear();
    }

    //调整画笔
    public void adjustPaint(View view) {
        PenPopup penPopup = new PenPopup(this);
        penPopup.setiBrushPresetListener(new IBrushPresetListener() {
            @Override
            public void brushPreset(BrushPreset brushPreset) {
                mSignaturePad.setBrushPreset(brushPreset);
            }
        });
        penPopup.show(view);

    }

    //橡皮擦
    public void eraser(View view) {
        isEraser = !isEraser;
        mSignaturePad.setIsmEraser(isEraser);
    }

    //旋转屏幕
    public void rotateScreen(View view) {
        rotateScreen();
    }


    //旋转屏幕
    private void rotateScreen() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    //保存
    @PermissionNeed(permissions = {Manifest.permission.ACCESS_FINE_LOCATION}, requestCode = 17)
    private void createDialogSave() {
        if (isEmpty) {
            Toast.makeText(this, "您还没有签名", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("确定要保存吗?");
            alert.setCancelable(false);
            alert.setTitle("保存");
            alert.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                            addJpgSignatureToGallery(signatureBitmap);
                            Toast.makeText(SignatureActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            finish();//只签一次
                        }
                    });
            alert.setNegativeButton("取消", null);
            alert.show();
        }
    }

    //清除
    private void createDialogClear() {
        if (isEmpty) {
            Toast.makeText(this, "您还没有签名", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("确定要清除吗?");
            alert.setCancelable(false);
            alert.setTitle("重签");
            alert.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            isEmpty = true;
                            mSignaturePad.clear();
                        }
                    });
            alert.setNegativeButton("取消", null);
            alert.show();
        }
    }

    //保存图片
    public void addJpgSignatureToGallery(Bitmap signature) {
        try {
            File photo = new File(getAlbumStorageDir("Signature"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //设置保存图片位置
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(getExternalFilesDir(albumName).getAbsolutePath());
        if (!file.mkdirs()) {
            Log.e("Signature", "Directory not created");
        }
        return file;


    }

    //保存成图片
    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }


}
