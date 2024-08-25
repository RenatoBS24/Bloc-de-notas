package com.Bloc.Interfaz;

import com.Bloc.Clases.Archivo;
import com.Bloc.Clases.Editar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.util.LinkedList;
import java.util.Stack;

public class Bloc extends JFrame {
    private JEditorPane editorPane;
    private JPanel panel;
    private JMenuBar menu;
    private Archivo ar = new Archivo();
    private Editar ed = new Editar();
    public File file = null;
    private Stack<String> pila = new Stack<>();
    public Bloc(){
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bloc de notas");
        setVisible(true);
        pack();
        setSize(1360,900);
        setLocationRelativeTo(null);
        //-----------------------------------------------------
        // Configuramos el menú
        JMenu archivo = new JMenu("Archivo");
        JMenu editar = new JMenu("Editar");
        JMenu ver = new JMenu("Ver");
        // Configuramos elp popupmenu
        JPopupMenu menuLateral = new JPopupMenu();
        // Configuracion de menu items
        JMenuItem abrir = new JMenuItem("Abrir");
        JMenuItem guardar = new JMenuItem("Guardar");
        JMenuItem guardarComo = new JMenuItem("Guardar Como");
        JMenuItem imprimir = new JMenuItem("Imprimir");
        JMenuItem salir = new JMenuItem("Salir");
        JMenuItem Deshacer = new JMenuItem("Deshacer");
        JMenuItem copiar = new JMenuItem("Copiar");
        JMenuItem cortar = new JMenuItem("Cortar");
        JMenuItem pegar = new JMenuItem("Pegar");
        JMenuItem Deshacer2 = new JMenuItem("Deshacer");
        JMenuItem copiar2 = new JMenuItem("Copiar");
        JMenuItem cortar2 = new JMenuItem("Cortar");
        JMenuItem pegar2 = new JMenuItem("Pegar");
        JMenuItem  zoom = new JMenuItem("Zoom");
        archivo.add(abrir);
        archivo.add(guardar);
        archivo.add(guardarComo);
        archivo.add(imprimir);
        archivo.add(salir);
        //-----------------------------------------------------------------
        editar.add(Deshacer);
        editar.add(copiar);
        editar.add(cortar);
        editar.add(pegar);
        menuLateral.add(Deshacer2);
        menuLateral.add(copiar2);
        menuLateral.add(cortar2);
        menuLateral.add(pegar2);
        //-----------------------------------
        menu.add(archivo);
        menu.add(editar);
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               LinkedList<String> texto =  ar.abrirArchivo();
               file = new File(texto.get(0));
               for(int i = 1;i<texto.size();i++){
                   String linea = editorPane.getText();
                   if (linea.equals("")) {
                       editorPane.setText(linea+texto.get(i));
                   }else{
                       editorPane.setText(linea+"\n"+texto.get(i));
                   }
               }
            }
        });
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                   file = ar.guardarComo(editorPane.getText());
                }else{
                    ar.guardar(editorPane.getText(),file);
                }
            }
        });
        guardarComo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               file = ar.guardarComo(editorPane.getText());
            }
        });
        imprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean confirm = editorPane.print();
                    if(confirm){
                        JOptionPane.showMessageDialog(null,"Impresion finalizada","Impresion",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null,"Se cancelo la impresion","Impresion",JOptionPane.WARNING_MESSAGE);
                    }
                } catch (PrinterException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file !=null) {
                    System.exit(0);
                }else{
                    ar.guardarComo(editorPane.getText());
                    System.exit(0);
                }

            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (file !=null) {
                    System.exit(0);
                }else{
                    ar.guardarComo(editorPane.getText());

                }
            }
        });
        copiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection = editorPane.getSelectedText();
                StringSelection copy = new StringSelection(selection);
                ed.copiar(copy);
            }
        });
        copiar2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection = editorPane.getSelectedText();
                StringSelection copy = new StringSelection(selection);
                ed.copiar(copy);
            }
        });
        pegar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = ed.pegar();
                editorPane.setText(editorPane.getText()+texto);
            }
        });
        pegar2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = ed.pegar();
                editorPane.setText(editorPane.getText()+texto);
            }
        });
        cortar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = editorPane.getSelectedText();
                StringSelection cut = new StringSelection(text);
                ed.copiar(cut);
                editorPane.replaceSelection("");
            }
        });
        cortar2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = editorPane.getSelectedText();
                StringSelection cut = new StringSelection(text);
                ed.copiar(cut);
                editorPane.replaceSelection("");
            }
        });
        Deshacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pila.isEmpty()) {
                    pila.pop();
                    if (!pila.isEmpty()) {
                        editorPane.setText(pila.pop());
                    }
                }
            }
        });
        Deshacer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pila.isEmpty()) {
                    pila.pop();
                    if (!pila.isEmpty()) {
                        editorPane.setText(pila.pop());
                    }
                }
            }
        });

        editorPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    String texto = editorPane.getSelectedText();
                    if(texto == null){
                        copiar2.setVisible(false);
                        cortar2.setVisible(false);
                    }else{
                        copiar2.setVisible(true);
                        cortar2.setVisible(true);
                    }
                    menuLateral.show(editorPane,e.getX(),e.getY());
                }
            }
        });
        editorPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (pila.size()<20) {
                    if(!editorPane.getText().equals("")){
                        pila.add(editorPane.getText());
                    }
                }else{
                    pila.clear();
                    if(!editorPane.getText().equals("")){
                        pila.add(editorPane.getText());
                    }
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (pila.size()<20) {
                    if(!editorPane.getText().equals("")){
                        pila.add(editorPane.getText());
                    }
                }else{
                    pila.clear();
                    if(!editorPane.getText().equals("")){
                        pila.add(editorPane.getText());
                    }
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

}
