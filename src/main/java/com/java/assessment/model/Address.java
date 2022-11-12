package com.java.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) // So we do not serialise/print properties with empty values
public class Address {
    long id;
    AddressType type;
    AddressLineDetail addressLineDetail;
    ProvinceOrState provinceOrState;
    String cityOrTown;
    Country country;
    String postalCode;
    String suburbOrDistrict;
    LocalDateTime lastUpdated;
}
