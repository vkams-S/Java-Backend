package com.url.urlshortener.controller;

import com.url.urlshortener.exception.NotFoundException;
import com.url.urlshortener.model.UrlRequest;
import com.url.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class resource {
    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/api/")
    String create(@RequestBody UrlRequest urlRequest){
        return urlShortenerService.createShortUrl(urlRequest);
    }

    @GetMapping("/{encrypted_id}")
    ResponseEntity create(@PathVariable("encrypted_id") String  encryptedId){
        try {
            return ResponseEntity.ok(urlShortenerService.getLongUrl(encryptedId));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //to be triggered by some jobs
    @DeleteMapping("/")
    void delete(){
       urlShortenerService.deleteExpiredUrl();
    }
}
