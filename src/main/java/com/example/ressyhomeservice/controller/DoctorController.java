package com.example.ressyhomeservice.controller;

import com.example.ressyhomeservice.dto.DoctorDTO;
import com.example.ressyhomeservice.dto.ResponseModel;
import com.example.ressyhomeservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("doctor")
public class DoctorController {
    private final DoctorService doctorService;
    @GetMapping("/all")
    public ResponseEntity<ResponseModel<List<DoctorDTO>>> getAllDoctors() throws InterruptedException {
        return new ResponseEntity<>(doctorService.getDoctorsByProfession("all"), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ResponseModel<List<DoctorDTO>>> getDoctorsByProfession(@RequestParam("profession") String profession) throws InterruptedException {
        return new ResponseEntity<>(doctorService.getDoctorsByProfession(profession), HttpStatus.OK);
    }
}