package test.tienda_libros.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import test.tienda_libros.modelo.Libro;
import test.tienda_libros.repositorio.LibroRepositorio;

public class LibroServicio implements ILibroServicio{

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Override
    public List<Libro> listarLibros() {
       return libroRepositorio.findAll();
    }

    @Override
    public Libro buscarLibro(Integer idLibro) {
        return libroRepositorio.findById(idLibro).orElse(null);
    }

    @Override
    public void guardarLibro(Libro libro) {
        libroRepositorio.save(libro);
    }

    @Override
    public void eliminarLibro(Libro libro) {
        libroRepositorio.delete(libro);
    }
}
