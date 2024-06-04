package enset.iibdcc.digitalbanking.controllers;

import enset.iibdcc.digitalbanking.dtos.CustomerDTO;
import enset.iibdcc.digitalbanking.exceptions.CustomerNotFoundException;
import enset.iibdcc.digitalbanking.services.IBankAccountService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerController.CUSTOMER_END_POINT)
@AllArgsConstructor
public class CustomerController {
    static final String CUSTOMER_END_POINT = "/customers";

    private IBankAccountService bankAccountService;

    @GetMapping("/")
    ResponseEntity<List<CustomerDTO>> customers(){
        List<CustomerDTO> customers = bankAccountService.listCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id")UUID id) throws CustomerNotFoundException {
        CustomerDTO customer = bankAccountService.findCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customer){
       CustomerDTO customerDTO = bankAccountService.saveCustomer(customer);
       return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<CustomerDTO> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customer) throws CustomerNotFoundException{
        CustomerDTO customerDTO = bankAccountService.updateCustomer(customer, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
    }

}
