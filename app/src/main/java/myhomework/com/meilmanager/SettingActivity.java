package myhomework.com.meilmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingActivity extends Activity {
    private ImageView mImgProfilePhoto;

    private TextView mTxtServerAddress, mTxtName, mTxtKeyPair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initUI();
    }
    public void initUI() {
        mImgProfilePhoto = (ImageView)findViewById(R.id.imgProfilePhoto);

        mTxtServerAddress = (TextView) findViewById(R.id.txtServerAddress);
        mTxtName = (TextView) findViewById(R.id.txtPublicKey);
        mTxtKeyPair    = (TextView) findViewById(R.id.txtKeyPair);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
