package fm.ghinwa.audiosessionmanager;

import android.util.Log;

public final class Logger {

    private static final boolean LOGGING_ENABLED = false;

    private Logger() {
        throw new AssertionError();
    }

    static void d(String tag, String message) {
        if (LOGGING_ENABLED) {
            Log.d(tag, message);
        }
    }
}
