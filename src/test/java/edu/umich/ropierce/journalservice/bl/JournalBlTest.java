package edu.umich.ropierce.journalservice.bl;

import edu.umich.ropierce.cs571.journal_service.Journal;
import edu.umich.ropierce.journalservice.dto.JournalDto;
import edu.umich.ropierce.journalservice.repository.JournalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class JournalBlTest {

    @MockBean
    private JournalRepository journalRepository;

    @Autowired
    private JournalBl journalBl;

    @Test
    void getJournal_returnsNonNullResult() {
        when(journalRepository.getJournalEntries(anyInt())).thenReturn(List.of(JournalDto.builder().journalDate(LocalDateTime.of(2020,1,3,15,22,35)).build()));

        Journal journal = journalBl.getJournal(3);
        assertThat(journal).isNotNull();
    }

    @Test
    void getJournal() {
        when(journalRepository.getJournalEntries(anyInt())).thenReturn(
                List.of(
                        JournalDto.builder()
                                .id(22)
                                .tripId(3)
                                .journalDate(LocalDateTime.of(2020,1,3,15,22,35))
                                .journalDate(LocalDateTime.of(2020,1,3,15,22,35))
                                .contents("My journal Contents")
                                .build()));

        Journal journal = journalBl.getJournal(3);
        assertThat(journal).isNotNull();
        assertThat(journal.getTripId()).isEqualTo(3);
        assertThat(journal.getContents()).isEqualTo("1/3/2020 03:22:35 PM" + System.lineSeparator() + "My journal Contents");

        assertThat(journal.getCreationDate()).isNotNull();
        GregorianCalendar creationDate = journal.getCreationDate().toGregorianCalendar();
        assertThat(creationDate.get(Calendar.YEAR)).isEqualTo(2020);
        assertThat(creationDate.get(Calendar.MONTH)).isEqualTo(Calendar.JANUARY);
        assertThat(creationDate.get(Calendar.DAY_OF_MONTH)).isEqualTo(3);
        assertThat(creationDate.get(Calendar.HOUR_OF_DAY)).isEqualTo(15);
        assertThat(creationDate.get(Calendar.MINUTE)).isEqualTo(22);
        assertThat(creationDate.get(Calendar.SECOND)).isEqualTo(35);

        assertThat(journal.getLastUpdateDate()).isNotNull();
        GregorianCalendar lastUpdateDate = journal.getLastUpdateDate().toGregorianCalendar();
        assertThat(lastUpdateDate.get(Calendar.YEAR)).isEqualTo(2020);
        assertThat(lastUpdateDate.get(Calendar.MONTH)).isEqualTo(Calendar.JANUARY);
        assertThat(lastUpdateDate.get(Calendar.DAY_OF_MONTH)).isEqualTo(3);
        assertThat(lastUpdateDate.get(Calendar.HOUR_OF_DAY)).isEqualTo(15);
        assertThat(lastUpdateDate.get(Calendar.MINUTE)).isEqualTo(22);
        assertThat(lastUpdateDate.get(Calendar.SECOND)).isEqualTo(35);
    }

    @Test
    void getJournal_withMultipleEntries() {
        when(journalRepository.getJournalEntries(anyInt())).thenReturn(
                List.of(
                        JournalDto.builder()
                                .id(22)
                                .tripId(3)
                                .journalDate(LocalDateTime.of(2020,1,3,15,22,35))
                                .contents("My journal Contents")
                                .build(),
                        JournalDto.builder()
                                .id(22)
                                .tripId(3)
                                .journalDate(LocalDateTime.of(2020,2,12,1,33,0))
                                .contents("My 2nd journal Contents")
                                .build()
                )
        );

        Journal journal = journalBl.getJournal(3);
        assertThat(journal).isNotNull();
        assertThat(journal.getTripId()).isEqualTo(3);
        assertThat(journal.getContents()).isEqualTo("1/3/2020 03:22:35 PM" + System.lineSeparator() + "My journal Contents" + System.lineSeparator() + System.lineSeparator() +
                                                    "2/12/2020 01:33:00 AM" + System.lineSeparator() + "My 2nd journal Contents");

        assertThat(journal.getCreationDate()).isNotNull();
        GregorianCalendar creationDate = journal.getCreationDate().toGregorianCalendar();
        assertThat(creationDate.get(Calendar.YEAR)).isEqualTo(2020);
        assertThat(creationDate.get(Calendar.MONTH)).isEqualTo(Calendar.JANUARY);
        assertThat(creationDate.get(Calendar.DAY_OF_MONTH)).isEqualTo(3);
        assertThat(creationDate.get(Calendar.HOUR_OF_DAY)).isEqualTo(15);
        assertThat(creationDate.get(Calendar.MINUTE)).isEqualTo(22);
        assertThat(creationDate.get(Calendar.SECOND)).isEqualTo(35);

        assertThat(journal.getLastUpdateDate()).isNotNull();
        GregorianCalendar lastUpdateDate = journal.getLastUpdateDate().toGregorianCalendar();
        assertThat(lastUpdateDate.get(Calendar.YEAR)).isEqualTo(2020);
        assertThat(lastUpdateDate.get(Calendar.MONTH)).isEqualTo(Calendar.FEBRUARY);
        assertThat(lastUpdateDate.get(Calendar.DAY_OF_MONTH)).isEqualTo(12);
        assertThat(lastUpdateDate.get(Calendar.HOUR_OF_DAY)).isEqualTo(1);
        assertThat(lastUpdateDate.get(Calendar.MINUTE)).isEqualTo(33);
        assertThat(lastUpdateDate.get(Calendar.SECOND)).isEqualTo(0);
    }

    @Test
    public void addJournalEntry_valid(){
        when(journalRepository.getJournalEntries(3)).thenReturn(
                List.of(
                        JournalDto.builder()
                                .id(22)
                                .tripId(3)
                                .journalDate(LocalDateTime.of(2020,1,3,15,22,35))
                                .journalDate(LocalDateTime.of(2020,1,3,15,22,35))
                                .contents("Really Fun Time!")
                                .build()));
        Journal journal = journalBl.addJournalEntry(3, "Really Fun Time!");
        verify(journalRepository, times(1)).addJournalEntry(3, "Really Fun Time!");
        assertThat(journal).isNotNull();
    }

}