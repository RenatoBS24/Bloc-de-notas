package com.Bloc.Clases;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.LinkedList;

public class Archivo {
    public LinkedList<String> abrirArchivo(){
        LinkedList<String> lineas = new LinkedList<String>();
        File file = obtenerarchivo();
        if(file == null){
            JOptionPane.showMessageDialog(null,"No ha seleccionado ningun archivo");
        }else {
            lineas.add(file.getAbsolutePath());
            try {
                FileReader lectura = new FileReader(file);
                BufferedReader bf = new BufferedReader(lectura);
                String line = bf.readLine();
                while (line != null) {
                    lineas.add(line);
                    line = bf.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return lineas;
    }
    private  File obtenerarchivo(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter extension = new FileNameExtensionFilter("Archivos de tipo (.txt)","txt");
        fileChooser.setFileFilter(extension);
        int result = fileChooser.showOpenDialog(null);
        if(result ==JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }
    public void guardar(String texto,File file){
        Guardar(texto,file);
    }
    private void Guardar(String texto,File file){
        if(file !=null){
            try {
                FileWriter fw = new FileWriter(file);
                System.out.println(texto);
                fw.write(texto);
                System.out.println(texto);
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public File guardarComo(String texto){
       return GuardarComo(texto);
    }
    private File GuardarComo(String texto){
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del archivo");
            File file = new File(fileChooser.getSelectedFile(),nombre+".txt");
            try {
                if(file.createNewFile()) {
                    try (FileWriter fw = new FileWriter(file)) {
                        fw.write(texto);
                        JOptionPane.showMessageDialog(null,"Se guardo correctamente el archivo");
                    }
                }
                return file;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
