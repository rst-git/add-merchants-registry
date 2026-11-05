package br.com.myproject.service;

import br.com.myproject.model.Merchant;
import br.com.myproject.repository.MerchantRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MerchantServiceImplTest {

    @Mock
    private MerchantRepository merchantRepository;

    @InjectMocks
    private MerchantServiceImpl merchantService;

    @Test
    void shouldSaveMerchant() {
        Merchant merchant = new Merchant("12345678000199", "Padaria Central Ltda");
        Merchant savedMerchant = new Merchant(1L, "12345678000199", "Padaria Central Ltda");
        when(merchantRepository.save(merchant)).thenReturn(savedMerchant);

        Merchant result = merchantService.save(merchant);

        assertThat(result).isSameAs(savedMerchant);
        verify(merchantRepository).save(merchant);
    }

    @Test
    void shouldFindMerchantByCnpj() {
        String cnpj = "12345678000199";
        Merchant merchant = new Merchant(1L, cnpj, "Padaria Central Ltda");
        when(merchantRepository.findByCnpj(cnpj)).thenReturn(Optional.of(merchant));

        Optional<Merchant> result = merchantService.findByCnpj(cnpj);

        assertThat(result).contains(merchant);
        verify(merchantRepository).findByCnpj(cnpj);
    }

    @Test
    void shouldFindAllMerchants() {
        List<Merchant> merchants = List.of(
                new Merchant(1L, "12345678000199", "Padaria Central Ltda"),
                new Merchant(2L, "98765432000188", "Restaurante Bom Sabor"));
        when(merchantRepository.findAll()).thenReturn(merchants);

        List<Merchant> result = merchantService.findAll();

        assertThat(result).containsExactlyElementsOf(merchants);
        verify(merchantRepository).findAll();
    }
}