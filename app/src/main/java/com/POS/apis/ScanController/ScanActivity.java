package com.POS.apis.ScanController;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.POS.apis.R;
import com.POSD.controllers.ScanController;
import com.POSD.controllers.ScanController.Callback;
import com.POSD.util.Tools;

public class ScanActivity extends Activity implements OnClickListener {

	private final String Tag = "ScanActivity";
	private TextView tv_all_count , tv_success_count;
	private TextView main_tv, tv_codeid;
	private Button start_bu1, stop_bu2 , clean_bu , start_continue , stop_continue , open_bu3 , close_bu4 ;
	private ScrollView scrollview;
	private long nowTime = 0;
	private long lastTime = 0;
	
	private final int Handler_SHOW_RESULT = 1999;
	private final int Handler_CLEAN = 2000;
	private BeepManager beepManager;
	private ScanController barcodeManager;
	private byte[] codeBuffer;
	private String codeId;

	private UpadateThread upadateThread;
	private int success_count;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Handler_SHOW_RESULT:
				tv_codeid.setText("");
				if (null != codeId) {
					tv_codeid.setText(codeId);
				}
				if (null != codeBuffer) {
					String codeType = Tools.returnType(codeBuffer);
					String val = null;
					if (codeType.equals("default")) {
						val = new String(codeBuffer);
					} else {
						try {
							val = new String(codeBuffer, codeType);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					main_tv.append(val + "\n");
					success_count++;
					tv_success_count.setText(""+success_count);
					
					Log.d(Tag, val);
					beepManager.play();
					scrollview.fullScroll(ScrollView.FOCUS_DOWN);
				}
				break;
				
			case Handler_CLEAN:
				tv_codeid.setText("");
				main_tv.setText("");
				break;
				
			case 777:// 刷新UI
				if(null != barcodeManager){
					tv_all_count.setText(""+barcodeManager.getScan_count());
				}
				tv_success_count.setText(""+success_count);
				break;
				
			case 888:
				if(null != upadateThread){
					upadateThread.run = false;
					upadateThread = null;
				}
				break;
				
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barcode);
		initView();

		beepManager = new BeepManager(this, true, false);
		if (barcodeManager == null) {
			barcodeManager = ScanController.getInstance();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(null != upadateThread){
			upadateThread.run = false;
			upadateThread = null;
		}
		
		if (null != barcodeManager) {
			barcodeManager.Barcode_Close();
			barcodeManager.Barcode_Stop();
		}
		success_count = 0;
		barcodeManager.clearScan_count();
		mHandler.sendEmptyMessage(Handler_CLEAN);
		mHandler.sendEmptyMessage(777);
		super.onDestroy();
	}

	private void initView() {
		tv_all_count = (TextView)findViewById(R.id.tv_all_count);
		tv_success_count = (TextView)findViewById(R.id.tv_success_count);
		
		scrollview = (ScrollView) findViewById(R.id.scrollview);
		main_tv = (TextView) findViewById(R.id.main_tv);
		tv_codeid = (TextView) findViewById(R.id.tv_codeid);
		start_bu1 = (Button) findViewById(R.id.start_bu1);
		stop_bu2 = (Button) findViewById(R.id.stop_bu2);
		clean_bu = (Button) findViewById(R.id.clean_bu);
		start_continue = (Button) findViewById(R.id.start_continue);
		stop_continue = (Button) findViewById(R.id.stop_continue);
		open_bu3 = (Button) findViewById(R.id.open_bu3);
		close_bu4 = (Button) findViewById(R.id.close_bu4);
		start_bu1.setOnClickListener(this);
		stop_bu2.setOnClickListener(this);
		
		setVisble(false);
	}
	
	private void setVisble(boolean b){
		start_bu1.setEnabled(b);
		stop_bu2.setEnabled(b);
		clean_bu.setEnabled(b);
		start_continue.setEnabled(b);
		stop_continue.setEnabled(b);
	}

	Callback dataReceived = new Callback() {

		@Override
		public void Barcode_Read(byte[] buffer, String codeId, int errorCode) {
			// TODO Auto-generated method stub
			if (null != buffer) {
				codeBuffer = buffer;
				ScanActivity.this.codeId = codeId;
				Message msg = new Message();
				msg.what = Handler_SHOW_RESULT;
				mHandler.sendMessage(msg);
				barcodeManager.Barcode_Stop();
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.clean_bu:
			success_count = 0;
			barcodeManager.clearScan_count();
			mHandler.sendEmptyMessage(Handler_CLEAN);
			mHandler.sendEmptyMessage(777);
			break;
			
		case R.id.start_continue:
			barcodeManager.Barcode_Continue_Start(500);
			
			upadateThread = new UpadateThread();
			upadateThread.run = true;
			upadateThread.start();
			break;
			
		case R.id.stop_continue:
			barcodeManager.Barcode_Continue_Stop();
			mHandler.sendEmptyMessageDelayed(888, 500);
			break;
			
		case R.id.start_bu1:
			nowTime = System.currentTimeMillis();
			barcodeManager.Barcode_Stop();
			// 按键时间不低于200ms
			if (nowTime - lastTime > 200) {
				System.out.println("scan(0)");
				if (null != barcodeManager) {
					mHandler.sendEmptyMessage(777);
					barcodeManager.Barcode_Start();
				}
				lastTime = nowTime;
			}
			break;

		case R.id.stop_bu2:
			barcodeManager.Barcode_Stop();
			break;

		case R.id.open_bu3:
			int ret = barcodeManager.Barcode_Open(this, dataReceived);
			setVisble(true);
			break;

		case R.id.close_bu4:
			if (null != barcodeManager) {
				barcodeManager.Barcode_Close();
				barcodeManager.Barcode_Stop();
			}
			setVisble(false);
			break;

		default:
			break;
		}
	}

	
	private class UpadateThread extends Thread {
		public boolean run;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (run) {
				mHandler.sendEmptyMessage(777);
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			mHandler.removeMessages(777);
		}
	}
}
