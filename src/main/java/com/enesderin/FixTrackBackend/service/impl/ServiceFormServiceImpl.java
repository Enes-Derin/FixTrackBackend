package com.enesderin.FixTrackBackend.service.impl;

import com.enesderin.FixTrackBackend.dto.request.ServiceFormRequest;
import com.enesderin.FixTrackBackend.dto.response.CustomerResponse;
import com.enesderin.FixTrackBackend.dto.response.ServiceFormResponse;
import com.enesderin.FixTrackBackend.exception.ErrorMessage;
import com.enesderin.FixTrackBackend.exception.MessageType;
import com.enesderin.FixTrackBackend.exception.handler.BaseException;
import com.enesderin.FixTrackBackend.model.Customer;
import com.enesderin.FixTrackBackend.model.ServiceForm;
import com.enesderin.FixTrackBackend.repository.CustomerRepository;
import com.enesderin.FixTrackBackend.repository.ServiceFormRepository;
import com.enesderin.FixTrackBackend.service.CloudinaryService;
import com.enesderin.FixTrackBackend.service.ServiceFormService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceFormServiceImpl implements ServiceFormService {

    private ServiceFormRepository serviceFormRepository;
    private CustomerRepository customerRepository;
    private CloudinaryService cloudinaryService;

    @Override
    public ServiceFormResponse addServiceForm(ServiceFormRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND, "Customer not found")));

        ServiceForm serviceForm = new ServiceForm();
        serviceForm.setTitle(request.getTitle());
        serviceForm.setDescription(request.getDescription());
        serviceForm.setCustomer(customer);
        serviceForm.setCustomerSignatureUrl(request.getCustomerSignature());
        serviceForm.setTechnicianSignatureUrl(request.getTechnicianSignature());

        serviceForm.setMachineType(request.getMachineType());
        serviceForm.setMachineSerialNumber(request.getMachineSerialNumber());
        serviceForm.setWorkingHours(request.getWorkingHours());
        serviceForm.setUsedParts(request.getUsedParts());
        serviceForm.setUsedPartQuantities(request.getUsedPartQuantities());

        serviceFormRepository.save(serviceForm);
        return mapToResponse(serviceForm);
    }

    @Override
    public ServiceFormResponse getServiceForm(long id) {
        ServiceForm serviceForm = serviceFormRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Service form not found")));

        return mapToResponse(serviceForm);
    }

    @Override
    public List<ServiceFormResponse> getServiceForms() {
        return serviceFormRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ServiceFormResponse> getCustomerServiceForms(long customerId) {
        return serviceFormRepository.findAllByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ServiceFormResponse updateServiceFormSignatures(
            long id, String customerSignature, String technicianSignature) {

        ServiceForm serviceForm = serviceFormRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Service form not found")));

        if (customerSignature != null && !customerSignature.isBlank()) {
            serviceForm.setCustomerSignatureUrl(
                    cloudinaryService.uploadSignature(
                            customerSignature,
                            "customer-" + serviceForm.getCustomer().getId() + "-" + id));
        }

        if (technicianSignature != null && !technicianSignature.isBlank()) {
            serviceForm.setTechnicianSignatureUrl(
                    cloudinaryService.uploadSignature(
                            technicianSignature,
                            "technician-" + serviceForm.getCustomer().getId() + "-" + id));
        }

        serviceFormRepository.save(serviceForm);
        return mapToResponse(serviceForm);
    }

    @Override
    public Long deleteServiceForm(long id) {
        ServiceForm serviceForm = serviceFormRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Service form not found")));

        cloudinaryService.deleteByUrl(serviceForm.getCustomerSignatureUrl());
        cloudinaryService.deleteByUrl(serviceForm.getTechnicianSignatureUrl());

        serviceFormRepository.deleteById(id);
        return id;
    }

    // 🔥 TEK VE DOĞRU MAPPER
    private ServiceFormResponse mapToResponse(ServiceForm serviceForm) {
        ServiceFormResponse response = new ServiceFormResponse();

        response.setId(serviceForm.getId());
        response.setTitle(serviceForm.getTitle());
        response.setDescription(serviceForm.getDescription());
        response.setCreatedDate(serviceForm.getCreatedDate());

        Customer customer = serviceForm.getCustomer();
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setName(customer.getName());
        customerResponse.setCompany(customer.getCompany());
        customerResponse.setPhone(customer.getPhone());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setAddress(customer.getAddress());

        response.setCustomer(customerResponse);

        response.setCustomerSignatureUrl(serviceForm.getCustomerSignatureUrl());
        response.setTechnicianSignatureUrl(serviceForm.getTechnicianSignatureUrl());

        response.setMachineType(serviceForm.getMachineType());
        response.setMachineSerialNumber(serviceForm.getMachineSerialNumber());
        response.setWorkingHours(serviceForm.getWorkingHours());
        response.setUsedParts(serviceForm.getUsedParts());
        response.setUsedPartQuantities(serviceForm.getUsedPartQuantities());

        return response;
    }



    @Override
    public byte[] generateServiceFormPdf(long id) {
        ServiceForm serviceForm = serviceFormRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Service form not found")));

        Customer customer = serviceForm.getCustomer();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font sectionFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font labelFont = new Font(Font.HELVETICA, 11, Font.BOLD);
            Font valueFont = new Font(Font.HELVETICA, 11);

            // ===== BAŞLIK =====
            Paragraph title = new Paragraph("SERVİS FORMU", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // ===== MÜŞTERİ BİLGİLERİ =====
            document.add(new Paragraph("MÜŞTERİ BİLGİLERİ", sectionFont));
            document.add(Chunk.NEWLINE);

            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);

            addCell(customerTable, "Firma", customer.getCompany(), labelFont, valueFont);
            addCell(customerTable, "Yetkili", customer.getName(), labelFont, valueFont);
            addCell(customerTable, "Telefon", customer.getPhone(), labelFont, valueFont);
            addCell(customerTable, "Email", customer.getEmail(), labelFont, valueFont);
            addCell(customerTable, "Adres", customer.getAddress(), labelFont, valueFont);

            document.add(customerTable);
            document.add(Chunk.NEWLINE);

            // ===== SERVİS BİLGİLERİ =====
            document.add(new Paragraph("SERVİS BİLGİLERİ", sectionFont));
            document.add(Chunk.NEWLINE);

            PdfPTable serviceTable = new PdfPTable(2);
            serviceTable.setWidthPercentage(100);

            addCell(serviceTable, "Başlık", serviceForm.getTitle(), labelFont, valueFont);
            addCell(serviceTable, "Tarih",
                    serviceForm.getCreatedDate().toString(), labelFont, valueFont);
            addCell(serviceTable, "Açıklama",
                    serviceForm.getDescription(), labelFont, valueFont);

            document.add(serviceTable);
            document.add(Chunk.NEWLINE);

            // ===== MAKİNE =====
            if (serviceForm.getMachineType() != null) {
                document.add(new Paragraph("MAKİNE BİLGİLERİ", sectionFont));
                document.add(Chunk.NEWLINE);

                PdfPTable machineTable = new PdfPTable(2);
                machineTable.setWidthPercentage(100);

                addCell(machineTable, "Tür", serviceForm.getMachineType(), labelFont, valueFont);
                addCell(machineTable, "Seri No", serviceForm.getMachineSerialNumber(), labelFont, valueFont);
                addCell(machineTable, "Çalışma Saati",
                        serviceForm.getWorkingHours() + " saat", labelFont, valueFont);

                document.add(machineTable);
                document.add(Chunk.NEWLINE);
            }

            // ===== KULLANILAN MALZEMELER =====
            if (serviceForm.getUsedParts() != null && !serviceForm.getUsedParts().isEmpty()) {
                document.add(new Paragraph("KULLANILAN MALZEMELER", sectionFont));
                document.add(Chunk.NEWLINE);

                PdfPTable partsTable = new PdfPTable(2);
                partsTable.setWidthPercentage(100);

                for (int i = 0; i < serviceForm.getUsedParts().size(); i++) {
                    partsTable.addCell(new PdfPCell(new Phrase(serviceForm.getUsedParts().get(i), valueFont)));
                    partsTable.addCell(new PdfPCell(new Phrase(
                            serviceForm.getUsedPartQuantities().get(i) + " adet", valueFont)));
                }

                document.add(partsTable);
                document.add(Chunk.NEWLINE);
            }

            // ===== İMZALAR =====
            document.add(new Paragraph("İMZALAR", sectionFont));
            document.add(Chunk.NEWLINE);

            PdfPTable signTable = new PdfPTable(2);
            signTable.setWidthPercentage(100);

            addSignature(signTable, "Müşteri İmzası", serviceForm.getCustomerSignatureUrl());
            addSignature(signTable, "Teknisyen İmzası", serviceForm.getTechnicianSignatureUrl());

            document.add(signTable);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(
                    MessageType.GENERAL_EXCEPTION, "PDF oluşturulamadı"));
        }
    }

    private void addCell(PdfPTable table, String label, String value,
                         Font labelFont, Font valueFont) {
        table.addCell(new PdfPCell(new Phrase(label, labelFont)));
        table.addCell(new PdfPCell(new Phrase(value != null ? value : "-", valueFont)));
    }

    private void addSignature(PdfPTable table, String title, String imageUrl) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(new Paragraph(title));

        if (imageUrl != null) {
            try {
                Image img = Image.getInstance(imageUrl);
                img.scaleToFit(200, 100);
                cell.addElement(img);
            } catch (Exception e) {
                cell.addElement(new Paragraph("[İmza yüklenemedi]"));
            }
        } else {
            cell.addElement(new Paragraph("[İmza yok]"));
        }

        table.addCell(cell);
    }


}
