package com.example.healthhive.controller;

import com.example.healthhive.DTO.ReportDTO;
import com.example.healthhive.model.Report;
import com.example.healthhive.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitReport(@RequestBody ReportDTO reportDTO) {
        try {
            reportService.submitReport(reportDTO);
            return ResponseEntity.ok("Report submitted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while submitting the report");
        }
    }

    @GetMapping("/userReports")
    public List<ReportDTO> getUserReports() {
        List<ReportDTO> reports = reportService.getReports();
        System.out.println("Reports: " + reports);
        return reports;
    }

}
