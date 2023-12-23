package com.example.ressyhomeservice.service.Impl;

import com.example.ressyhomeservice.dto.DoctorDTO;
import com.example.ressyhomeservice.dto.HomeRequestMessage;
import com.example.ressyhomeservice.dto.ResponseModel;
import com.example.ressyhomeservice.dto.UserResponseMessage;
import com.example.ressyhomeservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final RedisTemplate<String, List<DoctorDTO>> redisTemplate;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "user-response-queue")
    public void processUserResponse(UserResponseMessage message) {
        List<DoctorDTO> doctorDTOList = message.getDoctorDTOList();
        redisTemplate.opsForValue().set(message.getProfession(), doctorDTOList, 2, TimeUnit.MINUTES);
    }

//    @Override
//    public ResponseModel<List<DoctorDTO>> getAllDoctors() throws InterruptedException {
//        ResponseModel<List<DoctorDTO>> responseModel = new ResponseModel<>();
//        responseModel.setMessage("Successfully retrieved all doctors");
//        if (redisTemplate.opsForValue().get(allDoctorsKey) == null) {
////            List<DoctorDTO> doctorDTOs = (List<DoctorDTO>) rabbitTemplate.convertSendAndReceive("doctor.exchange", "doctor.routingKey", List.class);
////            redisTemplate.opsForValue().set(allDoctorsKey, doctorDTOs, 5, TimeUnit.MINUTES);
////            responseModel.setData(doctorDTOs);
//            String category = "all";
//            HomeRequestMessage requestMessage = new HomeRequestMessage(category);
//            rabbitTemplate.convertAndSend("user-request-queue", requestMessage, message -> {
//                message.getMessageProperties().setContentType("application/json");
//                return message;
//            });
//            Thread.sleep(3000); //200 seconds
//            List<DoctorDTO> doctorDTOs = redisTemplate.opsForValue().get(allDoctorsKey);
//            responseModel.setData(doctorDTOs);
//            return responseModel;
//        } else {
//            List<DoctorDTO> doctorDTOs = redisTemplate.opsForValue().get(allDoctorsKey);
//            responseModel.setData(doctorDTOs);
//            //
//            for (DoctorDTO doctorDTO : doctorDTOs
//            ) {
//                System.out.println("From redis" + doctorDTO);
//            }
//            //
//        }
//        return responseModel;
//    }

    @Override
    public ResponseModel<List<DoctorDTO>> getDoctorsByProfession(String profession) throws InterruptedException {
        ResponseModel<List<DoctorDTO>> responseModel = new ResponseModel<>();
        responseModel.setMessage("Successfully retrieved doctors");
        if (redisTemplate.opsForValue().get(profession) == null) {
            HomeRequestMessage requestMessage = new HomeRequestMessage(profession);
            rabbitTemplate.convertAndSend("user-request-queue", requestMessage, message -> {
                message.getMessageProperties().setContentType("application/json");
                return message;
            });
            Thread.sleep(900); //1.2 seconds wait
            List<DoctorDTO> doctorDTOs = redisTemplate.opsForValue().get(profession);
            responseModel.setData(doctorDTOs);
            return responseModel;
        } else {
            List<DoctorDTO> doctorDTOs = redisTemplate.opsForValue().get(profession);
            responseModel.setData(doctorDTOs);
        }
        return responseModel;
    }


}
