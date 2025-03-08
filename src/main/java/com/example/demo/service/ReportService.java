package com.example.demo.service;

import com.example.demo.domain.Enum.reportEnum;
import com.example.demo.domain.Report;
import com.example.demo.domain.User;
import com.example.demo.domain.response.ResReportDTO;
import com.example.demo.domain.response.ResUserDTO;
import com.example.demo.repository.ReportRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.error.IdInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    public ResReportDTO handleCreateReport(Report report) {
        if (report.getUser_sent_report() == null || report.getUser_received_report() == null) {
            throw new IllegalArgumentException("Thông tin người gửi hoặc người nhận không được để trống.");
        }
        report.setStatus(reportEnum.PENDING);
        Report savedReport = reportRepository.save(report);
        return convertToReportDTO(savedReport);
    }



    public ResReportDTO convertToReportDTO(Report report) {
        ResReportDTO res = new ResReportDTO();
        res.setId(report.getId());
        res.setStatus(String.valueOf(reportEnum.PENDING));
        res.setReson(report.getReason());
        res.setId_sender(report.getUser_sent_report().getId());
        res.setId_receiver(report.getUser_received_report().getId());
        res.setCreated_at(String.valueOf(report.getCreatedAt()));
        return res;
    }
}