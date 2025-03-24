package com.zync.ai.service;

import com.zync.ai.data.*;
import com.zync.ai.service.BookingTools.BookingDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 17:16
 **/
@Service
public class FlightBookingService {

    private final BookingData db;

    public FlightBookingService() {
        this.db = new BookingData();
        initDemoData();
    }

    private void initDemoData() {
        List<String> names = List.of("刘备", "关羽", "张飞", "诸葛亮", "赵子龙");
        List<String> airportCodes = List.of("北京", "上海", "广州", "深圳", "杭州", "南京", "青岛", "成都", "武汉", "西安", "重庆", "大连", "天津");

        var size = airportCodes.size();
        var random = new Random();
        var customers = new ArrayList<Customer>();
        var bookings = new ArrayList<Booking>();

        for (int i = 0, count = names.size(); i < count; i++) {
            String name = names.get(i);
            String from = airportCodes.get(random.nextInt(size));
            String to = airportCodes.get(random.nextInt(size));
            BookingClass bookingClass = BookingClass.values()[random.nextInt(BookingClass.values().length)];

            Customer customer = new Customer(name);
            LocalDate date = LocalDate.now().plusDays(2 * (i + 1));
            Booking booking = new Booking("10" + (i + 1), date, customer, BookingStatus.CONFIRMED, from, to, bookingClass);

            customer.getBookings().add(booking);

            customers.add(customer);
            bookings.add(booking);
        }

        db.setCustomers(customers);
        db.setBookings(bookings);
    }

    public List<BookingDetails> getBookings() {
        return db.getBookings().stream().map(this::toBookingDetails).toList();
    }

    public BookingDetails getBookingDetails(String bookingNumber, String name) {
        var booking = findBooking(bookingNumber, name);
        return toBookingDetails(booking);
    }

    public void changeBooking(String bookingNumber, String name, String newDate, String from, String to) {
        var booking = findBooking(bookingNumber, name);
        if (booking.getDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("Booking cannot be changed within 24 hours of the start date.");
        }
        booking.setDate(LocalDate.parse(newDate));
        booking.setFrom(from);
        booking.setTo(to);
    }

    public void cancelBooking(String bookingNumber, String name) {
        var booking = findBooking(bookingNumber, name);
        if (booking.getDate().isBefore(LocalDate.now().plusDays(2))) {
            throw new IllegalArgumentException("Booking cannot be cancelled within 48 hours of the start date.");
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
    }

    private Booking findBooking(String bookingNumber, String name) {
        return db.getBookings()
                .stream()
                .filter(b -> b.getBookingNumber().equalsIgnoreCase(bookingNumber))
                .filter(b -> b.getCustomer().getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }


    private BookingDetails toBookingDetails(Booking booking) {
        return new BookingDetails(booking.getBookingNumber(), booking.getCustomer().getName(), booking.getDate(),
                booking.getBookingStatus(), booking.getFrom(), booking.getTo(), booking.getBookingClass().toString());
    }

}
