package com.example.bianchini.bibliotecauffs.dominio.entidades;

/**
 *	Academicos: Joao Carlos Becker e Leonardo Bianchini
 *	Emails: joaoc.becker@hotmail.com e leonardobianchini7@gmail.com
 *
 *  Classe que contem o Livro em si, com seus getters e setters
 *	*/

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Livro implements Serializable {

    private long id;
    private String nome;
    private String autor;
    private Date data;

    //define o id=0 para saber se o livro eh nulo
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

    //metodo que formata a data para dd/mm/yyyy para ser retornado
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
