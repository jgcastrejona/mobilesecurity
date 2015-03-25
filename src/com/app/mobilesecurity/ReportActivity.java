package com.app.mobilesecurity;

import java.util.concurrent.ExecutionException;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReportActivity extends Activity implements LocationListener {
	
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	protected String provider;
	protected Double lat, lon;
	protected Location loc;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

		
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
		
		final EditText txtLatitud = (EditText)findViewById(R.id.editText3);
		txtLatitud.setOnClickListener(new EditText.OnClickListener(){
			@Override
			public void onClick(View v) {
				txtLatitud.setText("");
			}	
		});
		
		final EditText txtLongitud = (EditText)findViewById(R.id.editText4);
		txtLongitud.setOnClickListener(new EditText.OnClickListener(){
			@Override
			public void onClick(View v) {
				txtLongitud.setText("");
			}	
		});
		
		final EditText txtTipo = (EditText)findViewById(R.id.editText5);
		txtTipo.setOnClickListener(new EditText.OnClickListener(){
			@Override
			public void onClick(View v) {
				txtTipo.setText("");
			}	
		});
	
		final Button sendReport = (Button)findViewById(R.id.sendReporte);
		sendReport.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editable edTitle = txtTitulo.getText();
				Editable edDescr = txtDescripcion.getText();
				Editable edLatit = txtLatitud.getText();
				Editable edLongi = txtLongitud.getText();
				Editable edTipor = txtTipo.getText();
				/*Strings para validar que los datos ya están insertados*/
				String flgTitle = edTitle.toString();
				String flgDescr = edDescr.toString();
				String flgLatit = edLatit.toString();
				String flgLongi = edLongi.toString();
				String flgTipor = edTipor.toString();
				if(flgTitle.matches("") || flgDescr.matches("") || flgLatit.matches("") || flgLongi.matches("") || flgTipor.matches("")){
					Toast.makeText(getApplicationContext(), "Por favor completa los campos", Toast.LENGTH_SHORT).show();
				}
				else{
					AsyncTaskReporter reporter = new AsyncTaskReporter(flgTitle, flgDescr, flgLatit, flgLongi, flgTipor);
					reporter.execute();
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

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		lat = location.getLatitude();
		lon = location.getLongitude();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
