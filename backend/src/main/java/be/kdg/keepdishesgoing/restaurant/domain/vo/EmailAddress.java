package be.kdg.keepdishesgoing.restaurant.domain.vo;

import java.util.regex.Pattern;

public record EmailAddress(String emailAddress) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public EmailAddress {
        if (emailAddress == null || emailAddress.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (!EMAIL_PATTERN.matcher(emailAddress).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + emailAddress);
        }
        emailAddress = emailAddress.toLowerCase(); // normalize
    }

    public static EmailAddress of(String emailAddress) {
        return new EmailAddress(emailAddress);
    }
    
}
