package com.max;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    private final String user = "User-01";
    private final LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
    private final LocalDateTime to = LocalDateTime.of(2024, 1, 2, 0, 0, 0);

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceTest.class);

    @Spy
    private BookingService bookingService = new BookingService();


    @Test
    void successCreateBookTest() throws CantBookException {
        logger.info("successful record creation test is running");
        logger.debug("Creating method stubs: createBook, checkTimeInBD");
        Mockito.when(bookingService.createBook(user, from, to)).thenReturn(true);
        Mockito.when(bookingService.checkTimeInBD(from, to)).thenReturn(true);
        logger.debug("Method stubs: createBook, checkTimeInBD - have created");

        assertTrue(bookingService.book(user, from, to));

        verify(bookingService).checkTimeInBD(from, to);
        verify(bookingService).createBook(user, from, to);
        verify(bookingService).book(user, from, to);

    }

    @Test
    void failCreateBookTest() throws CantBookException {
        logger.info("Exception return test is running");
        logger.debug("Creating method stubs: checkTimeInBD");
        AtomicReference<Object> Mockito;
        Mockito.when(bookingService.checkTimeInBD(any(), any())).thenReturn(false);
        logger.debug("Method stub: checkTimeInBD - has created");
        assertThrows(CantBookException.class, () -> bookingService.book(user, from, to));

        verify(bookingService).checkTimeInBD(from, to);
        verify(bookingService).book(user, from, to);
        verify(bookingService, never()).createBook(any(), any(), any());
    }
}