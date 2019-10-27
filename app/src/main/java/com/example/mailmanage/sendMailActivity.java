package com.example.mailmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sendAndVarify.sendMail;
import com.sendAndVarify.varifyAddrClass;

public class sendMailActivity extends AppCompatActivity {

    public EditText address;
    public EditText subject;
    public EditText info;
    public Button send;
    public Handler mHandle;

    class Mhandle extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(sendMailActivity.this,
                            "发送成功！",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(sendMailActivity.this,
                            "发送失败！",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);


        address = findViewById(R.id.address);
        subject = findViewById(R.id.subject);
        info = findViewById(R.id.info);
        send = findViewById(R.id.send);
        mHandle = new Mhandle();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(){
                    @Override
                    public void run(){
                        String addr = address.getText().toString();
                        Message msg = Message.obtain();
                        try{
                            Thread.sleep(1000);

                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        String add = address.getText().toString();
                        String sub = subject.getText().toString();
                        String infom = info.getText().toString();
                        sendMail s = new sendMail(add,sub,infom);
                        boolean res = s.send();
                        if (res)
                            msg.what = 1;
                        else
                            msg.what = 0;
                        mHandle.sendMessage(msg);
                    }
                }.start();
            }
        });
    }
}
