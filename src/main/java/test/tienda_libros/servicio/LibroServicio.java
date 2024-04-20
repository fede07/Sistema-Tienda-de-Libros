package test.tienda_libros.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.tienda_libros.modelo.Libro;
import test.tienda_libros.repositorio.LibroRepositorio;

/**
 * This class represents the service layer for managing books in the bookstore system.
 * It provides methods for listing, searching, saving, and deleting books.
 */
@Service
public class LibroServicio implements ILibroServicio{

    @Autowired
    private LibroRepositorio libroRepositorio;

    /**
     * Retrieves a list of all books in the bookstore.
     * 
     * @return a list of books
     */
    @Override
    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }

    /**
     * Searches for a book in the bookstore by its ID.
     * 
     * @param idLibro the ID of the book to search for
     * @return the book with the specified ID, or null if not found
     */
    @Override
    public Libro buscarLibroPorId(Integer idLibro) {
        Libro libro = libroRepositorio.findById(idLibro).orElse(null);
        return libro;
    }

    /**
     * Saves a new book or updates an existing book in the bookstore.
     * 
     * @param libro the book to be saved or updated
     */
    @Override
    public void guardarLibro(Libro libro) {
        libroRepositorio.save(libro);
    }

    /**
     * Deletes a book from the bookstore.
     * 
     * @param libro the book to be deleted
     */
    @Override
    public void eliminarLibro(Libro libro) {
        libroRepositorio.delete(libro);
    }
}
