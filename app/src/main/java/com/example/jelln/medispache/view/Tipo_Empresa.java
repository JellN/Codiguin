package com.example.jelln.medispache.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jelln.medispache.R;
import com.example.jelln.medispache.control.Conexao;
import com.example.jelln.medispache.model.Usuarios;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Tipo_Empresa extends AppCompatActivity {
ImageView restaurante, agua, lanchonete, bebidas;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    DatabaseReference   databaseReference2;
    Usuarios u = new Usuarios();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo__empresa);
        user = FirebaseAuth.getInstance().getCurrentUser();
        inicializarfirebase();
        restaurante = findViewById(R.id.restaurante);
        agua = findViewById(R.id.agua);
        lanchonete = findViewById(R.id.lanchonete);
        bebidas = findViewById(R.id.bebidas);
        databaseReference2 = FirebaseDatabase.getInstance().getReference("UserEmpresa").child(user.getUid());
        //     eventoclick();
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    u = dataSnapshot.getValue(Usuarios.class);



                }            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        restaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Empresas").child("restaurante").child(user.getUid()).setValue(u);
                u.setTipo("restaurante");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tipo", u.getTipo());
                databaseReference2.updateChildren(hashMap);
                alert("Seleção Restaurante");
            }
        });
        agua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Empresas").child("agua").child(user.getUid()).setValue(u);
                u.setTipo("agua");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tipo", u.getTipo());
                databaseReference2.updateChildren(hashMap);
                alert("Seleção Água e Gás");



            }
        });
        lanchonete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Empresas").child("lanchonete").child(user.getUid()).setValue(u);
                u.setTipo("lanchonete");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tipo", u.getTipo());
                databaseReference2.updateChildren(hashMap);
                alert("Seleção Lanchonete");

            }
        });
        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Empresas").child("bebidas").child(user.getUid()).setValue(u);
                u.setTipo("bebidas");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("tipo", u.getTipo());
                databaseReference2.updateChildren(hashMap);
                alert("Seleção Bebidas");



            }
        });

    }

    private void inicializarfirebase() {


        FirebaseApp.initializeApp(Tipo_Empresa.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public  void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        Intent ss = new Intent(this, Splash.class);
        ss.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ss);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Conexao.logOut();
        Intent ss = new Intent(this, login.class);
        ss.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ss);
        finish();
    }
}
