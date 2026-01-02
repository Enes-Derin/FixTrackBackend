package com.enesderin.FixTrackBackend.service;

import com.enesderin.FixTrackBackend.dto.request.ServiceFormRequest;
import com.enesderin.FixTrackBackend.dto.response.ServiceFormResponse;

import java.util.List;

public interface ServiceFormService {
    ServiceFormResponse getServiceForm(long id);
    List<ServiceFormResponse> getServiceForms();
    List<ServiceFormResponse> getCustomerServiceForms(long customerId);
    ServiceFormResponse addServiceForm(ServiceFormRequest serviceForm);
    ServiceFormResponse updateServiceFormSignatures(long id, String customerSignature, String technicianSignature);
    byte[] generateServiceFormPdf(long id);
    Long deleteServiceForm(long id);
}
