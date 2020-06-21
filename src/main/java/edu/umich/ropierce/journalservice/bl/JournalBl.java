package edu.umich.ropierce.journalservice.bl;

import edu.umich.ropierce.cs571.journal_service.Journal;
import edu.umich.ropierce.journalservice.dto.JournalDto;
import edu.umich.ropierce.journalservice.repository.JournalRepository;
import edu.umich.ropierce.journalservice.util.DateConversion;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class JournalBl {

    private final JournalRepository journalRepository;

    public JournalBl(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public Journal getJournal(int tripId){
        List<JournalDto> journalDtoList = journalRepository.getJournalEntries(tripId);

        Journal journal = new Journal();
        journal.setTripId(BigInteger.valueOf(tripId));
        journal.setContents("");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy hh:mm:ss a");

        for (JournalDto journalDto : journalDtoList){
            journal.setContents(journal.getContents() + dateTimeFormatter.format(journalDto.getJournalDate()) + System.lineSeparator() + journalDto.getContents() + System.lineSeparator()  + System.lineSeparator() );
            if (journal.getCreationDate() == null) {
                journal.setCreationDate(DateConversion.getXMLDate(journalDto.getJournalDate()));
            }
            journal.setLastUpdateDate(DateConversion.getXMLDate(journalDto.getJournalDate()));
        }

        journal.setContents(journal.getContents().trim());

        return journal;
    }

    public Journal addJournalEntry(int tripId, String contents) {
        journalRepository.addJournalEntry(tripId, contents);
        return getJournal(tripId);
    }
}
