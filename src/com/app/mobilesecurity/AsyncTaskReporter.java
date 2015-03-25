package com.app.mobilesecurity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncTaskReporter extends AsyncTask<String, String, String>{

	String titulo;
	String descripcion;
	String latitud;
	String longitud;
	String tipo;
	String serverURL = "http://mobilesecurity.herokuapp.com/reports";
	
	public AsyncTaskReporter(String titulo, String descripcion, String latitud, String longitud, String tipo){
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.tipo = tipo;
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		JSONObject sentRep = new JSONObject();
		InputStream is;
		String reporteOK = "";
		/*Comienza el parseo JSON para enviar el reporte*/
		try{
			sentRep.put("titulo", titulo);
			sentRep.put("descripcion", descripcion);
			sentRep.put("latitud", latitud);
			sentRep.put("longitud", longitud);
			sentRep.put("tipo", tipo);
			StringEntity strReport = new StringEntity(sentRep.toString());
			HttpPost peticion = new HttpPost(serverURL);
			HttpClient cliente = new DefaultHttpClient();
			peticion.addHeader("content-type","application/json");
			peticion.setEntity(strReport);
			HttpResponse reporteResponse = cliente.execute(peticion);
			HttpEntity repEntidad = reporteResponse.getEntity();
			is = repEntidad.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				sb.append(line + "\n");
			}
			is.close();
			reporteOK = sb.toString();
			JSONObject jsonr = new JSONObject(reporteOK);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return reporteOK;
	}
	protected void onPostExecute(){
		
	}

}
