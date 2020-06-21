package edu.umich.ropierce.journalservice.repository;

import edu.umich.ropierce.journalservice.dto.JournalDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JournalRepository {

    private JdbcTemplate jdbcTemplate;

    public JournalRepository(DataSource dataSource) throws SQLException {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<JournalDto> getJournalEntries(int tripId) {
        String sql = "select id, tripid, journalDate, contents from journal where tripid = ?";
        BeanPropertyRowMapper<JournalDto> journalDtoBeanPropertyRowMapper = BeanPropertyRowMapper.newInstance(JournalDto.class);
        List<JournalDto> journalDomList = jdbcTemplate.query(sql, new Object[]{tripId}, journalDtoBeanPropertyRowMapper);
        return journalDomList;
    }

    public void addJournalEntry(Integer tripId, String contents) {
        String sql = "insert into journal(tripId, contents) values(?, ?)";
        jdbcTemplate.update(sql, new Object[]{tripId, contents});
    }
}
