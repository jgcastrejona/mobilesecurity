package com.app.mobilesecurity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
	}
	
	public void goReporte(View v){
		Intent i = new Intent(this, ReportActivity.class );
		startActivity(i);
		
	}
}
