package edu.umich.ropierce.journalservice.repository;

import edu.umich.ropierce.journalservice.dto.JournalDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JournalRepositoryTest {

    @Autowired
    private JournalRepository journalRepository;

    @Test
    void getJournalEntries() {
        List<JournalDto> journalEntries = journalRepository.getJournalEntries(1);

        assertThat(journalEntries).isNotNull();
        assertThat(journalEntries.size()).isEqualTo(1);
        assertThat(journalEntries.get(0).getContents()).isEqualTo("Weather is beautiful and not too hot!");
        assertThat(journalEntries.get(0).getTripId()).isEqualTo(1);
        assertThat(journalEntries.get(0).getId()).isEqualTo(1);
        assertThat(journalEntries.get(0).getJournalDate()).isEqualTo(LocalDateTime.of(2020,02,16, 07,58,6));
    }

    @Test
    void getJournalEntries_whenNotFound() {
        List<JournalDto> journalEntries = journalRepository.getJournalEntries(9);

        assertThat(journalEntries).isNotNull();
        assertThat(journalEntries.size()).isEqualTo(0);
    }

    @Test
    void addJournalEntry() {
        journalRepository.addJournalEntry(12, "journal junit test");
        List<JournalDto> journalEntries = journalRepository.getJournalEntries(12);

        assertThat(journalEntries).isNotNull();
        assertThat(journalEntries.size()).isEqualTo(1);
        assertThat(journalEntries.get(0).getContents()).isEqualTo("journal junit test");
    }
}