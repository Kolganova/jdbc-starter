package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.dao.TicketDao;
import com.dmdev.jdbc.starter.entity.Ticket;

import java.math.BigDecimal;
import java.util.Optional;

public class DaoRunner {

    public static void main(String[] args) {
        updateTest();

        TicketDao ticketDao = TicketDao.getInstance();
        System.out.println(ticketDao.findAll());
    }

    private static void updateTest() {
        TicketDao ticketDao = TicketDao.getInstance();
        Optional<Ticket> maybeTicket = ticketDao.find_by_id(2L);
        System.out.println(maybeTicket);

        maybeTicket.ifPresent( ticket -> {
            ticket.setCost(BigDecimal.valueOf(1288.88));
            ticketDao.update(ticket);
        });
    }

    private static void saveTest() {
        TicketDao ticketDao = TicketDao.getInstance();
        Ticket ticket = new Ticket();
        ticket.setPassengerNo("1234507");
        ticket.setPassengerName("Test");
        ticket.setFlightId(3L);
        ticket.setSeatNo("A3");
        ticket.setCost(BigDecimal.TEN);
        Ticket savedTicket = ticketDao.save(ticket);
        System.out.println(savedTicket);
    }


}
