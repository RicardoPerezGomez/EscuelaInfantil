package com.example.lionkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpinnerActivity extends AppCompatActivity {

    Spinner mSpinner;
    FirebaseFirestore mFirestore;
    TextView mText;
    String escuelaSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        Typeface miFuente = Typeface.createFromAsset(getAssets(),"kidszone.ttf");
        TextView titulo = (TextView) findViewById(R.id.titulo_bienvenida);
        TextView subtitulo = (TextView) findViewById(R.id.subtitulo_bienvenida);
        titulo.setTypeface(miFuente);
        subtitulo.setTypeface(miFuente);

        mSpinner = (Spinner) findViewById(R.id.nombre_escuela);
        mFirestore = FirebaseFirestore.getInstance();
        mText = (TextView) findViewById(R.id.direccion_escuela);
        loadEscuelas();

    }

    public void loadEscuelas(){
        List<Escuela> escuelas =  new ArrayList<>();
        Query nombreEscuelas = mFirestore.collection("Escuelas_madrid");
        nombreEscuelas.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot ds : queryDocumentSnapshots) {
                    String nombre = ds.getString("Nombre");
                    String direccion = ds.getString("Direccion");
                    String email = ds.getString("Email");
                    String movil = ds.getString("Movil");
                    escuelas.add(new Escuela(nombre,direccion,email,movil));
                }
                ArrayAdapter<Escuela> arrayAdapter = new ArrayAdapter<>(SpinnerActivity.this,android.R.layout.simple_dropdown_item_1line, escuelas);
                mSpinner.setAdapter(arrayAdapter);
                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        escuelaSelecionada = parent.getItemAtPosition(position).toString();

                        String direccion = escuelas.get(position).getDireccion();
                        mText.setText("Direccion : " + direccion);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(),"OBTENCION DE DATOS FALLIDA",Toast.LENGTH_LONG);
                toast.show();
            }
        });



    }
}