package com.enesderin.FixTrackBackend.service.impl;

import com.enesderin.FixTrackBackend.dto.request.CustomerRequest;
import com.enesderin.FixTrackBackend.dto.response.CustomerResponse;
import com.enesderin.FixTrackBackend.dto.response.ServiceFormResponse;
import com.enesderin.FixTrackBackend.exception.ErrorMessage;
import com.enesderin.FixTrackBackend.exception.MessageType;
import com.enesderin.FixTrackBackend.exception.handler.BaseException;
import com.enesderin.FixTrackBackend.model.Customer;
import com.enesderin.FixTrackBackend.model.ServiceForm;
import com.enesderin.FixTrackBackend.repository.CustomerRepository;
import com.enesderin.FixTrackBackend.repository.ServiceFormRepository;
import com.enesderin.FixTrackBackend.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse getCustomer(long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND, "Customer not found")));

        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setCompany(customer.getCompany());
        response.setAddress(customer.getAddress());
        response.setPhone(customer.getPhone());
        response.setEmail(customer.getEmail());

        return response;
    }

    @Override
    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll().stream().map(customer -> {
            CustomerResponse response = new CustomerResponse();
            response.setId(customer.getId());
            response.setName(customer.getName());
            response.setCompany(customer.getCompany());
            response.setAddress(customer.getAddress());
            response.setPhone(customer.getPhone());
            response.setEmail(customer.getEmail());
            return response;
        }).toList();
    }

    @Override
    public CustomerResponse addCustomer(CustomerRequest request) {

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setCompany(request.getCompany());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());

        customerRepository.save(customer);

        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setCompany(customer.getCompany());
        response.setAddress(customer.getAddress());
        response.setPhone(customer.getPhone());
        response.setEmail(customer.getEmail());

        return response;
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND, "Customer not found")));

        customer.setName(request.getName());
        customer.setCompany(request.getCompany());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());

        customerRepository.save(customer);

        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setCompany(customer.getCompany());
        response.setAddress(customer.getAddress());
        response.setPhone(customer.getPhone());
        response.setEmail(customer.getEmail());

        return response;
    }

    @Override
    public Long deleteCustomer(long id) {
        customerRepository.deleteById(id);
        return id;
    }
}
