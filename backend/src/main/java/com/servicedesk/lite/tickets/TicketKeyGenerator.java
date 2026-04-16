package com.servicedesk.lite.tickets;

import com.servicedesk.lite.tickets.exceptions.TicketKeyGenerationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TicketKeyGenerator {
    private final JdbcTemplate jdbcTemplate;
    private static final String PREFIX = "SDL";
    private static final int PAD = 10;
    private static final String SQL = "select nextval('ticket_key_seq')";

    public TicketKeyGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Long nextSequenceValue() {
        Long res;
        res = jdbcTemplate.queryForObject(
            SQL,
            Long.class);
        if (res == null) {
            throw new TicketKeyGenerationException("Failed to generate next ticket key sequence value");
        }
        return res;
    }

    public String nextKey() {
        Long seq = nextSequenceValue();
        return PREFIX + String.format("%0" + PAD + "d", seq);
    }

    public String nextKey(String prefix) {
        Long seq = nextSequenceValue();
        return prefix + String.format("%0" + PAD + "d", seq);
    }
}
