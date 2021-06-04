package com.example.lionkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView banner;
    private Button btnRegistrarse;
    private EditText editTextFullName, editTextDNI, editTextEmail, editTextMovil, editTextPassword, editTextUser;
   // private FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registro);
        getSupportActionBar().hide();

        Typeface miFuente = Typeface.createFromAsset(getAssets(),"kidszone.ttf");
        TextView titulo = (TextView) findViewById(R.id.rTitulo);
        titulo.setTypeface(miFuente);

       // mAuth = FirebaseAuth.getInstance();
       // mFirestore = FirebaseFirestore.getInstance();

        banner = (TextView) findViewById(R.id.rTitulo);
        banner.setOnClickListener(this);
        btnRegistrarse = (Button) findViewById(R.id.cirLoginButton);
        btnRegistrarse.setOnClickListener(this);
        editTextFullName = (EditText) findViewById(R.id.editTextName);
        editTextDNI = (EditText) findViewById(R.id.editTextDNI);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextMovil = (EditText) findViewById(R.id.editTextMobile);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUser = (EditText) findViewById(R.id.editTextTypeUser);
        inicializarFirebase();
       progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rTitulo:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.cirLoginButton:
                Registro();
                break;
        }
    }

    public void Registro(){
        if(validacion()){
           RegisterNew();
        }
        else  {
            Toast.makeText(RegistroActivity.this,"No se pudo registrar verifique sus datos ingresados porfavor!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validacion() {
        boolean resul = true;
        String nombre = editTextFullName.getText().toString().trim();
        String dni = editTextDNI.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String movil = editTextMovil.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String typeUser = editTextUser.getText().toString().trim();

        if (nombre.equals("")) {
            editTextFullName.setError("Requerido");
            editTextFullName.requestFocus();
            resul = false;
        } else if (dni.equals("")) {
            editTextDNI.setError("Requerido");
            editTextDNI.requestFocus();
            resul = false;
        } else if (email.equals("")) {
            editTextEmail.setError("Requerido");
            editTextEmail.requestFocus();
            resul = false;
        } else if (movil.equals("")) {
            editTextMovil.setError("Requerido");
            editTextMovil.requestFocus();
            resul = false;
        } else if (password.equals("")) {
            editTextPassword.setError("Requerido");
            editTextPassword.requestFocus();
            resul = false;
        } else if (typeUser.equals("")) {
            editTextUser.setError("Requerido");
            editTextUser.requestFocus();
            resul = false;
        }
        return resul;
    }


    private void RegisterNew(){
        String nombre = editTextFullName.getText().toString();
        String dni = editTextDNI.getText().toString();
        String password = editTextPassword.getText().toString();
        String email =editTextEmail.getText().toString();
        String movil = editTextMovil.getText().toString();
        String tipo = editTextUser.getText().toString();

        User u = new User();
        u.setNombre(nombre);
        u.setDni(dni);
        u.setEmail(email);
        u.setMovil(movil);
        u.setTipo(tipo);
        try{
            u.setPassword(CodificadorAES.encriptar(password));
            mFirestore.collection("Usuarios_Madrid").add(u);
            //databaseReference.child("Users").child(u.getUid()).setValue(u);
            Toast.makeText(this, "Se registro correctamente", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegistroActivity.this,LoginActivity.class));
            finish();
        }
        catch (Exception e){
            Toast.makeText(this, "Error: "+e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        mFirestore = FirebaseFirestore.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        CollectionReference users = mFirestore.collection("Usuarios_Madrid");
    }


}