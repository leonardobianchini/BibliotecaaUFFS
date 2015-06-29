package com.example.bianchini.bibliotecauffs;

/**
 *	Academicos: Joao Carlos Becker e Leonardo Bianchini
 *	Emails: joaoc.becker@hotmail.com e leonardobianchini7@gmail.com
 *
 *  Classe que contem a main do aplicativo, tela inicial
 *	*/

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.database.sqlite.*;

import com.example.bianchini.bibliotecauffs.app.MessageBox;
import com.example.bianchini.bibliotecauffs.database.DataBase;
import com.example.bianchini.bibliotecauffs.dominio.RepositorioLivro;
import com.example.bianchini.bibliotecauffs.dominio.entidades.Livro;

public class Main extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener{


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

        //metodo que cria o banco, caso nao de certo mostra o erro
        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            repositorioLivro = new RepositorioLivro(conn);
            adpLivros = repositorioLivro.buscaLivro(this);
            lstLivros.setAdapter(adpLivros);
        } catch (Exception ex){
            MessageBox.show(this, "Erro ao criar o Banco " + ex.getMessage());
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

    //chama a classe de acordo com o botao que foi selecionado
    @Override
    public void onClick(View v) {

        if (v == btAdd){
            Intent it = new Intent(this, Cadastrar.class);
            startActivityForResult(it, 0);
        } else {
            Intent it = new Intent(this, Informacoes.class);
            startActivity(it);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log para saber se esta retornando nulo ou nao, atualizando listview
        if (data != null) {
            Log.wtf("actMain", "NAO EH NULO");
        }else{
            Log.wtf("actMain", "RETORNOU NULO");
        }
        adpLivros = repositorioLivro.buscaLivro(this);
        lstLivros.setAdapter(adpLivros);
    }

    //chama a classe Mostra passando o livro que foi selecionado no listView
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Livro livro = adpLivros.getItem(position);
        Intent it = new Intent(this, MostraLivro.class);
        it.putExtra("LIVRO", livro);
        startActivityForResult(it, 1);
    }
}
