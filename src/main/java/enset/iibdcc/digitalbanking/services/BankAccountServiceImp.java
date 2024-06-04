package enset.iibdcc.digitalbanking.services;

import enset.iibdcc.digitalbanking.dtos.AccountOperationCreateRequestDTO;
import enset.iibdcc.digitalbanking.dtos.AmountTransferRequestDTO;
import enset.iibdcc.digitalbanking.dtos.BankAccountCreateRequestDTO;
import enset.iibdcc.digitalbanking.dtos.BankAccountDTO;
import enset.iibdcc.digitalbanking.dtos.CurrentBankAccountDTO;
import enset.iibdcc.digitalbanking.dtos.CustomerDTO;
import enset.iibdcc.digitalbanking.models.AccountOperationEntity;
import enset.iibdcc.digitalbanking.models.BankAccountEntity;
import enset.iibdcc.digitalbanking.models.CurrentAccountEntity;
import enset.iibdcc.digitalbanking.models.CustomerEntity;
import enset.iibdcc.digitalbanking.models.OperationType;
import enset.iibdcc.digitalbanking.exceptions.BalanceNotSufficientException;
import enset.iibdcc.digitalbanking.exceptions.BankAccountNotFoundException;
import enset.iibdcc.digitalbanking.exceptions.CustomerNotFoundException;
import enset.iibdcc.digitalbanking.repositories.AccountOperationRepository;
import enset.iibdcc.digitalbanking.repositories.BankAccountRepository;
import enset.iibdcc.digitalbanking.repositories.CustomerRepository;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class BankAccountServiceImp implements IBankAccountService{

    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;
    private ModelMapper modelMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {

        CustomerEntity mappedCustomer = modelMapper.map(customer, CustomerEntity.class);
        CustomerEntity customerEntity = customerRepository.save(mappedCustomer);
        log.info("saving new customer"+ customerEntity);
        return modelMapper.map(customerEntity, CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customer, UUID id) throws CustomerNotFoundException {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("customer with id: %s not found", id)));
        customerEntity.toBuilder()
                .email(customer.getEmail())
                .name(customer.getName());
        CustomerEntity updatedCustomer = customerRepository.save(customerEntity);

        return modelMapper.map(updatedCustomer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO findCustomerById(UUID id) throws CustomerNotFoundException {
       CustomerEntity customer = customerRepository.findById(id)
               .orElseThrow(() -> new CustomerNotFoundException(String.format("customer with id: %s not found", id)));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public CurrentBankAccountDTO saveBankAccount(BankAccountCreateRequestDTO bankAccountCreateRequestDTO) throws CustomerNotFoundException {
        CustomerEntity customer = customerRepository.findById(bankAccountCreateRequestDTO.getCustomerId())
                .orElseThrow( () -> new CustomerNotFoundException(String.format("customer with id: %s, not found ", bankAccountCreateRequestDTO.getCustomerId())));

        CurrentAccountEntity currentAccount =  CurrentAccountEntity.builder()
                .id(UUID.randomUUID())
                .createdAt(new Date())
                .balance(bankAccountCreateRequestDTO.getInitialBalance())
                .overDraft(bankAccountCreateRequestDTO.getOverDraft())
                .customer(customer)
                .build();

        log.info("saving new bank account"+ currentAccount);
        CurrentAccountEntity savedCurrentAccount = bankAccountRepository.save(currentAccount);
        return modelMapper.map(savedCurrentAccount, CurrentBankAccountDTO.class);
    }

    @Override
    public List<CustomerDTO> listCustomers() {

        log.info("retrieving all customers");
        List<CustomerEntity> customerList = customerRepository.findAll();
        return modelMapper.map(customerList, new TypeToken<List<CustomerDTO>>() {}.getType());
    }

    @Override
    public List<BankAccountDTO> listAccounts() {
        log.info("retrieving all accounts");
        List<BankAccountEntity> accounts = bankAccountRepository.findAll();
        return modelMapper.map(accounts, new TypeToken<List<BankAccountDTO>>(){}.getType());
    }

    @Override
    public BankAccountDTO getBankAccount(UUID id) throws BankAccountNotFoundException {

        log.info("retrieving  bank details for id "+ id);
        BankAccountEntity bankAccount = bankAccountRepository.findById(id)
                .orElseThrow( ()-> new BankAccountNotFoundException(String.format("account with id: %s not found", id)));

        return modelMapper.map(bankAccount, BankAccountDTO.class);
    }

    @Override
    public void debit(AccountOperationCreateRequestDTO accountOperationCreateRequest) throws BalanceNotSufficientException, BankAccountNotFoundException {

        log.info(String.format("transferring amount %f to account: %s", accountOperationCreateRequest.getAmount(), accountOperationCreateRequest.getAccountId().toString()));
        BankAccountEntity account = bankAccountRepository.findById(accountOperationCreateRequest.getAccountId())
                .orElseThrow( ()-> new BankAccountNotFoundException(String.format("account with id: %s not found", accountOperationCreateRequest.getAccountId())));

        double updatedAccountBalance = account.getBalance() - accountOperationCreateRequest.getAmount();
         if (updatedAccountBalance < 0) throw new BalanceNotSufficientException("Balance not sufficient");

         account.setBalance(updatedAccountBalance);
        AccountOperationEntity accountOperation = AccountOperationEntity.builder()
                .id(UUID.randomUUID())
                .operationDate(new Date())
                .amount(accountOperationCreateRequest.getAmount())
                .bankAccount(account)
                .description(accountOperationCreateRequest.getDescription())
                .type(OperationType.DEBIT)
                .build();
        accountOperationRepository.save(accountOperation);
        bankAccountRepository.save(account);
    }

    @Override
    public void credit(AccountOperationCreateRequestDTO accountOperationCreateRequest) throws BankAccountNotFoundException {
        BankAccountEntity account = bankAccountRepository.findById(accountOperationCreateRequest.getAccountId())
                .orElseThrow( ()-> new BankAccountNotFoundException(String.format("account with id: %s not found", accountOperationCreateRequest.getAccountId())));

        double updatedAccountBalance = account.getBalance() + accountOperationCreateRequest.getAmount();

        account.setBalance(updatedAccountBalance);
        AccountOperationEntity accountOperation = AccountOperationEntity.builder()
                .id(UUID.randomUUID())
                .operationDate(new Date())
                .amount(accountOperationCreateRequest.getAmount())
                .bankAccount(account)
                .description(accountOperationCreateRequest.getDescription())
                .type(OperationType.CREDIT)
                .build();
        accountOperationRepository.save(accountOperation);
        bankAccountRepository.save(account);
    }

    @Override
    public void transfer(AmountTransferRequestDTO amountTransferRequest) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit( new AccountOperationCreateRequestDTO(amountTransferRequest.getAccountIdSource(), amountTransferRequest.getAmount(),"Transfer to "+amountTransferRequest.getAccountIdTarget()));
        credit(new AccountOperationCreateRequestDTO(amountTransferRequest.getAccountIdTarget(),amountTransferRequest.getAmount(),"Transfer from "+amountTransferRequest.getAccountIdSource()));
    }
}
