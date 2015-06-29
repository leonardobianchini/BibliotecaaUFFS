package com.example.bianchini.bibliotecauffs.database;

/**
 *	Academicos: Joao Carlos Becker e Leonardo Bianchini
 *	Emails: joaoc.becker@hotmail.com e leonardobianchini7@gmail.com
 *
 *  Classe que define a tabela que sera usada no array de livros a serem guardados no banco
 *	*/

public class ScriptSQL {

    //metodo que cria a tabela para ser usada no banco
    public static String getCriateLivro(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS LIVRO( ");
        sqlBuilder.append(" _id         INTEGER      NOT NULL ");
        sqlBuilder.append("             PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(" NOME        VARCHAR(40), ");
        sqlBuilder.append(" AUTOR       VARCHAR(30), ");
        sqlBuilder.append(" DATA        DATE ");
        sqlBuilder.append("); ");

        return sqlBuilder.toString();
    }

}
