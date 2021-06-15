package um5.ssi.traininggps;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "way_points";
        public static final String COLUMN_NAME_ENTRY_ID = "location_id";
        public static final String COLUMN_NAME_TITLE = "location_title";
        public static final String COLUMN_NAME_LATITUDE = "lat";
        public static final String COLUMN_NAME_LONGTITUDE = "lon";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";

    }
}