package com.example.bianchini.bibliotecauffs;

/**
 *	Academicos: Joao Carlos Becker e Leonardo Bianchini
 *	Emails: joaoc.becker@hotmail.com e leonardobianchini7@gmail.com
 *
 *  Classe que contem a tela de cadastro do livro
 *	*/

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.example.bianchini.bibliotecauffs.app.MessageBox;
import com.example.bianchini.bibliotecauffs.database.DataBase;
import com.example.bianchini.bibliotecauffs.dominio.RepositorioLivro;
import com.example.bianchini.bibliotecauffs.dominio.entidades.Livro;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class Cadastrar extends ActionBarActivity{

    private Button btCancela;
    private Button btAdiciona;
    private EditText edNome;
    private EditText edAutor;
    private EditText edData;

    private DataBase dataBase ;
    private SQLiteDatabase conn;
    private RepositorioLivro repositorioLivro;
    private Livro livro;
    private Date data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro);

        edNome = (EditText) findViewById(R.id.edNome);
        edAutor = (EditText) findViewById(R.id.edAutor);
        edData = (EditText) findViewById(R.id.edData);
        btCancela = (Button) findViewById(R.id.btCancela);
        btAdiciona = (Button) findViewById(R.id.btAdiciona);

        //definidas a acoes dos botoes
        btCancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (livro.getId() == 0) {
                    salvar();
                    Intent result = new Intent();
                    result.putExtra("nomeLivro", livro.getNome());
                    setResult(RESULT_OK, result);
                } else {
                    Log.wtf("actCadastro", "LIVRO NAO E NULO");
                }
                finish();
            }
        });

        //acoes para exibicao da data
        exibeDataListener listener = new exibeDataListener();
        edData.setOnClickListener(listener);
        edData.setOnFocusChangeListener(listener);

        Bundle bundle;
        bundle = getIntent().getExtras();

        //verificada a passagem de parametros para criar ou preencher livro
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
            MessageBox.show(this, "Erro ao criar o Banco " + ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_cadastro, menu);
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

    //metodo que preenche os EditText com o livro que foi passado no parametro
    private void preencheDados(){
        edNome.setText(livro.getNome());
        edAutor.setText(livro.getAutor());
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(livro.getData());
        edData.setText(dt);
    }

    //metodo acionado ao clicar em adicionar
    private void salvar() {
        try {
            livro.setNome(edNome.getText().toString());
            livro.setAutor(edAutor.getText().toString());
            livro.setData(data);
            repositorioLivro.inserir(livro);
        } catch (Exception ex){
            MessageBox.show(this, "Erro ao inserir " + ex.getMessage());
        }
    }

    //metodo que chama a exibibicao da data
    private void exibeData(){
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dlg = new DatePickerDialog(this, new selecionaDataListener() , ano, mes, dia);
        dlg.show();
    }

    //implementacao do Dialog da data ao focar ou clicar no campo data
    private class exibeDataListener implements View.OnClickListener, View.OnFocusChangeListener {

        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                exibeData();
            }
        }
    }

    //define o formato de data e ja soma os dias para a devolucao do livro
    private class selecionaDataListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            data = calendar.getTime();
            DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String dt = format.format(data);
            edData.setText(dt);
            data.setDate(data.getDate() + 10);
        }
    }
}
