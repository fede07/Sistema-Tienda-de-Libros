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

/**
 * The LibroForm class represents a form for managing books in a book store system.
 * It extends the JFrame class and provides functionality for adding, modifying, and deleting books.
 * The form displays a table of books and allows the user to select a book to view or modify its details.
 * The form also provides fields for entering the title, author, price, and stock of a book to be added or modified.
 * The LibroForm class uses the LibroServicio class to interact with the book store system's data.
 */

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

    /**
     * Constructs a new instance of the LibroForm class.
     * 
     * @param libroServicio the LibroServicio object used for interacting with the libro service
     */

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

    /**
     * Sets up the form and initializes the GUI components.
     * Sets the content pane, default close operation, visibility, and size of the frame.
     * Calculates the center position of the screen and sets the location of the frame accordingly.
     */
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

    /**
     * Adds a new book to the bookstore system.
     * Validates the input fields and creates a new book object with the provided information.
     * Saves the book using the libroServicio instance, displays a success message, and updates the book list.
     * Clears the form fields after adding the book.
     */

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

    /**
     * Loads the selected book's information into the form fields.
     */

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

    /**
     * Modifies a book in the book store system.
     * If the ID field is empty, it displays an error message.
     * Otherwise, it checks if the title field is empty. If so, it displays an error message and sets focus on the title field.
     * If all fields are filled, it retrieves the values from the input fields, creates a new book object, and saves it using the book service.
     * Finally, it displays a success message, clears the form, and updates the list of books.
     */
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

    /**
     * Deletes a book from the system.
     * If a book is selected in the table, it retrieves the book's ID and uses it to delete the book from the system.
     * After deleting the book, it displays a success message, clears the form, and updates the book list.
     * If no book is selected, it displays an error message.
     */

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

    /**
     * Displays a message dialog with the given message.
     *
     * @param mensaje the message to be displayed
     */

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Clears the form by resetting the text fields for title, author, price, and stock.
     */

    private void limpiarFormulario(){
        tituloTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        stockTexto.setText("");
    }

    /**
     * Creates the UI components for the LibroForm class.
     * This method initializes the JTextField, DefaultTableModel, JTable, and sets the column identifiers.
     * It also sets the selection mode for the JTable and calls the listarLibros method.
     */

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

    /**
     * Retrieves a list of books from the book service and populates the table with the book data.
     */
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