package com.douwe.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Entity
@XmlRootElement(name = "evaluation")
@XmlAccessorType(XmlAccessType.FIELD)

public class Evaluation implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    @XmlTransient
    private int version;
    
    @Column(unique = true)
    private String code;
    
    @Column (columnDefinition = "boolean default false")
    private boolean isExam;
    
    @Column
    private String description;
    
     @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
    
    public Evaluation(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public int getVersion() {
        return version;
    }

    @JsonIgnore
    public void setVersion(int version) {
        this.version = version;
    }

    @JsonIgnore
    public int getActive() {
        return active;
    }

    @JsonIgnore
    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Evaluation{" + "id=" + id + ", version=" + version + ", code=" + code + ", description=" + description + ", active=" + active + '}';
    }

    public boolean isIsExam() {
        return isExam;
    }

    public void setIsExam(boolean isExam) {
        this.isExam = isExam;
    }
    
    
    
    
}
