package com.app.mobilesecurity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {
	String usrDetail;
	String usrToShow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);

		
		Bundle usrData = getIntent().getExtras();
		if(usrData != null){
			/*Obtenemos el valor completo enviado por el activity anterior*/
			usrDetail = usrData.getString("enviadoPor");
			Toast.makeText(getApplicationContext(), usrDetail, Toast.LENGTH_SHORT).show();
		}

	}
	
	public void goReporte(View v){
		Intent i = new Intent(this, ReportActivity.class );
		i.putExtra("usuario", usrDetail.toString());
		startActivity(i);
		
	}
	
	public String getUsrFromJSON(String passed, String keyToGet){
		String usrToSend = null;
		try {
			JSONObject usrDetailJSON = new JSONObject(passed);
			usrToSend = usrDetailJSON.getString(keyToGet);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usrToSend;
	}
	
}
