package com.techacademy.controller;

import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    public EmployeeController(EmployeeService service) {
        this.service = service; 
    }
    
    @GetMapping("/list")
    public String getList(Model model) {
        
        model.addAttribute("employeelist", service.getEmployeeList());
        
        return "employee/list";
    }
    
    @GetMapping("/show/{id}/")
    public String getShow(@PathVariable("id") Integer id, Model model) {
        
        model.addAttribute("employee", service.getEmployee(id));
        
        return "Employee/show";
    }
    
    /**登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {
        
        return "employee/register";
    }

    /** 登録処理 */
    @PostMapping("/register")
    public String postRegister(Employee employee,Authentication authentication) {
        employee.getAuthentication().setEmployee(employee);
        employee.setDeleteFlag(0);
        employee.setCreatedAt(timestamp);
        employee.setUpdatedAt(timestamp);
        // 登録
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }
    
    @GetMapping("/update/{id}/")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {
        
        model.addAttribute("employee", service.getEmployee(id));
        
        return "employee/update";
    }
    
    @PostMapping("/update/{id}/")
    public String postUpdate(Employee employee , @PathVariable("id") Integer id) {
        
     // サービス経由で従業員情報を取得
        Employee tableEmployee = service.getEmployee(id);

        // テーブルから取得した従業員情報へ画面側からの従業員情報の内容を上書き
        tableEmployee.setName(employee.getName());
        tableEmployee.setUpdatedAt(employee.getUpdatedAt());
        tableEmployee.getAuthentication().setPassword(employee.getAuthentication().getPassword());
        tableEmployee.getAuthentication().setRole(employee.getAuthentication().getRole());
        
        service.saveEmployee(tableEmployee);
        
        return "redirect:/employee/list";
    }
    
    /** 削除処理 */
    @PostMapping("/delete/{id}/")
    public String postDelete(Employee employee, @PathVariable("id") Integer id) {
        // サービス経由で従業員情報を取得
        Employee tableEmployee = service.getEmployee(id);
        
        tableEmployee.setDeleteFlag(1);
        tableEmployee.setUpdatedAt(employee.getUpdatedAt());
        
        service.saveEmployee(employee);
        
        return "redirect:/employee/list";
    }
}