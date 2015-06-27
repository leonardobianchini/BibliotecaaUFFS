package com.example.bianchini.bibliotecauffs;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.database.sqlite.*;
import android.database.*;

import com.example.bianchini.bibliotecauffs.database.DataBase;
import com.example.bianchini.bibliotecauffs.dominio.RepositorioLivro;
import com.example.bianchini.bibliotecauffs.dominio.entidades.Livro;

import java.io.Serializable;

public class actMain extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener{


    private Button btAdd;
    private Button btInfo;
    private ListView lstLivros ;
    private DataBase dataBase ;
    private SQLiteDatabase conn ;
    private RepositorioLivro repositorioLivro;
    private ArrayAdapter<Livro> adpLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        btAdd = (Button)findViewById(R.id.btAdd);
        btInfo = (Button)findViewById(R.id.btInfo);
        lstLivros = (ListView)findViewById(R.id.lstLivros);

        btInfo.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        lstLivros.setOnItemClickListener(this);

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioLivro = new RepositorioLivro(conn);
            //adpLivros = repositorioLivro.buscaLivro(this);

            //lstLivros.setAdapter(adpLivros);


        } catch (Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o Banco " + ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_main, menu);
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

    @Override
    public void onClick(View v) {

        if (v == btAdd){
            Intent it = new Intent(this, actCadastro.class);
            startActivityForResult(it, 0);
        } else /*if (v == btInfo)*/ {
            Intent it = new Intent(this, actInfo.class);
            startActivity(it);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Log.wtf("actMain", "NAO E NULO DHSUAHDUSDHUDASHUASDHDSAUAHSDUSDHSDU");
            adpLivros = repositorioLivro.buscaLivro(this);
            lstLivros.setAdapter(adpLivros);
        }else{
            Log.wtf("actMain", "FODEU TUDO");

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Livro livro = adpLivros.getItem(position);

        Intent it = new Intent(this, actCadastro.class);

        it.putExtra("LIVRO", livro);

        startActivityForResult(it, 0);
    }
}
