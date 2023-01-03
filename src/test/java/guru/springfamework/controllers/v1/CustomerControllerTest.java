package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractRestControllerTest {
    private static final Long ID = 3L;
    public static final String CUSTOMER_URL = "/api/v1/customers/" + ID;
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerDTOS.add(new CustomerDTO());
        customerDTOS.add(new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(get("/api/v1/customers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jim");
        customerDTO.setLastName("Bob");
        customerDTO.setId(ID);
        customerDTO.setCustomerUrl(CUSTOMER_URL);

        when(customerService.getCustomerById(ID)).thenReturn(customerDTO);

        mockMvc.perform(get(CUSTOMER_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Jim")));
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Fred");
        customerDTO.setLastName("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName(customerDTO.getLastName());
        returnDTO.setCustomerUrl(CUSTOMER_URL);

        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);

        System.out.println("CustomerDTO: " + customerDTO);
        mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));

    }

    @Test
    public void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Fred");
        customerDTO.setLastName("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName(customerDTO.getLastName());
        returnDTO.setCustomerUrl(CUSTOMER_URL);
        
        when(customerService.saveCustomerById(ID, customerDTO)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(CUSTOMER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName("Flintstone");
        returnDTO.setCustomerUrl(CUSTOMER_URL);

        when(customerService.patchCustomerById(ID, customerDTO)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(patch(CUSTOMER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));

    }
}