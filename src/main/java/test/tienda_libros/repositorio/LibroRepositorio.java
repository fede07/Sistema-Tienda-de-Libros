package test.tienda_libros.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import test.tienda_libros.modelo.Libro;

public interface LibroRepositorio extends JpaRepository<Libro, Integer>{

}
