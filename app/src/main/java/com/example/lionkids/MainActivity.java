package com.example.lionkids;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private String usernameActivo;
    TextView texBienvenida;
    FirebaseFirestore mFirestore;
    public List<Alumno> alumnos = new ArrayList<>();
    private FirebaseStorage storage;
    private StorageReference reference;
    private String nombre ;
    private String direccion;
    private String edad;
    private String padre;
    private String URL;
   // StorageReference ref;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texBienvenida=findViewById(R.id.textVBienvenido);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            usernameActivo = bundle.getString("NameUser");
            texBienvenida.setText("Bienvenido, "+usernameActivo);
        }

        inicializarFirebase();
        //updateNavHeader();
       traerAlumno();
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //
                //  .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_agenda, R.id.nav_profile, R.id.nav_evaluacion)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    public boolean existAlumno(){
      boolean  band = false;
      for(Alumno alu : alumnos){
          if(alu.getPadre().equals(usernameActivo)){

              band = true;
              break;
          }else{
              Toast toast = Toast.makeText(getApplicationContext(),"ALUMNO NO EXISTE",Toast.LENGTH_LONG);
              toast.show();
          }
      }
      return band;
    }


    public void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        mFirestore = FirebaseFirestore.getInstance();
        CollectionReference users = mFirestore.collection("Escuelas_Madrid/DMnkyZbs8BrVUmPFQy2T/Alumnos");

    }

    public void traerAlumno(){

        mFirestore.collection("Escuelas_madrid").document("DMnkyZbs8BrVUmPFQy2T")
                .collection("Alumnos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Alumno a = document.toObject(Alumno.class);
                                alumnos.add(a);
                                if(a.getPadre().equals(usernameActivo)){
                                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                                    View headerView = navigationView.getHeaderView(0);
                                    TextView navAlumno = headerView.findViewById(R.id.nav_alumno);
                                    TextView navAlumnoEdad = headerView.findViewById(R.id.nav_alumno_edad);
                                    ImageView navUserPhot = headerView.findViewById(R.id.nav_user_photo);
                                    navAlumno.setText(a.getNombre());
                                    navAlumnoEdad.setText(a.getEdad());

                                    StorageReference miref = reference.child("Aitana/perfil_aitana.jpg");
                                    miref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Glide.with(MainActivity.this).load(uri).into(navUserPhot);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                                }else{
                                    Toast toast = Toast.makeText(getApplicationContext(),"OBTENCION DE DATOS FALLIDA",Toast.LENGTH_LONG);
                                    toast.show();
                                }

                            }
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(),"OBTENCION DE DATOS FALLIDA",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}