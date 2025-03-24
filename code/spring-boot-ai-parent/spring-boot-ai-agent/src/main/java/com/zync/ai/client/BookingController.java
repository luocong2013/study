package com.zync.ai.client;


import com.zync.ai.service.BookingTools.BookingDetails;
import com.zync.ai.service.FlightBookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class BookingController {

    private final FlightBookingService flightBookingService;

    public BookingController(FlightBookingService flightBookingService) {
        this.flightBookingService = flightBookingService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/api/bookings")
    @ResponseBody
    public List<BookingDetails> getBookings() {
        return flightBookingService.getBookings();
    }

}
