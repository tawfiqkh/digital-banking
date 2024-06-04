package enset.iibdcc.digitalbanking.services;

import enset.iibdcc.digitalbanking.dtos.AccountOperationCreateRequestDTO;
import enset.iibdcc.digitalbanking.dtos.AmountTransferRequestDTO;
import enset.iibdcc.digitalbanking.dtos.BankAccountCreateRequestDTO;
import enset.iibdcc.digitalbanking.dtos.BankAccountDTO;
import enset.iibdcc.digitalbanking.dtos.CurrentBankAccountDTO;
import enset.iibdcc.digitalbanking.dtos.CustomerDTO;
import enset.iibdcc.digitalbanking.exceptions.BalanceNotSufficientException;
import enset.iibdcc.digitalbanking.exceptions.BankAccountNotFoundException;
import enset.iibdcc.digitalbanking.exceptions.CustomerNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

public interface IBankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customer);
    CustomerDTO updateCustomer(CustomerDTO customer, UUID id) throws CustomerNotFoundException;
    CustomerDTO findCustomerById(UUID id) throws  CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    List<BankAccountDTO> listAccounts();
    CurrentBankAccountDTO saveBankAccount(BankAccountCreateRequestDTO bankAccountCreateRequest) throws CustomerNotFoundException;
    BankAccountDTO getBankAccount(UUID id) throws BankAccountNotFoundException;
    void debit(AccountOperationCreateRequestDTO accountOperationCreateRequest) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(AccountOperationCreateRequestDTO accountOperationCreateRequest) throws BankAccountNotFoundException;
    void transfer(AmountTransferRequestDTO amountTransferRequest) throws BankAccountNotFoundException, BalanceNotSufficientException;
}
