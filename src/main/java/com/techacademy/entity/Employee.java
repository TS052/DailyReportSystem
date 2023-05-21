package com.techacademy.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
@Where(clause = "delete_flag = 0")
public class Employee {
    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /** 名前。20桁。null不許可 */
    @Column(length = 20, nullable = false)
    @NotEmpty 
    @Length(max=20)
    private String name;

    /** 削除フラグ。 */
    @Column(name = "delete_flag")
    @NotNull
    private Integer deleteFlag;

    /** 登録日時 */
    @Column(name = "created_at")
    private Timestamp createdAt;
    
    /** 更新日時 */
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Authentication authentication;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Report> reports;

}
