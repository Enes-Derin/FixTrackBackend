package com.enesderin.FixTrackBackend.controller.impl;


import com.enesderin.FixTrackBackend.controller.CustomerController;
import com.enesderin.FixTrackBackend.controller.RestBaseController;
import com.enesderin.FixTrackBackend.controller.RootEntity;
import com.enesderin.FixTrackBackend.dto.request.CustomerRequest;
import com.enesderin.FixTrackBackend.dto.response.CustomerResponse;
import com.enesderin.FixTrackBackend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerControllerImpl extends RestBaseController implements CustomerController {
    private CustomerService customerService;


    @GetMapping("/{id}")
    @Override
    public RootEntity<CustomerResponse> getCustomer(@PathVariable long id) {
        return success(customerService.getCustomer(id));
    }

    @GetMapping
    @Override
    public RootEntity<List<CustomerResponse>> getCustomers() {
        return success(customerService.getCustomers());
    }

    @PostMapping
    @Override
    public RootEntity<CustomerResponse> addCustomer(@Valid @RequestBody CustomerRequest customer) {
        return success(customerService.addCustomer(customer));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<CustomerResponse> updateCustomer(@PathVariable Long id,@Valid @RequestBody CustomerRequest customer) {
        return success(customerService.updateCustomer(id, customer));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<Long> deleteCustomer(@PathVariable long id) {
        success(customerService.deleteCustomer(id));
        return success(id);
    }
}
