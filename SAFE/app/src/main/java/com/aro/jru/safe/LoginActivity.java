package com.aro.jru.safe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/******************************************************************
 * Grado en Ingeniería Informática
 * Asignatura: Aplicaciones sobre redes de ordenadores (ARO)
 * Proyecto SAFE
 *****************************************************************/
public class LoginActivity extends Activity {

    /************************************************************
     * Variables
     ************************************************************/
    private EditText nombreET;
    private EditText passET;
    private Button botonLogin;

    // Definimos un Toast el cual nos servirá para mostrar un mensaje "flotante" al
    // usuario indicando si la información introducida en el proceso de login es
    // o no correcta, si la cuenta no está activada o si se ha produucido algún error.
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Identificamos los elementos descritos en el "activity_login.xml"
        nombreET = findViewById(R.id.nombre);
        passET = findViewById(R.id.pass);
        botonLogin = findViewById(R.id.loginbutton);

        // Establecemos desde el inicio un listener para comprobar si el botón de login
        // ha sido pulsado. En ese caso, comprobaremos los valores de nombre y contraseña
        // introducidos por el usuario
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarValoresIntroducidos();
            }
        });


    }

    private void comprobarValoresIntroducidos() {
        // Buscamos que siempre se introduzca algún valor en los dos campos (nombre y contraseña).
        // En el caso contrario, debe de aparecer un mensaje informativo diciéndonos que faltan
        // por introducir datos.

        // En primer lugar, decimos que no existe ningún tipo de error de ese tipo:
        nombreET.setError(null);
        passET.setError(null);

        // A continuación, guardamos en variables la información obtenida de los campos
        // de nombre y contraseña. Comprobamos que hay algo de información en ellos:
        final String nombre = nombreET.getText().toString();
        final String pass = passET.getText().toString();

        if (TextUtils.isEmpty(nombre)){
            nombreET.setError("Es necesario introducir el nombre de usuario");
            nombreET.requestFocus();    // Esta linea de comando sirve para que aparezca un simbolo de alerta
        }
        else if (TextUtils.isEmpty(pass)){
            passET.setError("Es necesario introducir una contraseña");
            passET.requestFocus();
        }
        else{
            // Si se ha introducido tanto un nombre como una contraseña, debemos verificar si
            // existe dicho usuario en la base de datos.

            // Encriptamos la contraseña escrita en el EditText, mediante el algoritmo MD5
            final String passEncriptada = encriptacionMD5(pass);

            // Ahora debemos de buscar al usuario en la base de datos con la informacion introducida

        }


    }

    /***********************************************************************************************
     * Función encriptacionMD5(String pass)
     * Funcion que sirve para encriptar una contraseña mediante el algoritmo md5
     * @return (String encriptado)
     ***********************************************************************************************/
    private String encriptacionMD5(String pass) {
        try{
            // Creamos un Hash MD5 con la contraseña introducida por el usuario
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(pass.getBytes());
            byte messageDigest[] = digest.digest();

            // Establecemos el String pasado por algumento en hexadecimal
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();
        }
         catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
