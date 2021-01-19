package net.deanly.structure.search.infrastructure.jpa.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "users")
public class UserDTO {
    @Id
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "married")
    private Boolean married;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
