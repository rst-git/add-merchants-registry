package br.com.myproject.repository;

import br.com.myproject.model.Merchant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    /**
     * Busca um merchant pelo CNPJ.
     *
     * @param cnpj CNPJ a ser consultado
     * @return Optional contendo o Merchant se encontrado
     */
    Optional<Merchant> findByCnpj(String cnpj);

    /**
     * Verifica se já existe um merchant cadastrado com o CNPJ informado.
     *
     * @param cnpj CNPJ a ser verificado
     * @return true se já existir, false caso contrário
     */
    boolean existsByCnpj(String cnpj);
}
