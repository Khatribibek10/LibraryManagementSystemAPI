package com.example.lms.dto.response.solidiare;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankDTOResponse {
        private Long id;
        private String bankCode;
        private String bankName;
        private String bankNameLocal;
        private String swiftCode;
        private String bankAbbreviationName;
        private Integer countryId;
        private String countryName;
        private String bankLocation;
        private String effectiveFrom;
        private String effectiveTo;
        private String statusType;
        private String headOfficeId;
        private String bankLogo;
        private String bankType;
        private String contactEmail;
        private String contactNumber;
}
