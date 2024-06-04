package enset.iibdcc.digitalbanking.controllers;

import enset.iibdcc.digitalbanking.dtos.AccountOperationCreateRequestDTO;
import enset.iibdcc.digitalbanking.dtos.AmountTransferRequestDTO;
import enset.iibdcc.digitalbanking.dtos.BankAccountCreateRequestDTO;
import enset.iibdcc.digitalbanking.dtos.BankAccountDTO;
import enset.iibdcc.digitalbanking.exceptions.BalanceNotSufficientException;
import enset.iibdcc.digitalbanking.exceptions.BankAccountNotFoundException;
import enset.iibdcc.digitalbanking.exceptions.CustomerNotFoundException;
import enset.iibdcc.digitalbanking.services.IBankAccountService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(BankAccountController.BANK_ACCOUNT_ENDPOINT)
public class BankAccountController {
    static final String BANK_ACCOUNT_ENDPOINT = "account";
    private IBankAccountService bankAccountService;
    @GetMapping("/")
    ResponseEntity<List<BankAccountDTO>> accounts(){
        List<BankAccountDTO> accounts = bankAccountService.listAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    ResponseEntity<BankAccountDTO> findAccount(@PathVariable UUID id) throws BankAccountNotFoundException {
        BankAccountDTO account = bankAccountService.getBankAccount(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    ResponseEntity<BankAccountDTO> saveAccount(@RequestBody BankAccountCreateRequestDTO account) throws CustomerNotFoundException {
        BankAccountDTO accountDTO = bankAccountService.saveBankAccount(account);
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("/credit")
    ResponseEntity<HttpStatus> createCredit(@RequestBody AccountOperationCreateRequestDTO operation) throws BankAccountNotFoundException {
        bankAccountService.credit(operation);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/debit")
    ResponseEntity<HttpStatus> createDebit(@RequestBody AccountOperationCreateRequestDTO operation) throws BalanceNotSufficientException, BankAccountNotFoundException {
        bankAccountService.debit(operation);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    ResponseEntity<HttpStatus> amountTransfer(@RequestBody AmountTransferRequestDTO amountTransferRequest) throws BalanceNotSufficientException, BankAccountNotFoundException {
        bankAccountService.transfer(amountTransferRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
