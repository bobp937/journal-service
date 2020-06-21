package edu.umich.ropierce.journalservice;

import edu.umich.ropierce.cs571.journal_service.*;
import edu.umich.ropierce.journalservice.bl.JournalBl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class JournalService {
    private static final String NAMESPACE_URI = "http://ropierce.umich.edu/cs571/journal-service";

    private final JournalBl journalBl;

    @Autowired
    public JournalService(JournalBl journalBl) {
        this.journalBl = journalBl;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getJournalRequest")
    @ResponsePayload
    public GetJournalResponse getJournal(@RequestPayload GetJournalRequest getJournalRequest) throws DatatypeConfigurationException {
        GetJournalResponse getJournalResponse = new GetJournalResponse();
        Journal journal = journalBl.getJournal(getJournalRequest.getTripId().intValue());
        getJournalResponse.setJournal(journal);

        return getJournalResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addJournalEntryRequest")
    @ResponsePayload
    public AddJournalEntryResponse addToJournal(@RequestPayload AddJournalEntryRequest addJournalEntryRequest) {
        AddJournalEntryResponse response = new AddJournalEntryResponse();
        Journal journal = journalBl.addJournalEntry(
                addJournalEntryRequest.getJournalAddition().getTripId().intValue(),
                addJournalEntryRequest.getJournalAddition().getContents());

        response.setJournal(journal);

        return response;
    }
}
