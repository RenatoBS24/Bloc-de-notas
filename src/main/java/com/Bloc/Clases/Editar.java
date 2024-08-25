package com.Bloc.Clases;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class Editar {
    public void copiar(StringSelection texto){
        Copiar(texto);
    }
    private void Copiar(StringSelection texto){
        Clipboard c1 = Toolkit.getDefaultToolkit().getSystemClipboard();
        c1.setContents(texto,null);
    }
    public String pegar(){
       return Pegar();
    }
    private String Pegar(){
        String texto = "";
        Clipboard c1 = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable =c1.getContents(this);
        if(transferable !=null){
            try {
                texto = (String)transferable.getTransferData(DataFlavor.stringFlavor);
                return texto;
            } catch (UnsupportedFlavorException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return texto;
    }
}
