package test.tienda_libros;

import java.awt.EventQueue;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import test.tienda_libros.vista.LibroForm;

/**
	 * The main entry point for the TiendaLibrosApplication.
	 * It starts the Spring application context and displays the LibroForm.
**/

@SpringBootApplication
public class TiendaLibrosApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
		 	new SpringApplicationBuilder(TiendaLibrosApplication.class)
			.headless(false)
			.web(WebApplicationType.NONE)
			.run(args);
		EventQueue.invokeLater(()->{
			LibroForm libroForm = context.getBean(LibroForm.class);
			libroForm.setVisible(true);
		});
	}

}
