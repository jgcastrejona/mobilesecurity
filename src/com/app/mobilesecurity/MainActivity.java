/*MainActivity.java: esta clase es el punto de partida de la aplicación cliente de MobileSecurity
 * Su vista, layout o interfaz gráfica corresponde a la pantalla de login del cliente. 
 * */
package com.app.mobilesecurity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;
/*MainActivity extiende de la clase Activity propia del SDK de Android, encargada de el manejo de 
 * aplicaciones en entornos Android*/
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	   //set content view AFTER ABOVE sequence (to avoid crash)

		setContentView(R.layout.activity_main);
		
		final EditText logUsr = (EditText)findViewById(R.id.txtUsr);
		logUsr.setOnClickListener(new EditText.OnClickListener(){
			@Override
			public void onClick(View v) {
				logUsr.setText("");
			}	
		});
		
		final EditText logPass = (EditText)findViewById(R.id.txtPass);
		logPass.setOnClickListener(new EditText.OnClickListener(){
			public void onClick(View v){
				logPass.setText("");
			}
		});
		
		final Button logBtn = (Button)findViewById(R.id.btnLog);
		logBtn.setOnClickListener(new Button.OnClickListener(){
			 public void onClick(View v){
				Editable ustext = logUsr.getText();
				Editable pstext = logPass.getText();
				String temp = ustext.toString();
				String tempp = pstext.toString();
				if(temp.matches("") || tempp.matches("")){
					Toast.makeText(getApplicationContext(), "Por favor completa los campos", Toast.LENGTH_SHORT).show();
				}
				else{
				String Usuario = ustext.toString();
				String Contras = pstext.toString();
				//Toast.makeText(getApplicationContext(), "Usuario: "+ Usuario + " Pass: "+ Contras, Toast.LENGTH_LONG).show();

				AsyncTaskParser task = new AsyncTaskParser(Usuario, Contras);
				task.execute();
				try {
					String dum = task.get().toString();
					if(task.bandera == false){
						//Toast.makeText(getApplicationContext(), dum, Toast.LENGTH_LONG).show();
						Intent intent = new Intent(MainActivity.this, StartActivity.class);
						intent.putExtra("enviadoPor", dum.toString());
						startActivity(intent);
						finish();
					}
					else{
						Toast.makeText(getApplicationContext(), "Error de credenciales, verifica los datos", Toast.LENGTH_SHORT).show();
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				} /* Alcance del if-else*/
				}
		});
	}		
}


	
