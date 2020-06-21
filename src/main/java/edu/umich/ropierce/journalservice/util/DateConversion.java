package edu.umich.ropierce.journalservice.util;

import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

@Slf4j
public class DateConversion {
    public static XMLGregorianCalendar getXMLDate(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        GregorianCalendar creationGregorianCalendar = GregorianCalendar.from(zonedDateTime);
        XMLGregorianCalendar xmlGregorianCalendar;
        try {
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(creationGregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            log.error("Unable to convert GregorianCalendar to XmlGregorianCalendar. LocalDateTime: {}", localDateTime);
            throw new RuntimeException("Unable to convert GregorianCalendar to XmlGregorianCalendar", e);
        }
        return xmlGregorianCalendar;
    }
}
