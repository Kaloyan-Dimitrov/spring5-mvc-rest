package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
        CustomerMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CustomerMapper.class);

        CustomerDTO customerToCustomerDTO(Customer customer);
        Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
