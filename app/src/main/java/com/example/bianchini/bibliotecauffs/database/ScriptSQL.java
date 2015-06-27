package com.example.bianchini.bibliotecauffs.database;

/**
 * Created by Bianchini on 12/06/2015.
 */

public class ScriptSQL {

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
