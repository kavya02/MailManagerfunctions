package myhomework.com.meilmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import myhomework.com.meilmanager.adapter.EmailListViewAdapter;
import myhomework.com.meilmanager.database.ContactDatabaseHandler;
import myhomework.com.meilmanager.database.MessageDatabaseHandler;
import myhomework.com.meilmanager.model.EmailData;
import myhomework.com.meilmanager.model.UserInfomation;

public class MainActivity extends Activity {
    Context mContext;
    private ImageView mImgSetting, mImgUser, mImgEdit;
    private ListView mListEmail;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public static List<EmailData> mainData;

    private int nConter = 0;

    RunRollTask roll;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
        boolean firstStart = settings.getBoolean("firstStart", true);

        if(firstStart) {
            //display your Message here
            generate3Contact();
            generate3Message();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstStart", false);
            editor.commit();
        }

        generateKeyPair();

        initUI();

        getKeyPair();

        if(timer != null){
            timer.cancel();
        }
        timer = new Timer();;
        roll = new RunRollTask();
        timer.schedule(roll, 1000,1000);


    }
    public void generateKeyPair() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("PrivateKey", 10);
        editor.putInt("PublicKey", 12);
        editor.commit();
    }
    public void getKeyPair() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int nPrivateKey = prefs.getInt("PrivateKey", 0);
        int nPublicKey = prefs.getInt("PublicKey", 0);

        Toast.makeText(MainActivity.this, "PrivateKey="+nPrivateKey, Toast.LENGTH_LONG).show();
        Toast.makeText(MainActivity.this, "PublicKey="+nPublicKey, Toast.LENGTH_LONG).show();
    }
    public void initUI() {
        mImgSetting = (ImageView)findViewById(R.id.imgSetting);
        mImgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iner = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(iner);
                finish();
            }
        });
        mImgUser = (ImageView) findViewById(R.id.imgUser);
        mImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iner = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(iner);
                finish();
            }
        });

        mImgEdit = (ImageView) findViewById(R.id.imgEdit);
        mImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iner = new Intent(MainActivity.this, ComposeActivity.class);
                startActivity(iner);
                finish();
            }
        });

        mListEmail = (ListView) findViewById(R.id.listEmail);
        refresh();

    }

    private void refresh() {
        final MessageDatabaseHandler messageDB = new MessageDatabaseHandler(this);
        mainData = messageDB.getAllEmailInfo();
        mListEmail.setAdapter(new EmailListViewAdapter(this, (ArrayList) mainData));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void generate3Message() {
        final MessageDatabaseHandler messageDB = new MessageDatabaseHandler(this);
        messageDB.AddEmailInfo(new EmailData(
                "id",
                "Smith",
                "Love",
                "Hi Dear, I love you. from me",
                "5"
        ));
        messageDB.AddEmailInfo(new EmailData(
                "id",
                "John",
                "Study",
                "Hi Dear, I study English. from me",
                "15"
        ));
        messageDB.AddEmailInfo(new EmailData(
                "id",
                "Jane",
                "Song",
                "Hi Dear, I sing a song. from me",
                "900"
        ));
    }
    private void generate3Contact() {
        final ContactDatabaseHandler contactDB = new ContactDatabaseHandler(this);
        contactDB.AddContactInfo(new UserInfomation(
                "id",
                "Smith",
                "path",
                "123"
        ));
        contactDB.AddContactInfo(new UserInfomation(
                "id",
                "John",
                "path",
                "456"
        ));
        contactDB.AddContactInfo(new UserInfomation(
                "id",
                "Jane",
                "path",
                "789"
        ));
    }
    class RunRollTask extends TimerTask {
        @Override
        public void run() {
            nConter++;
            if (nConter == 5) {
                for (int i = 0; i < mainData.size(); i ++) {
                    if (mainData.get(i).getTime().equals("5")){
                        final MessageDatabaseHandler messageDB = new MessageDatabaseHandler(mContext);
                        messageDB.DeleteEmailInfo(mainData.get(i).getEmailID());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    }
                }
            }
            if (nConter == 15) {
                for (int i = 0; i < mainData.size(); i ++) {
                    if (mainData.get(i).getTime().equals("15")){
                        final MessageDatabaseHandler messageDB = new MessageDatabaseHandler(mContext);
                        messageDB.DeleteEmailInfo(mainData.get(i).getEmailID());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    }
                }
            }
            if (nConter == 900) {
                for (int i = 0; i < mainData.size(); i ++) {
                    if (mainData.get(i).getTime().equals("900")){
                        final MessageDatabaseHandler messageDB = new MessageDatabaseHandler(mContext);
                        messageDB.DeleteEmailInfo(mainData.get(i).getEmailID());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    }
                }
            }
        }
    }
}
