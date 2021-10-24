package com.url.urlshortener.service;

import com.url.urlshortener.exception.NotFoundException;
import com.url.urlshortener.model.UrlRequest;

import java.io.UnsupportedEncodingException;

public interface UrlShortenerService {
    String createShortUrl(UrlRequest urlRequest);
    String getLongUrl(String encryptedId) throws UnsupportedEncodingException, NotFoundException;
    void deleteExpiredUrl();
}
