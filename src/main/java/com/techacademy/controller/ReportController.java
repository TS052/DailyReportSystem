package com.techacademy.controller;

import java.sql.Timestamp;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;
    
    public ReportController(ReportService service) {
        this.service = service; 
    }
    
    @GetMapping("/top")
    public String getList(Model model,@AuthenticationPrincipal UserDetail userDetail) {
        
        model.addAttribute("reportlist", service.getReportEmpList(userDetail.getEmployee()));
        
        return "report/top";    
    }
    
    /**一覧画面を表示 **/
    @GetMapping("/index")
    public String getIndexList(Model model) {
        
        model.addAttribute("reportlist", service.getReportList());
        
        return "report/index";
    }
    
    /**登録画面を表示 **/
    @GetMapping("/reportregister")
    public String getReportRegister(@ModelAttribute Report report,@AuthenticationPrincipal UserDetail userDetail) {
        
        report.setEmployee(userDetail.getEmployee());
        
        return "report/reportregister";
    }

    /** 登録処理 **/
    @PostMapping("/reportregister")
    public String postReportRegister(Report report,@AuthenticationPrincipal UserDetail userDetail) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        report.setEmployee(userDetail.getEmployee().getAuthentication().getEmployee());
        report.setCreatedAt(timestamp);
        report.setUpdatedAt(timestamp);
        
        // 登録
        service.saveReport(report);
        // 一覧画面にリダイレクト
        return "redirect:index";
    }
    
    /** 詳細　**/
    @GetMapping("/reportshow/{id}/")
    public String getReportShow(@PathVariable("id") Integer id, Model model) {
        
        model.addAttribute("report", service.getReport(id));
        
        return "report/reportshow";
    }
    
    /** 更新　**/
    @GetMapping("/reportupdate/{id}/")
    public String getReportupdate(@PathVariable("id") Integer id, Model model) {
        
        model.addAttribute("report", service.getReport(id));
        
        return "report/reportupdate";
    }
                                    
    @PostMapping("/reportupdate/{id}/")
    public String postUpdate(Report report , @PathVariable("id") Integer id) {
        
     // サービス経由で従業員情報を取得
        Report tableReport = service.getReport(id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        // テーブルから取得した従業員情報へ画面側からの従業員情報の内容を上書き
        tableReport.setTitle(report.getTitle());
        tableReport.setContent(report.getContent());
        tableReport.setUpdatedAt(timestamp);
        
        service.saveReport(tableReport);
        
        return "redirect:/report/index";
    }
    
}