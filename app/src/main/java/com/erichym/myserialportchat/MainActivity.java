package com.erichym.myserialportchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;

public class MainActivity extends SerialPortActivity {

    private static final String TAG = MyConsoleActivity.class.getSimpleName();

    private EditText getSerialPortContentEt = null;
    private EditText sendSerialPortContentEt = null;
    private Button sendSerialPortContentBtn = null;
    private Button clearSerialPortContentBtn = null;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOverFlowMenu();//添加菜单方法

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
                    Toast.makeText(MainActivity.this,"发送内容不能为空",Toast.LENGTH_SHORT).show();
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

//        final Button buttonSetup = (Button)findViewById(R.id.ButtonSetup);
//        buttonSetup.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MySerialPortPreferences.class));
//            }
//        });

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

    private void getOverFlowMenu(){
        try{
            ViewConfiguration config=ViewConfiguration.get(this);//得到一个已经设置好设备的显示密度的对象
            //反射获取其中的方法sHasPermanentMenuKey()，他的作用是报告设备的菜单是否对用户可用，
            // 如果不可用可强制可视化。
            Field menuKeyField=ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField!=null){
                //强制设置参数，让其重绘三个点
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config,false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 填充menu的main.xml文件; 给action bar添加条目
        getMenuInflater().inflate(R.menu.setting, menu);
        menu.add(0, 0, 1, "设置");// 相当于在res/menu/main.xml文件中，给menu增加一个新的条目item，这个条目会显示title标签的文字（如备注1）
       // menu.add(0, 1, 2, "备注2");//第二个参数代表唯一的item ID.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0://显示由menu.add()方法增加内容item的ID
                //Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                Log.e("setting","click setting");
                startActivity(new Intent(MainActivity.this, MySerialPortPreferences.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
