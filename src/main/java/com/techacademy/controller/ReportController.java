package com.techacademy.controller;

import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;
    
    
    public ReportController(ReportService service) {
        this.service = service; 
    }
    
    @GetMapping("/top")
    public String getList(Model model) {
        
        model.addAttribute("reportlist", service.getReportList());
        
        return "report/top";
    }
    
    /**
    @GetMapping("reportshow/{id}/")
    public String getReportShow(@PathVariable("id") Integer id, Model model) {
        
        model.addAttribute("employee", service.getReport(id));
        
        return "reportshow";
    }
    
    @GetMapping("reportregister")
    public String getReportRegister() {
        
        return "eportregister";
    }
    
    /**登録画面を表示 
    @GetMapping("reportregister")
    public String getReportRegister(@ModelAttribute Report report) {
        
        return "reportregister";
    }**/

    /** 登録処理 **/
    @PostMapping("/reportregister")
    public String postReportRegister(Report report) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        report.setReportDate(timestamp);
        
        // 登録
        service.saveReport(report);
        // 一覧画面にリダイレクト
        return "redirect:index";
    }
    /**
    @GetMapping("reportupdate/{id}/")
    public String getReport(@PathVariable("id") Integer id, Model model) {
        
        model.addAttribute("report", service.getReport(id));
        
        return "reportupdate";
    }
    
    @PostMapping("/reportupdate/{id}/")
    public String postUpdate(Report report , @PathVariable("id") Integer id) {
        
     // サービス経由で従業員情報を取得
        Report tableReport = service.getReport(id);

        // テーブルから取得した従業員情報へ画面側からの従業員情報の内容を上書き
        tableReport.setTitle(report.getTitle());
        tableReport.setContent(report.getContent());
        
        service.saveReport(tableReport);
        
        return "redirect:/report/index";
    }**/
    
}