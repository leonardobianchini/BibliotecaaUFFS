package com.example.bianchini.bibliotecauffs.app;

/**
 *	Academicos: Joao Carlos Becker e Leonardo Bianchini
 *	Emails: joaoc.becker@hotmail.com e leonardobianchini7@gmail.com
 *
 *  Classe que contem metodo para diminuir a replicacao de codigo
 *	*/

import android.app.AlertDialog;
import android.content.Context;

public class MessageBox {

    //metodo criado para evitar replicacao de codigo
    public static void show(Context ctx, String msg){
        AlertDialog.Builder dlg = new AlertDialog.Builder(ctx);
        dlg.setMessage(msg);
        dlg.setNeutralButton("OK",null);
        dlg.show();
    }
}
