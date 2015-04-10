package com.app.mobilesecurity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		Bundle usrData = getIntent().getExtras();
		if(usrData != null){
			/*Obtenemos el valor completo enviado por el activity anterior*/
			String usrDetail = usrData.getString("enviadoPor");
			Toast.makeText(getApplicationContext(), usrDetail, Toast.LENGTH_LONG).show();
		}
	}
	
	
	
	public void goReporte(View v){
		Intent i = new Intent(this, ReportActivity.class );
		startActivity(i);
		
	}
}
