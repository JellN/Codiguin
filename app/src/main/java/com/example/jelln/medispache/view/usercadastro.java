package com.example.jelln.medispache.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jelln.medispache.R;
import com.example.jelln.medispache.control.Conexao;
import com.example.jelln.medispache.model.Usuarios;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class usercadastro extends AppCompatActivity {


    EditText username, useradress, userpass, userCNPJ;
    Spinner cidade;

    Button userbotao, voltar;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Usuarios u = new Usuarios();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercadastro);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //inicializarcomponentes();
       // EventoClicks();
        inicializarfirebase();

    }

    private void inicializarfirebase() {


        FirebaseApp.initializeApp(usercadastro.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void EventoClicks() {
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baac();

            }
        });
        userbotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final   String name = username.getText().toString().trim();
                final String email = useradress.getText().toString().trim();
                final   String senha = userpass.getText().toString().trim();
                final String CNPJ = userCNPJ.getText().toString().trim();
                final String cidades = cidade.getSelectedItem().toString();


                if(!name.equals("")&&!email.equals("")&&!senha.equals("")&&!CNPJ.equals("")&&!cidades.equals("Selecione uma Cidade")){
                    criarUser(email, senha, name, CNPJ, cidades);

                }else{
                    alert("preencha todos os dados");
                }



            }


        });
    }

    private void baac() {

        Intent ss = new Intent(this, login.class);
        startActivity(ss);
        finish();
    }

    private void criarUser(final String email, final String senha, final String name, final String CNPJ, final  String cidades) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(usercadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    u.setEmail(email);
                    u.setName(name);
                    u.setSearch(name.toLowerCase());
                    u.setStatus("offline");
                    u.setSenha(senha);
                    u.setCnpj(CNPJ);
                    u.setCidade(cidades);
                    u.setId(auth.getUid());
                    databaseReference.child("UserEmpresa").child(auth.getUid()).setValue(u);


                    alert("Cadastro efetuado com sucesso");
                    Intent i = new Intent(usercadastro.this, Tipo_Empresa.class);
                    startActivity(i);
                    finish();
                }else{
                    alert("Erro ao cadastrar, verifique sua conexão.");

                }
            }
        });
    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

    }

    /*private void inicializarcomponentes() {
        userCNPJ = findViewById(R.id.userCNPJ);
        userbotao = findViewById(R.id.userbotao);
        username = findViewById(R.id.username);
        useradress = findViewById(R.id.useradress);
        userpass = findViewById(R.id.userpass);
        voltar = findViewById(R.id.voltar);
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN\n");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(userCNPJ, smf);
        userCNPJ.addTextChangedListener(maskTextWatcher);
        cidade = findViewById(R.id.cidade);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.cidades, android.R.layout.simple_spinner_item);
        cidade.setAdapter(arrayAdapter);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    @Override
    public void onBackPressed() {
        Intent ss = new Intent(this, login.class);
        ss.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ss);
        finish();
    }
}
