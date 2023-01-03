package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface CustomerMapper {
        CustomerMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CustomerMapper.class);

        @Mapping(target = "customerUrl", ignore = true) // Needed so the warning does not shown, it is mapped in calculateTotal

        CustomerDTO customerToCustomerDTO(Customer customer);
        Customer customerDTOToCustomer(CustomerDTO customerDTO);
        @AfterMapping
        default void createCustomerUrl(Customer order, @MappingTarget CustomerDTO dto) {
                dto.setCustomerUrl("/api/v1/customers/" + order.getId());
        }
}
