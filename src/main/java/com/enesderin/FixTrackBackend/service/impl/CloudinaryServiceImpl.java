package com.enesderin.FixTrackBackend.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.enesderin.FixTrackBackend.exception.ErrorMessage;
import com.enesderin.FixTrackBackend.exception.MessageType;
import com.enesderin.FixTrackBackend.exception.handler.BaseException;
import com.enesderin.FixTrackBackend.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadSignature(String base64Signature, String publicIdPrefix) {
        if (base64Signature == null || base64Signature.isBlank()) {
            return null;
        }
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    base64Signature,
                    ObjectUtils.asMap(
                            "folder", "fixtrack/signatures",
                            "public_id", publicIdPrefix + "-" + UUID.randomUUID(),
                            "overwrite", true,
                            "resource_type", "image"
                    )
            );
            Object secureUrl = uploadResult.get("secure_url");
            return secureUrl == null ? null : secureUrl.toString();
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Signature upload failed"));
        }
    }

    @Override
    public void deleteByUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }
        try {
            String publicId = extractPublicIdFromUrl(imageUrl);
            if (publicId == null || publicId.isBlank()) {
                return;
            }
            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap(
                            "resource_type", "image"
                    )
            );
        } catch (Exception e) {
            // Cloudinary silme hatasını loglayıp yutuyoruz, ana akışı bozmayalım
            System.err.println("Cloudinary delete failed for url: " + imageUrl + " -> " + e.getMessage());
        }
    }

    /**
     * Örnek URL:
     * https://res.cloudinary.com/<cloud>/image/upload/v123456789/fixtrack/signatures/customer-1-uuid.png
     * Dönen publicId: fixtrack/signatures/customer-1-uuid
     */
    private String extractPublicIdFromUrl(String url) {
        try {
            int uploadIndex = url.indexOf("/upload/");
            if (uploadIndex == -1) {
                return null;
            }
            String afterUpload = url.substring(uploadIndex + "/upload/".length());
            String[] parts = afterUpload.split("/");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                // Versiyon bilgisini (v12345) atla
                if (i == 0 && part.matches("v\\d+")) {
                    continue;
                }
                if (sb.length() > 0) {
                    sb.append("/");
                }
                sb.append(part);
            }
            String path = sb.toString();
            // Uzantıyı kaldır (.png, .jpg, .jpeg, .webp vs.)
            int dotIndex = path.lastIndexOf('.');
            if (dotIndex != -1) {
                path = path.substring(0, dotIndex);
            }
            return path;
        } catch (Exception e) {
            return null;
        }
    }
}

