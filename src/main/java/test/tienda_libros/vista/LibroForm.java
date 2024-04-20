package test.tienda_libros.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import test.tienda_libros.modelo.Libro;
import test.tienda_libros.servicio.LibroServicio;

@Component
public class LibroForm extends JFrame{
    LibroServicio libroServicio;
    private JTextField idTexto;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField tituloTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField stockTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private DefaultTableModel modeloTablaLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
        iniciarForma();

        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
            }
        });

        agregarButton.addActionListener(e -> agregarLibro());
        modificarButton.addActionListener(e -> modificarLibro());
        eliminarButton.addActionListener(e -> eliminarLibro());

    }

    private void iniciarForma(){
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900,700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()/ 2);
        int y = (screenSize.height = getHeight() / 2);
        setLocation(x, y);
    }

    private void agregarLibro(){
        if(tituloTexto.getText().isEmpty())
        {
            mostrarMensaje("Proporcione el nombre del libro");
            tituloTexto.requestFocusInWindow();
            return;
        }
        var titulo = tituloTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var stock = Integer.parseInt(stockTexto.getText());

        var libro = new Libro(null, titulo, autor, precio, stock);
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Libro agregado");
        limpiarFormulario();
        listarLibros();
    }

    private void cargarLibroSeleccionado(){
        var row = tablaLibros.getSelectedRow();
        if(row != -1){
            String idLibro = tablaLibros.getModel().getValueAt(row, 0).toString();
            idTexto.setText(idLibro);
            String tituloLibro = tablaLibros.getModel().getValueAt(row, 1).toString();
            tituloTexto.setText(tituloLibro);
            String autorLibro = tablaLibros.getModel().getValueAt(row, 2).toString();
            autorTexto.setText(autorLibro);
            String precioLibro = tablaLibros.getModel().getValueAt(row, 3).toString();
            precioTexto.setText(precioLibro);
            String stockLibro = tablaLibros.getModel().getValueAt(row, 4).toString();
            stockTexto.setText(stockLibro);
        }
    }

    private void modificarLibro(){
        if(this.idTexto.getText().isEmpty()){
            mostrarMensaje("Debe seleccionar un registro");
        }else{
            if(tituloTexto.getText().isEmpty()){
                mostrarMensaje("Proporcione el nombre del libro");
                tituloTexto.requestFocusInWindow();
                return;
            }
            int idLibro = Integer.parseInt(idTexto.getText());
            var tituloLibro = tituloTexto.getText();
            var autorLibro = autorTexto.getText();
            var precioLibro = Double.parseDouble(precioTexto.getText());
            var stockLibro = Integer.parseInt(stockTexto.getText());
            var libro = new Libro(idLibro,tituloLibro,autorLibro,precioLibro, stockLibro);
            libroServicio.guardarLibro(libro);
            mostrarMensaje("Libro modificado");
            limpiarFormulario();
            listarLibros();
        }
    }

    void eliminarLibro(){
        var row = tablaLibros.getSelectedRow();
        if(row != -1){
            String idLibro = tablaLibros.getModel().getValueAt(row, 0).toString();
            var libro = new Libro();
            libro.setIdLibro(Integer.parseInt(idLibro));
            libroServicio.eliminarLibro(libro);
            mostrarMensaje("Libro " + idLibro + " eliminado");
            limpiarFormulario();
            listarLibros();
        }else {
            mostrarMensaje("Debe seleccionar un registro");
        }
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarFormulario(){
        tituloTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        stockTexto.setText("");
    }

    private void createUIComponents() {
        idTexto = new JTextField();
        idTexto.setVisible(false);
        this.modeloTablaLibros = new DefaultTableModel(0,5){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columns = {"ID", "Título", "Autor", "Precio", "Cantidad"};
        this.modeloTablaLibros.setColumnIdentifiers(columns);
        this.tablaLibros = new JTable(modeloTablaLibros);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarLibros();

    }

    private void listarLibros(){
        modeloTablaLibros.setRowCount(0);
        var libros = libroServicio.listarLibros();
        libros.forEach(libro -> {
            Object[] libroData = {
                libro.getIdLibro(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getPrecio(),
                libro.getStock()
            };
            modeloTablaLibros.addRow(libroData);
        });
    }
}