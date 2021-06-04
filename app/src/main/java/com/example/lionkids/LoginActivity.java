package com.example.lionkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView cuentaNueva;
    EditText editTextDni, editTextPassword;
    Button btnLogin;
    //private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;
    private String dni;
    private String pass;
    private String nameUser;
    FirebaseFirestore mFirestore;
    public List<User> listUser = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Typeface miFuente = Typeface.createFromAsset(getAssets(),"kidszone.ttf");
        TextView titulo = (TextView) findViewById(R.id.header);
        titulo.setTypeface(miFuente);

        editTextDni = (EditText) findViewById(R.id.editTextDni);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.cirLoginButton);
        btnLogin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        cuentaNueva = (TextView) findViewById(R.id.tRegistro);
        cuentaNueva.setOnClickListener(this);
       // mAuth = FirebaseAuth.getInstance();
       // mFirestore = FirebaseFirestore.getInstance();
        inicializarFirebase();
        obtenerUsersNew();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tRegistro:
                Intent intent = new Intent(this,RegistroActivity.class);
                startActivity(intent);
                break;
            case R.id.cirLoginButton:
                loginUser();
                break;
        }
    }


    public void loginUser(){
        dni = editTextDni.getText().toString();
        pass = editTextPassword.getText().toString();
        if(!dni.isEmpty() && !pass.isEmpty()){

            if(existUser(dni,pass)){
                if(dni.equalsIgnoreCase("03587418E")) {
                    Toast.makeText(LoginActivity.this, "Usuario logueado correctamente!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("NameUser", nameUser);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Usuario logueado correctamente!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("NameUser", nameUser);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }

            }else{
                Toast.makeText(LoginActivity.this, "Usted no esta registrado!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(LoginActivity.this,"Debe ingresar email y contraseña!",Toast.LENGTH_LONG).show();
        }
    }


    private boolean existUser(String dni, String pass) {
        boolean band=false;
        for (User user : listUser) {
            if(user.getDni().equals(dni)){
                try{
                    String passuser=CodificadorAES.desencriptar(user.getPassword());
                    nameUser=user.getNombre();
                    if(passuser.equals(pass)){
                        band=true;
                        break;
                    }
                    else Toast.makeText(this, "Su DNI esta registrado pero no coincide con su contraseña, dirigase a Recuperar Contraseña.", Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(this, "Error Codificacion: "+e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }
        return band;
    }

    private void obtenerUsersNew(){
        //listUser.clear();
        mFirestore.collection("Usuarios_Madrid")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User u = document.toObject(User.class);
                                listUser.add(u);

                            }
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(),"OBTENCION DE DATOS FALLIDA",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });



    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        mFirestore = FirebaseFirestore.getInstance();
        CollectionReference users = mFirestore.collection("Usuarios_Madrid");
    }


}