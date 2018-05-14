/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.erichym.myserialportchat;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import android_serialport_api.SerialPortFinder;

public class SerialPortPreferences extends PreferenceActivity {

	private Application mApplication;
	private SerialPortFinder mSerialPortFinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mApplication = (Application) getApplication();
		mSerialPortFinder = mApplication.mSerialPortFinder;

		addPreferencesFromResource(R.xml.serial_port_preferences);

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
	}
}
