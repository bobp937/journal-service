package edu.umich.ropierce.journalservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalDto {
    private int id;
    private int tripId;
    private LocalDateTime journalDate;
    private String contents;
}
