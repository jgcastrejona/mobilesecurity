package com.app.mobilesecurity;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;


public class AsyncTaskParser extends AsyncTask<String, String, String> {
	
	Context context;
	boolean bandera;
	String usuario;
	String contrasena;
	String serverURL = "http://mobilesecurity.herokuapp.com/alumno/login";

	
	public AsyncTaskParser(String usuario, String contrasena){
		super();
		this.usuario = usuario;
		this.contrasena = contrasena;
	}
	@Override
	protected void onPreExecute(){
	}
	
	@Override
	protected String doInBackground(String...arg0) {
		// TODO Auto-generated method stub
		JSONObject sentUsr = new JSONObject();
		InputStream is;
		String result = "";
		try{
			sentUsr.put("usuario", usuario);
			sentUsr.put("password", contrasena);
			StringEntity strSentUsr = new StringEntity(sentUsr.toString());
			HttpPost peticion = new HttpPost(serverURL);
			HttpClient cliente = new DefaultHttpClient();
			peticion.addHeader("content-type","application/json");
			peticion.setEntity(strSentUsr);
			HttpResponse servResponse = cliente.execute(peticion);
			HttpEntity htEntidad = servResponse.getEntity();
			is =  htEntidad.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			JSONObject json = new JSONObject(result);
			String mesErr = json.getString("err");
			if(mesErr.contains("Password o usuario invalidos")){
				bandera = true;
				return result;
			}
			else{
				bandera = false;
				return result;
			}

			
		}catch(Exception e){
			e.printStackTrace();
			return result;
		}
	}
	
	
	protected void onPostExcecute(String doInBg){

	}
	
} 
