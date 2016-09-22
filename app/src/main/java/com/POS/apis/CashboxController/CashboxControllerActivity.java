package com.POS.apis.CashboxController;

import com.POS.apis.R;
import com.POSD.controllers.CashboxController;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CashboxControllerActivity extends Activity implements
		OnClickListener {
	private Button btn = null;
	private CashboxController cashboxController = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cashbox_controller_layout);
		btn = (Button) findViewById(R.id.cashbox_open);
		btn.setOnClickListener(this);
		cashboxController = CashboxController.getInstance();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.cashbox_open:
			Controller();
			break;
		default:
			break;
		}
	}

	private void Controller() {
		// TODO Auto-generated method stub
		int flag = cashboxController.CashboxController_Controller();
		if (-1 == flag) {
			Toast.makeText(this, "CashboxController_Failure",
					Toast.LENGTH_SHORT).show();
		} else if (0 == flag) {
			Toast.makeText(this, "CashboxController_Success",
					Toast.LENGTH_SHORT).show();
		}
	}
}
