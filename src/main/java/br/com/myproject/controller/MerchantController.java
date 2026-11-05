package br.com.myproject.controller;

import br.com.myproject.dto.MerchantRequest;
import br.com.myproject.model.Merchant;
import br.com.myproject.service.MerchantServiceImpl;
import br.com.myproject.validation.MerchantRequestValidator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchants")
public class MerchantController {

    private final MerchantServiceImpl merchantService;
    private final MerchantRequestValidator merchantRequestValidator;

    public MerchantController(
            MerchantServiceImpl merchantService,
            MerchantRequestValidator merchantRequestValidator) {
        this.merchantService = merchantService;
        this.merchantRequestValidator = merchantRequestValidator;
    }

    @PostMapping
    public ResponseEntity<Merchant> save(@RequestBody MerchantRequest request) {
        merchantRequestValidator.validateCnpj(request.getCnpj());
        Merchant merchant = new Merchant(request.getCnpj(), request.getRazaoSocial());
        Merchant savedMerchant = merchantService.save(merchant);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMerchant);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<Merchant> findByCnpj(@PathVariable String cnpj) {
        merchantRequestValidator.validateCnpj(cnpj);
        return ResponseEntity.of(merchantService.findByCnpj(cnpj));
    }

    @GetMapping
    public List<Merchant> findAll() {
        return merchantService.findAll();
    }
}