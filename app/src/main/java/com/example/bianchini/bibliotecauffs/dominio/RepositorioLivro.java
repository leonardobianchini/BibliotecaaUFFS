package com.example.bianchini.bibliotecauffs.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.widget.*;

import com.example.bianchini.bibliotecauffs.dominio.entidades.Livro;

import java.util.Date;

/**
 * Created by Bianchini on 12/06/2015.
 */
public class RepositorioLivro {

    private SQLiteDatabase conn;
    public RepositorioLivro (SQLiteDatabase conn){
        this.conn = conn;
    }

    public void inserir(Livro livro){
        ContentValues values = new ContentValues();
        values.put("NOME", livro.getNome());
        values.put("AUTOR", livro.getAutor());
        values.put("DATA", livro.getData().getTime());
        conn.insertOrThrow("LIVRO", null, values);
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
