package com.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Library Fine Calculation.
 * Rate: Rs.2/day for the first 7 overdue days, Rs.5/day afterwards, capped at Rs.500.
 */
public class LibraryFine {

    public static final double RATE_FIRST_WEEK = 2.0;
    public static final double RATE_AFTER_WEEK = 5.0;
    public static final double MAX_FINE = 500.0;
    public static final double PREMIUM_DISCOUNT = 0.10;

    // Operation 1: number of days a book is overdue (0 if returned on time)
    public int calculateOverdueDays(LocalDate dueDate, LocalDate returnDate) {
        if (dueDate == null || returnDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        long days = ChronoUnit.DAYS.between(dueDate, returnDate);
        return days > 0 ? (int) days : 0;
    }

    // Operation 2: fine for a number of overdue days
    public double calculateFine(int overdueDays) {
        if (overdueDays < 0) {
            throw new IllegalArgumentException("Overdue days cannot be negative");
        }
        double fine;
        if (overdueDays <= 7) {
            fine = overdueDays * RATE_FIRST_WEEK;
        } else {
            fine = 7 * RATE_FIRST_WEEK + (overdueDays - 7) * RATE_AFTER_WEEK;
        }
        return Math.min(fine, MAX_FINE);
    }

    // Operation 3: 10% discount for premium members
    public double applyMemberDiscount(double fine, boolean isPremiumMember) {
        if (fine < 0) {
            throw new IllegalArgumentException("Fine cannot be negative");
        }
        return isPremiumMember ? fine - (fine * PREMIUM_DISCOUNT) : fine;
    }

    // Operation 4: final payable fine from dates and membership
    public double calculateFinalFine(LocalDate dueDate, LocalDate returnDate, boolean isPremiumMember) {
        int days = calculateOverdueDays(dueDate, returnDate);
        return applyMemberDiscount(calculateFine(days), isPremiumMember);
    }
}
