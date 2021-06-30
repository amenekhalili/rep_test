package ir.fararayaneh.erp.commons;

/**
 * این کلاس برای تعیین حالت های مختلف سینک کردن دیتا استفاده میشود
 * به کلاس
 * CommonActivityState
 * هم توجه شود
 * <p>
 * 1- faghat radif haii baraye sync ersal shavad ke state anha : BEFORE_SYNC
 * 2- agar state = syncError bashad, karbar betavanad anra edit namayad , pas az on ==> stat = BEFORE_SYNC
 * 3- agar state = ACCESS_DENIED bashad, karbar ejaze virayesh va sync mojadad nadarad
 * 4- agar state = ON_SYNC bashad, karbar ejaze virayesh nadarad ta zamani ke taklif state moshakhas shode va be state synced, ya syncError, ya accessDenied beresim
 */
    public class CommonSyncState {
        public static final String BEFORE_SYNC = "beforeSync";
        public static final String ON_SYNC = "onSync";
        public static final String SYNCED = "synced";
        public static final String SYNC_ERROR = "syncError";
        public static final String ACCESS_DENIED = "accessDenied";
    }
