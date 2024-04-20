package test.tienda_libros.servicio;

import test.tienda_libros.modelo.Libro;
import java.util.List;

/**
 * The ILibroServicio interface represents the service layer for managing books in a bookstore system.
 * It provides methods for listing books, searching for a book by ID, saving a book, and deleting a book.
 */
public interface ILibroServicio {

    public List<Libro> listarLibros();

    public Libro buscarLibroPorId(Integer idLibro);

    public void guardarLibro(Libro libro);
    
    public void eliminarLibro(Libro libro);
}

