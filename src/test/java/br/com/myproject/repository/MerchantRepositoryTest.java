package br.com.myproject.repository;

import br.com.myproject.model.Merchant;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("h2")
class MerchantRepositoryTest {

    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    @DisplayName("Deve salvar e buscar um merchant por ID")
    void shouldSaveAndFindMerchantById() {
        Merchant merchant = new Merchant("12345678000199", "Padaria Central Ltda");
        Merchant saved = merchantRepository.save(merchant);

        assertThat(saved.getMerchantId()).isNotNull();

        Optional<Merchant> found = merchantRepository.findById(saved.getMerchantId());
        assertThat(found).isPresent();
        assertThat(found.get().getCnpj()).isEqualTo("12345678000199");
        assertThat(found.get().getRazaoSocial()).isEqualTo("Padaria Central Ltda");
    }

    @Test
    @DisplayName("Deve buscar um merchant por CNPJ")
    void shouldFindByCnpj() {
        Merchant merchant = new Merchant("98765432000188", "Restaurante Bom Sabor");
        merchantRepository.save(merchant);

        Optional<Merchant> found = merchantRepository.findByCnpj("98765432000188");
        assertThat(found).isPresent();
        assertThat(found.get().getRazaoSocial()).isEqualTo("Restaurante Bom Sabor");
    }

    @Test
    @DisplayName("Deve verificar existencia por CNPJ")
    void shouldCheckExistsByCnpj() {
        Merchant merchant = new Merchant("11222333000144", "Loja do Silva");
        merchantRepository.save(merchant);

        boolean exists = merchantRepository.existsByCnpj("11222333000144");
        boolean notExists = merchantRepository.existsByCnpj("00000000000000");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}
