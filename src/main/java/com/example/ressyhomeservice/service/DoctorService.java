package com.example.ressyhomeservice.service;

import com.example.ressyhomeservice.dto.DoctorDTO;
import com.example.ressyhomeservice.dto.ResponseModel;
import com.example.ressyhomeservice.dto.UserResponseMessage;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public interface DoctorService {
     void processUserResponse(UserResponseMessage message);
     ResponseModel<List<DoctorDTO>> getDoctorsByProfession(String profession) throws InterruptedException;
}
