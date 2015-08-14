package net.paulpop.services.foursquare.domain.external;

import java.util.List;

/**
 * Mapped domain from Foursquare's JSON structure. Every class should be final as we don't want to be able to
 * extend it. Also making use of inner class to compact the code as we don't have to worry about unit testing this.
 *
 * Created by popp on 14/08/15.
 */
@SuppressWarnings("unused")
public final class FoursquareRoot {

    private FoursquareMeta meta;
    private FoursquareResponse response;

    public FoursquareMeta getMeta() {
        return meta;
    }

    public FoursquareResponse getResponse() {
        return response;
    }

    public class FoursquareMeta {
        private Integer code;
        private String errorType;
        private String errorDetail;

        public Integer getCode() {
            return code;
        }

        public String getErrorType() {
            return errorType;
        }

        public String getErrorDetail() {
            return errorDetail;
        }
    }

    public class FoursquareResponse {
        private List<FoursquareGroup> groups;

        public List<FoursquareGroup> getGroups() {
            return groups;
        }
    }
}
