package com.enesderin.FixTrackBackend.service;

public interface CloudinaryService {

    String uploadSignature(String base64Signature, String publicIdPrefix);

    void deleteByUrl(String imageUrl);
}

