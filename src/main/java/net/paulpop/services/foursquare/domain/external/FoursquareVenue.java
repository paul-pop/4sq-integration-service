package net.paulpop.services.foursquare.domain.external;

import java.util.List;

/**
 * Created by popp on 14/08/15.
 */
@SuppressWarnings("unused")
public final class FoursquareVenue {

    private String id;
    private String name;
    private String url;
    private FoursquareContact contact;
    private FoursquareLocation location;
    private List<FoursquareCategory> categories;
    private FoursquareHours hours;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public FoursquareContact getContact() {
        return contact;
    }

    public FoursquareLocation getLocation() {
        return location;
    }

    public List<FoursquareCategory> getCategories() {
        return categories;
    }

    public FoursquareHours getHours() {
        return hours;
    }

    public class FoursquareContact {
        private String formattedPhone;

        public String getFormattedPhone() {
            return formattedPhone;
        }
    }

    public class FoursquareLocation {
        private String address;
        private String city;
        private String country;

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }
    }

    public class FoursquareCategory {
        private String name;

        public String getName() {
            return name;
        }
    }

    public class FoursquareHours {
        private boolean isOpen;

        public boolean isOpen() {
            return isOpen;
        }
    }
}
