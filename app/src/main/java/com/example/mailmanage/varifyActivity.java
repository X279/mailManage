package com.example.mailmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sendAndVarify.varifyAddrClass;

public class varifyActivity extends AppCompatActivity {

    public EditText address;
    public Button varify;
    public Handler mHandle;

    class Mhandle extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(varifyActivity.this,
                            "该地址是合法邮箱地址",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(varifyActivity.this,
                            "该地址不是合法邮箱地址",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify);

        address = findViewById(R.id.varifyAddress);
        varify = findViewById(R.id.varifyButton);
        mHandle = new Mhandle();
        varify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(){
                    @Override
                    public void run(){
                        String addr = address.getText().toString();
                        Message msg = Message.obtain();

                        varifyAddrClass varifyAddrClass = new varifyAddrClass(addr);
                        boolean res = varifyAddrClass.varifyEmail();
                        if(res)
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
