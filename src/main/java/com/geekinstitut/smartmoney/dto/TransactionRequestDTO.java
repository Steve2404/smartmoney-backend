package com.geekinstitut.smartmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    private UUID categoryId;
    private Double amount;
    private String note;
    private LocalDate date;
}
