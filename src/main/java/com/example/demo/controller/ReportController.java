package com.example.demo.controller;


import com.example.demo.domain.Enum.reportEnum;
import com.example.demo.domain.Report;

import com.example.demo.domain.response.ResReportDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReportService;
import com.example.demo.util.annotation.ApiMessage;
import com.example.demo.util.error.IdInvalidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    @ApiMessage("Create a new report")
    public ResponseEntity<ResReportDTO> createNewReport(@Valid @RequestBody Report report) throws IdInvalidException {
        // Kiểm tra user_sent_report
        if (report.getUser_sent_report() == null || report.getUser_sent_report().getId() == null) {
            throw new IdInvalidException("Người gửi (user_sent_report) không được để trống hoặc không hợp lệ.");
        }
        Long senderId = report.getUser_sent_report().getId();
        if (!userRepository.existsById(senderId)) {
            throw new IdInvalidException("Người gửi (sender_id) không tồn tại trong hệ thống.");
        }

        // Kiểm tra user_received_report
        if (report.getUser_received_report() == null || report.getUser_received_report().getId() == null) {
            throw new IdInvalidException("Người nhận (user_received_report) không được để trống hoặc không hợp lệ.");
        }
        Long receiverId = report.getUser_received_report().getId();
        if (!userRepository.existsById(receiverId)) {
            throw new IdInvalidException("Người nhận (receiver_id) không tồn tại trong hệ thống.");
        }

        // Gọi service để tạo report và trả về DTO
        ResReportDTO newReportDTO = reportService.handleCreateReport(report);

        return ResponseEntity.status(HttpStatus.CREATED).body(newReportDTO);
    }




}
