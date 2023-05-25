package com.techacademy.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    
    
    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    /* 全件を検索して返す
    public List<Report> getReportList() {
        // リポジトリのfindAllメソッドを呼び出す
        return reportRepository.findAll();
    }*/
    
    public List<Report> getReportList(Employee employee) {
 
        return reportRepository.findByEmployee(employee);
    }
    
    public Report getReport(Integer id) {
        return reportRepository.findById(id).get();
    }
    
    @Transactional
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }
    
    
}
