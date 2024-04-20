package test.tienda_libros.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import test.tienda_libros.modelo.Libro;

/**
 * The LibroRepositorio interface is a repository interface that extends JpaRepository.
 * It provides CRUD operations for the Libro entity.
 */

public interface LibroRepositorio extends JpaRepository<Libro, Integer>{

}
