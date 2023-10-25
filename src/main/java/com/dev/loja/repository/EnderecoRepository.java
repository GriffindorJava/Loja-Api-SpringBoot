package com.dev.loja.repository;

import com.dev.loja.model.Endereco;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("UPDATE Endereco e SET e.principal=false WHERE e.user.id = :userId")
    @Modifying
    @Transactional
    void desativarEnderecos(Long userId);
}
