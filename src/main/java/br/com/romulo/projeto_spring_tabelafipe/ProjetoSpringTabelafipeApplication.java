package br.com.romulo.projeto_spring_tabelafipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.romulo.projeto_spring_tabelafipe.principal.Principal;



@SpringBootApplication
public class ProjetoSpringTabelafipeApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSpringTabelafipeApplication.class, args);
	}

	@Override  //sera o methodo main
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}

}
