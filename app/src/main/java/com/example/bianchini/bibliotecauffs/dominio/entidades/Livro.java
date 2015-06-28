package com.example.bianchini.bibliotecauffs.dominio.entidades;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Bianchini on 13/06/2015.
 */
public class Livro implements Serializable {

    private long id;
    private String nome;
    private String autor;
    private Date data;

    public Livro(){
        id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    private String defineDatata(){
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String defineData = formato.format(data);
        return defineData;
    }

    @Override
    public String toString(){
        return "Nome: "+nome + "\nAutor: "+ autor +  "\nDevolucao: " + defineDatata();
    }
}
