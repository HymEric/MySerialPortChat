package com.erichym.myserialportchat;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import android_serialport_api.SerialPortFinder;
public class MySerialPortPreferences  extends PreferenceActivity {
    private Application mApplication;
    private SerialPortFinder mSerialPortFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplication = (Application) getApplication();
        mSerialPortFinder = mApplication.mSerialPortFinder;

        addPreferencesFromResource(R.xml.my_serial_port_preferences);

        // Devices,串口
        final ListPreference devices = (ListPreference)findPreference("DEVICE");
        String[] entries = mSerialPortFinder.getAllDevices();//展现给用户的下拉列表的值
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();//展现的用户的选择列表的每个元素选择后需要存储到手机中的值
        devices.setEntries(entries);//setEntries： 弹出的对话框中，列表显示的文本内容
//		setEntryValues ： 定义每个列表项的值。注意：每个列表项有一些文本和 一 个 值。
//		文本由entries定义，值由entryValues定义。
        devices.setEntryValues(entryValues);
        devices.setSummary(devices.getValue());//getValue ： 返回键的值,setSummary:实时显示内容变更
//		说 明： 当Preference的元素值发送改变时，触发该事件。
//		返回值：true 代表将新值写入sharedPreference文件中。
//		false 则不将新值写入sharedPreference文件.
        devices.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });

        // Baud rates，波特率
        final ListPreference baudrates = (ListPreference)findPreference("BAUDRATE");
        baudrates.setSummary(baudrates.getValue());
        baudrates.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });

        // parity，奇偶校验位
        final ListPreference parity = (ListPreference)findPreference("PARITY");
        parity.setSummary(parity.getValue());
        parity.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });

        // dataBits,数据位
        final ListPreference dataBits = (ListPreference)findPreference("PARITY");
        dataBits.setSummary(dataBits.getValue());
        dataBits.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });

        // stopBit,停止位
        final ListPreference stopBit = (ListPreference)findPreference("PARITY");
        stopBit.setSummary(stopBit.getValue());
        stopBit.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });
    }
}
