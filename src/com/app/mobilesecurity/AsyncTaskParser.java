/*AsyncTaskPArser.java: es la clase utilizada para realizar la operaci�n de login al sistema
 * de alertas de la plataforma MobileSecurity. Se vale de operaciones POST realizadas al servidor
 * a trav�s de HTTP; para relizar dicha tarea la aplicaci�n debe contar con acceso total a la red, 
 * mismo que es configurado a trav�s de un permiso agregado en el MANIFEST del paquete de la aplicaci�n*/

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

/*A partir de Android 4.4.x es preciso realizar operaciones complejas fuera del Activity principal
 * por ello se utilizan las tareas as�ncronas, las cuales son un equivalente a los hilos en aplicaciones
 * de escritorio. Las tareas as�ncronas se indican en el c�digo extendiendo la interfaz AsyncTask
 * y sobrecargando los m�todos onPreExecute, doInbackground y onPostExectute*/
 

/*La clase extiende la interface AsyncTask, la cual cuenta con par�metros variables que en este caso son
 * del tipo String, ya que son los valores que cada m�todo sobrecargado de esta interface va a devolver.
 * */
public class AsyncTaskParser extends AsyncTask<String, String, String> {
	/*Variables globales*/
	Context context; /* Utilizado para almacenar el contexto de la aplicaci�n y poder utilizar los Toast*/
	boolean bandera; /* Valor booleano que su utiliza para validar si se hall� un error dentro de la respuesta del POST*/
	String usuario; /* Valor a enviar a trav�s de la petici�n como par�metro de login*/
	String contrasena; /*Valor a enviar a trav�s de la petici�n como par�metro de login*/
	String serverURL = "http://mobilesecurity.herokuapp.com/alumno/login"; /*URL del recurso en el servidor*/
	/*Para utilizar la tarea as�ncrona redefinimos el contructor principal utilizando 
	 * el m�todo 'super();' para poder llamar a los m�todos de la superclase una vez que AsyncTask
	 * se ha instanciado dentro de la activity principal, adem�s de poder darle parametros al utilizarlo. 
	 * En este constructor indicaremos que recibir� dos datos de tipo Strins, mismos que se pasar�n
	 * al m�todo doInBackGround()*/
	public AsyncTaskParser(String usuario, String contrasena){
		super();
		this.usuario = usuario;
		this.contrasena = contrasena;
	}
	/*Dado que no requerimos que se realice acci�n alguna previo a ejecutar el la petici�n POST
	 * dejamos este m�todo en blanco*/ 
	@Override
	protected void onPreExecute(){
	}
	/*En este m�todo se definen todas las operaciones a realizar en la petici�n POST al servidor
	 * Recibe como par�metros los Strings definidos en el constructor al instanciar la clase en el
	 * Act�vity principal*/
	@Override
	protected String doInBackground(String...arg0) {
		// TODO Auto-generated method stub
		/*Variables locales*/
		JSONObject sentUsr = new JSONObject(); /* Objeto JSON que almacenara el resultado de la petici�n*/
		InputStream is; /* String que almacenara el flujo de datos mientras se construye la cadena de resultados*/
		String result = ""; /* Almacen el resultado de la petici�n y lo devuelve al Activity que lo llam�*/
		try{
			sentUsr.put("usuario", usuario); /*Usamos la clave 'usuario' con la cadena pasada como par�metro desde el constructor*/
			sentUsr.put("password", contrasena); /* Usuamos la clave 'constrase�a' con la cadena pasada como par�metro desde el constructor*/
			StringEntity strSentUsr = new StringEntity(sentUsr.toString()); /* Esta variable del tipo StringEntity se requiere para formar la URL antes de realizar la petici�n POST*/
			HttpPost peticion = new HttpPost(serverURL); /*Se crea una petici�n tipo HttpPost pas�ndole como par�metro la URL del servidor*/ 
			HttpClient cliente = new DefaultHttpClient(); /* Se crea un cliente HTTP, que ejecutara la petici�n posteriormente */
			peticion.addHeader("content-type","application/json"); /*Se a�ade el tipo de contenido a enviar por la petici�n*/ 
			peticion.setEntity(strSentUsr); /*Con los datos de la URL ya establecidos, pasamos la entidad strSentUsr al cliente HTTP*/
			HttpResponse servResponse = cliente.execute(peticion); /*Asignamos la ejecuci�n del cliente junto con la entidad a un objeto del tipo HttpReponse, aqu� se almacenar� la respuesta dle servidor temporalmente*/
			HttpEntity htEntidad = servResponse.getEntity(); /*Obtenemos la respuesta del servidor como entidad*/
			is =  htEntidad.getContent(); /*Asignamos la entidad obtenida a un flujo de entrada que procesaremos para obtener uno de sus campos, el cual validara el acceso*/
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8); /*Almacenamos el flujo de bytes en un buffer */
			StringBuilder sb = new StringBuilder(); /*Creamos un nuevo objeto del tipo StringBuilder para iterar sobre la respuesta obtenida desde el servidor*/
			String line = null;
			while((line = reader.readLine()) != null){ /*Mientras el buffer no se encuentre vac�o*/
				sb.append(line + "\n"); /*A�adimos una nueva l�nea, esto creara una cadena delimitada por comas y campos el objeto JSON*/ 
			}
			is.close();    /*Cerramos el flujo de entrada*/
			result = sb.toString();  /*Convertirmos el resultado de la construcci�n de la cadena con el m�todo toString */
			JSONObject json = new JSONObject(result); /* Convertimos result a un objeto JSON*/
			String mesErr = json.getString("err"); /*Buscamos dentro del objeto JSON la clave "err" y la asignamos a una cadena*/
			if(mesErr.contains("Password o usuario invalidos")){ /*Si la cadena coincide con la indicada en el m�todo contains*/
				bandera = true; /*Asignamos el valor true a una bandera, misma que ser� parte del resultado al ejecutar la tarea as�ncrona*/
				return result;  /* Ademas devolvemos el resultado como cadena para poder operar sobre �l donde se requiera de forma posterior al login*/
			}
			else{
				bandera = false; /*En caso contrario negamos la existencia del mensaje de error*/
				return result; /*Y de igual forma devolvemos el resultado como cadena para operar sobre �l*/
			}

			
		}catch(Exception e){
			e.printStackTrace();
			return result;
		}
	}
	
	
	protected void onPostExcecute(String doInBg){

	}
	
} 
