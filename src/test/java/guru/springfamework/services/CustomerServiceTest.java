package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    public static final Long ID = 1L;

    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getCustomerById() {
        Customer customer = new Customer();
        customer.setId(ID);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        CustomerDTO returnedCustomer = customerService.getCustomerById(ID);
        assertEquals(customer.getFirstName(), returnedCustomer.getFirstName());
        assertEquals(customer.getLastName(), returnedCustomer.getLastName());
        assertEquals(customer.getId(), returnedCustomer.getId());
        assertEquals("/api/v1/customers/" + ID, returnedCustomer.getCustomerUrl());
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> returnedCustomers = customerService.getAllCustomers();
        assertEquals(customers.size(), returnedCustomers.size());
    }
}