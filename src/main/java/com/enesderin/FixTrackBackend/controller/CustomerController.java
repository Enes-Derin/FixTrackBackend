package com.enesderin.FixTrackBackend.controller;



import com.enesderin.FixTrackBackend.dto.request.CustomerRequest;
import com.enesderin.FixTrackBackend.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerController {
    RootEntity<CustomerResponse> getCustomer(long id);
    RootEntity<List<CustomerResponse>> getCustomers();
    RootEntity<CustomerResponse> addCustomer(CustomerRequest customer);
    RootEntity<CustomerResponse> updateCustomer(Long id, CustomerRequest customer);
    RootEntity<Long> deleteCustomer(long id);
}
