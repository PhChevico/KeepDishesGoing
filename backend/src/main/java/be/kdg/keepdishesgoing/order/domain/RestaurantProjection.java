package be.kdg.keepdishesgoing.order.domain;


import be.kdg.keepdishesgoing.order.domain.vo.EmailAddress;
import be.kdg.keepdishesgoing.order.domain.vo.Picture;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.domain.vo.StreetAddress;

public class RestaurantProjection {
    private RestaurantId restaurantId;
    private String name;
    private EmailAddress email;
    private StreetAddress address;
    private CuisineTypeProjection cuisineType;
    private int defaultTimePreparation;
    private Picture picture;
    private PriceRangeProjection priceRange;

    public RestaurantProjection(
        RestaurantId restaurantId,
        String name,
        EmailAddress email,
        StreetAddress address,
        CuisineTypeProjection cuisineType,
        int defaultTimePreparation,
        Picture picture,
        PriceRangeProjection priceRange
    ) {
            this.restaurantId = restaurantId;
            this.name = name;
            this.email = email;
            this.address = address;
            this.cuisineType = cuisineType;
            this.defaultTimePreparation = defaultTimePreparation;
            this.picture = picture;
            this.priceRange = priceRange;
    }


    public static RestaurantProjection rehydrate(
            RestaurantId restaurantId,
            String name,
            EmailAddress email,
            StreetAddress address,
            CuisineTypeProjection cuisineType,
            int defaultTimePreparation,
            Picture picture,
            PriceRangeProjection priceRange
    ) {
        return new RestaurantProjection(
                restaurantId,
                name,
                email,
                address,
                cuisineType,
                defaultTimePreparation,
                picture,
                priceRange
        );
    }
    
    public void updatePriceRange(PriceRangeProjection newPriceRange) {
        this.priceRange = newPriceRange;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public StreetAddress getAddress() {
        return address;
    }

    public CuisineTypeProjection getCuisineType() {
        return cuisineType;
    }

    public int getDefaultTimePreparation() {
        return defaultTimePreparation;
    }

    public Picture getPicture() {
        return picture;
    }

    public PriceRangeProjection getPriceRange() {
        return priceRange;
    }
}

