package com.enesderin.FixTrackBackend.controller;


import com.enesderin.FixTrackBackend.dto.request.ServiceFormRequest;
import com.enesderin.FixTrackBackend.dto.request.UpdateSignatureRequest;
import com.enesderin.FixTrackBackend.dto.response.ServiceFormResponse;

import java.util.List;

public interface ServiceFormController {
    RootEntity<ServiceFormResponse> getServiceForm(long id);
    RootEntity<List<ServiceFormResponse>> getServiceForms();
    RootEntity<List<ServiceFormResponse>> getCustomerServiceForms(long customerId);
    RootEntity<ServiceFormResponse> addServiceForm(ServiceFormRequest serviceForm);
    RootEntity<ServiceFormResponse> updateServiceFormSignatures(long id, UpdateSignatureRequest request);
    byte[] downloadServiceFormPdf(long id);
    RootEntity<Long> deleteServiceForm(long id);
}
