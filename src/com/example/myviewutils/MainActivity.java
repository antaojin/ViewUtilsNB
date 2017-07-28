package com.example.myviewutils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private int num;
	
	@ViewInject(R.id.tv1)
	private TextView tvdddd;
	
	@ViewInject(R.id.tv2)
	private TextView tv2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		
		Log.d("MainActivity", "tv1="+tvdddd.getText());
		Log.d("MainActivity", "tv2="+tv2.getText());
	}
	
	@OnClick({R.id.btn1,R.id.btn2})
	private void test(View view){
		switch (view.getId()) {
		case R.id.btn1:
			Toast.makeText(this, "btn1", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn2:
			Toast.makeText(this, "btn2", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
	

	
}
