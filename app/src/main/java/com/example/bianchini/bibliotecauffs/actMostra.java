package com.example.bianchini.bibliotecauffs;

import android.app.AlertDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bianchini.bibliotecauffs.database.DataBase;
import com.example.bianchini.bibliotecauffs.dominio.RepositorioLivro;
import com.example.bianchini.bibliotecauffs.dominio.entidades.Livro;

import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;


public class actMostra extends ActionBarActivity {

    private Button btVoltar;
    private Button btRenovar;
    private Button btDevolver;
    private EditText edtName;
    private EditText edtAutorr;
    private EditText edtDate;

    private DataBase dataBase ;
    private SQLiteDatabase conn ;
    private RepositorioLivro repositorioLivro;
    private Livro livro;
    private Date data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mostra);

        edtName = (EditText) findViewById(R.id.edtName);
        edtAutorr = (EditText) findViewById(R.id.edtAutorr);
        edtDate = (EditText) findViewById(R.id.edtDate);
        btVoltar = (Button) findViewById(R.id.btVoltar);
        btRenovar = (Button) findViewById(R.id.btRenovar);
        btDevolver = (Button) findViewById(R.id.btDevolver);

        btRenovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterar();
                finish();
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btDevolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                excluir();
                finish();
            }
        });

        Bundle bundle;
        bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("LIVRO"))) {
            livro = (Livro)bundle.getSerializable("LIVRO");
            preencheDados();
        } else {
            livro = new Livro();
        }

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioLivro = new RepositorioLivro(conn);

        } catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o Banco " + ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    private void preencheDados(){
        edtName.setText(livro.getNome());
        edtAutorr.setText(livro.getAutor());
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(livro.getData());
        edtDate.setText(dt);
    }

    private void alterar() {
        try {
            Calendar calendar = Calendar.getInstance();
            livro.setNome(livro.getNome());
            livro.setAutor(livro.getAutor());
            data = calendar.getTime();
            data.setDate(data.getDate()+10);
            livro.setData(data);
            repositorioLivro.alterar(livro);

        } catch (Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao inserir " + ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    private void excluir (){
        try {
            repositorioLivro.excluir(livro.getId());
        } catch (Exception e){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao excluir os dados " + e.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_mostra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
