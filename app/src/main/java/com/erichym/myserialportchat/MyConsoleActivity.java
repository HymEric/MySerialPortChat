package com.erichym.myserialportchat;

import java.io.IOException;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MyConsoleActivity extends SerialPortActivity {

    private static final String TAG = MyConsoleActivity.class.getSimpleName();

    private EditText getSerialPortContentEt = null;
    private EditText sendSerialPortContentEt = null;
    private Button sendSerialPortContentBtn = null;
    private Button clearSerialPortContentBtn = null;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_console);

        getSerialPortContentEt=findViewById(R.id.get_serialport_text);
        sendSerialPortContentEt=findViewById(R.id.send_serialport_text);
        sendSerialPortContentEt.setFocusable(true);
        sendSerialPortContentEt.setFocusableInTouchMode(true);
        sendSerialPortContentEt.requestFocus();//获取焦点，光标出现

        sendSerialPortContentBtn=findViewById(R.id.send_serialport_text_btn);
        clearSerialPortContentBtn=findViewById(R.id.clear_get_serialport_text_btn);
        //清空按钮事件
        clearSerialPortContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSerialPortContentEt.setText("");
            }
        });
        //发送按钮点击事件
        sendSerialPortContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=sendSerialPortContentEt.getText().toString();
                //如果输入为空
                if(message.equals("")){
                    Toast.makeText(MyConsoleActivity.this,"发送内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                //读入输入文本并写入串口
                char[] text=new char[message.length()];
                for(int i=0;i<message.length();i++){
                    text[i]=message.charAt(i);
                }
                try{
                    mOutputStream.write(new String(text).getBytes());
                    mOutputStream.write('\n');
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(getSerialPortContentEt!=null){
                    result=new String(buffer,0,size);
                    getSerialPortContentEt.append(result);
                    Log.e(TAG,"result="+getSerialPortContentEt.getText().toString());
                }
            }
        });

    }
}
