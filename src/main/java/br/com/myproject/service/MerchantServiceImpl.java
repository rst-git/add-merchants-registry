package br.com.myproject.service;

import br.com.myproject.model.Merchant;
import br.com.myproject.repository.MerchantRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl {

    private final MerchantRepository merchantRepository;

    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Merchant save(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    public Optional<Merchant> findByCnpj(String cnpj) {
        return merchantRepository.findByCnpj(cnpj);
    }

    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }
}