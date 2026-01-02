package com.enesderin.FixTrackBackend.service;

import com.enesderin.FixTrackBackend.dto.request.CustomerRequest;
import com.enesderin.FixTrackBackend.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse getCustomer(long id);
    List<CustomerResponse> getCustomers();
    CustomerResponse addCustomer(CustomerRequest customer);
    CustomerResponse updateCustomer(Long id, CustomerRequest customer);
    Long deleteCustomer(long id);
}
