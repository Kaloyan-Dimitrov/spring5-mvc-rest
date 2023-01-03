package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 9/24/17.
 */
@Component
public class Bootstrap implements CommandLineRunner{

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Category Data Loaded = " + categoryRepository.count());
        
        // bootstrap customers
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Michael");
        customer1.setLastName("Weston");
        
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");
        
        Customer customer3 = new Customer();
        customer3.setId(3L);
        customer3.setFirstName("Fiona");
        customer3.setLastName("Glenanne");
        
        Customer customer4 = new Customer();
        customer4.setId(4L);
        customer4.setFirstName("Alex");
        customer4.setLastName("Guiness");
        
        Customer customer5 = new Customer();
        customer5.setId(5L);
        customer5.setFirstName("Sam");
        customer5.setLastName("Axe");
        
        Customer customer6 = new Customer();
        customer6.setId(6L);
        customer6.setFirstName("Bill");
        customer6.setLastName("Nershi");
        
        Customer customer7 = new Customer();
        customer7.setId(7L);
        customer7.setFirstName("Jimmy");
        customer7.setLastName("Buffet");
        
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
        customerRepository.save(customer5);
        customerRepository.save(customer6);
        customerRepository.save(customer7);
    }
}
