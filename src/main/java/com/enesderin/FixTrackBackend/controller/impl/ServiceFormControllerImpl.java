package com.enesderin.FixTrackBackend.controller.impl;


import com.enesderin.FixTrackBackend.controller.RestBaseController;
import com.enesderin.FixTrackBackend.controller.RootEntity;
import com.enesderin.FixTrackBackend.controller.ServiceFormController;
import com.enesderin.FixTrackBackend.dto.request.ServiceFormRequest;
import com.enesderin.FixTrackBackend.dto.request.UpdateSignatureRequest;
import com.enesderin.FixTrackBackend.dto.response.ServiceFormResponse;
import com.enesderin.FixTrackBackend.service.ServiceFormService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-form")
@AllArgsConstructor
public class ServiceFormControllerImpl extends RestBaseController implements ServiceFormController {

    private ServiceFormService serviceFormService;

    @GetMapping("/{id}")
    @Override
    public RootEntity<ServiceFormResponse> getServiceForm(@PathVariable Long id) {
        return success(serviceFormService.getServiceForm(id));
    }

    @GetMapping
    @Override
    public RootEntity<List<ServiceFormResponse>> getServiceForms() {
        return success(serviceFormService.getServiceForms());
    }

    @GetMapping("/customerId/{id}")
    @Override
    public RootEntity<List<ServiceFormResponse>> getCustomerServiceForms(@PathVariable Long id) {
        return success(serviceFormService.getCustomerServiceForms(id));
    }

    @PostMapping
    @Override
    public RootEntity<ServiceFormResponse> addServiceForm(@Valid @RequestBody ServiceFormRequest serviceForm) {
        return success(serviceFormService.addServiceForm(serviceForm));
    }

    @GetMapping("/{id}/pdf")
    @Override
    public byte[] downloadServiceFormPdf(@PathVariable long id) {
        // Arayüz için basit byte[] döndürüyoruz; ResponseEntity versiyonu aşağıda
        return serviceFormService.generateServiceFormPdf(id);
    }

    // Eğer doğrudan dosya indirtmek istersen:
    @GetMapping("/{id}/pdf/download")
    public ResponseEntity<byte[]> downloadServiceFormPdfResponse(@PathVariable long id) {

        // PDF byte'ları
        byte[] pdfBytes = serviceFormService.generateServiceFormPdf(id);

        // Dosya adı bilgileri
        ServiceFormResponse form = serviceFormService.getServiceForm(id);

        String companyName = sanitizeFileName(form.getCustomer().getCompany());
        String title = sanitizeFileName(form.getTitle());

        String date = String.valueOf(form.getCreatedDate().getYear());
        String fileName = companyName + "_" + title + "_" + date + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
    private String sanitizeFileName(String input) {
        if (input == null) return "dosya";

        return input
                .replaceAll("[çÇ]", "c")
                .replaceAll("[ğĞ]", "g")
                .replaceAll("[ıİ]", "i")
                .replaceAll("[öÖ]", "o")
                .replaceAll("[şŞ]", "s")
                .replaceAll("[üÜ]", "u")
                .replaceAll("\\s+", "_")
                .replaceAll("[^a-zA-Z0-9_]", "");
    }



    @PutMapping("/{id}/signatures")
    @Override
    public RootEntity<ServiceFormResponse> updateServiceFormSignatures(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSignatureRequest request) {
        return success(serviceFormService.updateServiceFormSignatures(
                id,
                request.getCustomerSignature(),
                request.getTechnicianSignature()
        ));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<Long> deleteServiceForm(@PathVariable long id) {
        return success(serviceFormService.deleteServiceForm(id));
    }
}
