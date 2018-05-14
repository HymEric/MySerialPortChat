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

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;


import android.content.SharedPreferences;
import android.util.Log;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

//在Android中，可以通过继承Application类来实现应用程序级的全局变量，
//		这种全局变量方法相对静态类更有保障，直到应用的所有Activity全部被destory掉之后才会被释放掉。
public class Application extends android.app.Application {

	public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
	private SerialPort mSerialPort = null;

	public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
		if (mSerialPort == null) {
			/* Read serial port parameters */
			SharedPreferences sp = getSharedPreferences("com.erichym.myserialportchat_preferences", MODE_PRIVATE);
			String path = sp.getString("DEVICE", "");
			int baudrate = Integer.decode(sp.getString("BAUDRATE", "-1"));
			/*新加的设置奇偶校验、数据位、停止位*/
			int parity=Integer.decode(sp.getString("PARITY","-1"));
			int dataBits=Integer.decode(sp.getString("DATABITS","-1"));
			int stopBit=Integer.decode(sp.getString("STOPBIT","-1"));


			Log.e("TAG","path="+path+"     baudrate="+baudrate+"     parity="+parity+
					"    dataBits="+dataBits+"    stopBit="+stopBit);
			/* Check parameters */
			if ( (path.length() == 0) || (baudrate == -1)||(parity==-1)||(dataBits==-1)||(stopBit==-1)) {
				throw new InvalidParameterException();
			}

			/* Open the serial port */
			mSerialPort = new SerialPort(new File(path), baudrate, parity,dataBits,stopBit);
		}
		return mSerialPort;
	}

	public void closeSerialPort() {
		if (mSerialPort != null) {
			mSerialPort.close();
			mSerialPort = null;
		}
	}
}
