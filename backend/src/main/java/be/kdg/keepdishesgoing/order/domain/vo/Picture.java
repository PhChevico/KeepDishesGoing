package be.kdg.keepdishesgoing.order.domain.vo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public record Picture(String url) {

    public Picture {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("Picture URL cannot be null or blank");
        }

        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + url, e);
        }
    }


    public static Picture of(String url) {
        return new Picture(url);
    }

    @Override
    public String toString() {
        return url;
    }
}
