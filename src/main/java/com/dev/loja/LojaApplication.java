package com.dev.loja;

import com.dev.loja.enums.UserRole;
import com.dev.loja.model.User;
import com.dev.loja.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class LojaApplication implements CommandLineRunner {
	private UserRepository userRepository;

	public LojaApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LojaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		criarPrimeiroUsuario();
	}

	public void criarPrimeiroUsuario(){
		Optional<User> user = userRepository.findUserByLogin("admin@teste.com");
		if(user.isEmpty()){
			User newUser = new User();
			newUser.setNome("Administrador");
			newUser.setSobrenome("Admin");
			newUser.setLogin("admin@teste.com");
			newUser.setPassword( new BCryptPasswordEncoder().encode("123123"));
			newUser.setRole(UserRole.ADMIN);
			userRepository.save(newUser);
			System.out.println("***** O administrador foi criado *****");
		}
	}
}
