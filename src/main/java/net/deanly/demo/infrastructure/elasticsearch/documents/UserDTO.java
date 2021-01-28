package net.deanly.demo.infrastructure.elasticsearch.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    @JsonProperty("id")
    public Long id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("email")
    public String email;

    @JsonProperty("gender")
    public String gender;

    @JsonProperty("ip_address")
    public String ipAddress;

    @JsonProperty("married")
    public String married;

    @JsonProperty("car_model")
    public String carModel;

    @JsonProperty("company_name")
    public String companyName;

    @JsonProperty("birthday")
    public String birthday;

    @JsonProperty("payment_currency")
    public String paymentCurrency;

    @JsonProperty("created_at")
    public Date createdAt;

    @JsonProperty("updated_at")
    public Date updatedAt;

}
