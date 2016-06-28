package myhomework.com.meilmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import myhomework.com.meilmanager.database.MessageDatabaseHandler;
import myhomework.com.meilmanager.model.EmailData;

public class ReadActivity extends Activity {
    private ImageView mImgDelete;
    private Button mBtnReply, mBtnTTL;
    private TextView mTxtToEmail, mTxtSubject, mTxtBody;
    private EmailData temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initUI();
        getEmailInfo();
    }
    public void getEmailInfo() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String emailID = extras.getString("EmailID");
        if (!emailID.equals(null)){
            final MessageDatabaseHandler messageDB = new MessageDatabaseHandler(this);
            temp = messageDB.getEmailItem(emailID);
            setData();
        }

    }
    public void setData() {
        mTxtToEmail.setText(temp.getSender());
        mTxtSubject.setText(temp.getSubject());
        mTxtBody.setText(temp.getBody());
    }
    public void initUI() {
        mImgDelete = (ImageView)findViewById(R.id.imgDelete);
        mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MessageDatabaseHandler messageDB = new MessageDatabaseHandler(v.getContext());
                messageDB.DeleteEmailInfo(temp.getEmailID());

                Intent iner = new Intent(ReadActivity.this, MainActivity.class);
                startActivity(iner);
                finish();
            }
        });
        mBtnReply = (Button) findViewById(R.id.btnReply);
        mBtnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iner = new Intent(ReadActivity.this, ComposeActivity.class);
                iner.putExtra("Name", temp.getSender());
                startActivity(iner);
                finish();
            }
        });

        mTxtToEmail = (TextView) findViewById(R.id.etxtFilter);
        mTxtSubject = (TextView) findViewById(R.id.txtSubject);
        mTxtBody    = (TextView) findViewById(R.id.txtBody);


    }
}
