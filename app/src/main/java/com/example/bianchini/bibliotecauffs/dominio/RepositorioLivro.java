package com.example.bianchini.bibliotecauffs.dominio;

/**
 *	Academicos: Joao Carlos Becker e Leonardo Bianchini
 *	Emails: joaoc.becker@hotmail.com e leonardobianchini7@gmail.com
 *
 *  Classe que contem o repositorio do livro, que implementa os metodos de controle do banco
 *	*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.widget.*;

import com.example.bianchini.bibliotecauffs.dominio.entidades.Livro;

import java.util.Date;


public class RepositorioLivro {

    private SQLiteDatabase conn;
    public RepositorioLivro (SQLiteDatabase conn){
        this.conn = conn;
    }

    public void preencheValues(Livro livro, ContentValues values){
        values.put("NOME", livro.getNome());
        values.put("AUTOR", livro.getAutor());
        values.put("DATA", livro.getData().getTime());
    }

    public void inserir(Livro livro){
        ContentValues values = new ContentValues();
        preencheValues(livro, values);
        conn.insertOrThrow("LIVRO", null, values);
    }

    public void alterar(Livro livro){
        ContentValues values = new ContentValues();
        preencheValues(livro, values);
        long id = livro.getId();
        conn.update("LIVRO", values, "_id = ?", new String[]{String.valueOf(id)});
    }

    public void excluir (long id){
        conn.delete("LIVRO", "_id = ?", new String[]{ String.valueOf(id) });
    }


    public ArrayAdapter<Livro> buscaLivro(Context context){
        ArrayAdapter<Livro> adpLivros = new ArrayAdapter<Livro>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("LIVRO", null, null, null, null, null, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Livro livro = new Livro();
                livro.setId(cursor.getLong(0));
                livro.setNome(cursor.getString(1));
                livro.setAutor(cursor.getString(2));
                livro.setData(new Date(cursor.getLong(3)));

                adpLivros.add(livro);
            } while (cursor.moveToNext());
        }
        return adpLivros;
    }

}
