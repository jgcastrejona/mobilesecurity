package com.app.mobilesecurity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ReportActivity extends Activity {
	
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	protected String provider;
	protected Double lat, lon;
	protected Location loc;
	String passedString;

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_report);
		
		
		Bundle usrDetail = getIntent().getExtras();
		if(usrDetail != null){
			passedString = usrDetail.getString("usuario");
		}
		
		final AppLocationManager appLocationManager = new AppLocationManager(ReportActivity.this);
		
		final EditText txtTitulo = (EditText)findViewById(R.id.editableTitulo);
		txtTitulo.setOnClickListener(new EditText.OnClickListener(){
			@Override
			public void onClick(View v) {
				txtTitulo.setText("");
			}	
		});
		
		final EditText txtDescripcion = (EditText)findViewById(R.id.editText2);
		txtDescripcion.setOnClickListener(new EditText.OnClickListener(){
			@Override
			public void onClick(View v) {
				txtDescripcion.setText("");
			}	
		});
		/*
		final EditText txtLatitud = (EditText)findViewById(R.id.editText1);
		txtLatitud.setOnClickListener(new EditText.OnClickListener(){
			@Override
			public void onClick(View v) {
				txtLatitud.setText("");
			}	
		});
		
		final EditText txtLongitud = (EditText)findViewById(R.id.editText3);
		txtLongitud.setOnClickListener(new EditText.OnClickListener(){
			@Override
			public void onClick(View v) {
				txtLongitud.setText("");
			}	
		});*/
		
		/*final EditText txtTipo = (EditText)findViewById(R.id.editText5);
		txtTipo.setOnClickListener(new EditText.OnClickListener(){
			@Override
			public void onClick(View v) {
				txtTipo.setText("");
			}	
		});*/
		
		final Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
		List<String> list= new ArrayList<String>();
		list.add("Robo/Asalto");
		list.add("Vandalismo");
		list.add("Incendio");		
		list.add("Accidente vial");
		list.add("Accidente peatonal");
		list.add("Emergencia Médica");
		list.add("Otro");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner1.setAdapter(dataAdapter);
		
		final Button sendReport = (Button)findViewById(R.id.sendReporte);
		sendReport.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Editable edTitle = txtTitulo.getText();
				Editable edDescr = txtDescripcion.getText();
				//Editable edLatit = txtLatitud.getText();
				//Editable edLongi = txtLongitud.getText();
				String edTipor = String.valueOf(spinner1.getSelectedItem());
				String enviadoPor = getUsrFromJSON(passedString, "matricula");
				String idEnviadoPor = getUsrFromJSON(passedString, "id"); 
				/*Strings para validar que los datos ya están insertados*/
				String flgTitle = edTitle.toString();
				String flgDescr = edDescr.toString();
				String flgLatit = appLocationManager.getLatitude();
				String flgLongi = appLocationManager.getLongitude();
				String flgTipor = edTipor;
				if(flgTitle.matches("") || flgDescr.matches("") || flgTipor.matches("")){
					Toast.makeText(getApplicationContext(), "Por favor completa los campos", Toast.LENGTH_SHORT).show();
				}
				else{
					AsyncTaskReporter reporter = new AsyncTaskReporter(idEnviadoPor, flgTitle, flgDescr, flgLatit, flgLongi, flgTipor);
					reporter.execute();
					Toast.makeText(getApplicationContext(), "El usuario que envía es: " + enviadoPor, Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), "Reporte enviado con éxito", Toast.LENGTH_SHORT).show();
					try {
						String dumRes = reporter.get().toString();
						Toast.makeText(getApplicationContext(), dumRes, Toast.LENGTH_SHORT).show();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
	
	}
	
	/*Obtiene los valores que se solicitan*/
	public String getUsrFromJSON(String passed, String keyToGet){
		String usrToSend = null;
		try {
			JSONObject usrDetailJSON = new JSONObject(passedString);
			usrToSend = usrDetailJSON.getString(keyToGet);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usrToSend;
	}
	
	
}
