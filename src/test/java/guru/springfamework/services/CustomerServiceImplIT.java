package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerServiceImplIT {
    public static final String NAME = "JoeUpdated";
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;
    private Long ID;

    @BeforeEach
    void setUp() {
        System.out.println("Loading Customer Data");
        System.out.println("Customers: " + customerRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);

        ID = getCustomerIdValue();
    }

    @Test
    void patchCustomerByIdFirstName() {
        Customer originalCustomer = customerRepository.getOne(ID);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(NAME);

        customerService.patchCustomerById(ID, customerDTO);
        CustomerDTO savedCustomer = customerService.getCustomerById(ID);

        assertEquals(NAME, savedCustomer.getFirstName());
        assertEquals(ID, savedCustomer.getId());
        assertNotEquals(originalFirstName, savedCustomer.getFirstName());
        assertEquals(originalLastName, savedCustomer.getLastName());

    }
    @Test
    void patchCustomerByIdLastName() {
        Customer originalCustomer = customerRepository.getOne(ID);
        assertNotNull(originalCustomer);

        String originalLastName = originalCustomer.getLastName();
        String originalFirstName = originalCustomer.getFirstName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(NAME);
        customerService.patchCustomerById(ID, customerDTO);
        CustomerDTO savedCustomer = customerService.getCustomerById(ID);
        assertEquals(NAME, savedCustomer.getLastName());
        assertEquals(ID, savedCustomer.getId());
        assertEquals(originalFirstName, savedCustomer.getFirstName());
        assertNotEquals(originalLastName, savedCustomer.getLastName());
    }

    private Long getCustomerIdValue() {
        return customerRepository.findAll().get(0).getId();
    }
}