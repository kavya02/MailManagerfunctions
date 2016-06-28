package myhomework.com.meilmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import myhomework.com.meilmanager.adapter.EmailListViewAdapter;
import myhomework.com.meilmanager.database.MessageDatabaseHandler;
import myhomework.com.meilmanager.model.EmailData;

public class ComposeActivity extends Activity {
    private ImageView mImgDelete, mImgProcess;
    private Button mBtnSent;
    private EditText mETxtToEmail, mETxtSubject, mETxtBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        initUI();
        SetName();
    }
    public void SetName() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String strName = extras.getString("Name");
        if (!strName.equals(null)){
            mETxtToEmail.setText(strName);
        }
    }
    public void initUI() {
        mImgDelete = (ImageView)findViewById(R.id.imgDelete);
        mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iner = new Intent(ComposeActivity.this, MainActivity.class);
                startActivity(iner);
                finish();
            }
        });
        mImgProcess = (ImageView) findViewById(R.id.imgProcess);

        mBtnSent = (Button) findViewById(R.id.buttonSent);
        mBtnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mETxtToEmail.getText().length() >0
                        && mETxtBody.getText().length() >0
                        && mETxtSubject.getText().length()>0){
                    final MessageDatabaseHandler messageDB = new MessageDatabaseHandler(v.getContext());
                    messageDB.AddEmailInfo(new EmailData("",
                            mETxtToEmail.getText().toString(),
                            mETxtSubject.getText().toString(),
                            mETxtBody.getText().toString(),
                            "time"));
                    Toast.makeText(v.getContext(), "Encrypted Message!", Toast.LENGTH_LONG).show();

                    Intent iner = new Intent(ComposeActivity.this, MainActivity.class);
                    startActivity(iner);
                    finish();
                } else {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Invalid Info")
                            .setMessage("Please type again!")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    return;
                                }
                            }).create().show();
                }
            }
        });

        mETxtToEmail = (EditText) findViewById(R.id.etxtToEmail);
        mETxtToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iner = new Intent(ComposeActivity.this, ContactsActivity.class);
                startActivity(iner);
                finish();
            }
        });
        mETxtSubject = (EditText) findViewById(R.id.etxtSubject);
        mETxtBody    = (EditText) findViewById(R.id.etxtBody);


    }
}
