package fm.ghinwa.audiosessionmanager;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class AudioSessionManagerModule extends ReactContextBaseJavaModule
        implements AudioSessionManager.OnPluggedInStatusChangedListener {

    private static final String MODULE_NAME = "AudioSessionManager";

    private static final String HEADPHONES_STATUS_EVENT_NAME = "headphonesStatus";
    private static final String ERROR_CANNOT_ACTIVATE_SESSION = "Cannot activate session";

    private final AudioSessionManager audioSessionManager;

    public AudioSessionManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        audioSessionManager = new AudioSessionManager(reactContext);
    }

    @Override
    public void initialize() {
        super.initialize();
        audioSessionManager.setPluggedInStatusListener(this);
    }

    @ReactMethod
    public void getPluggedInStatus(Callback pluggedInCallback) {
        pluggedInCallback.invoke(audioSessionManager.getHeadphonesPluggedInStatus());
    }

    /**
     * This will acquire audio focus for app, meaning that all apps currently playing sounds needs to stop.
     */
    @ReactMethod
    public void setActive(boolean status, Callback callback) {
        boolean success = audioSessionManager.requestAudioFocus();
        callback.invoke(success ? null : ERROR_CANNOT_ACTIVATE_SESSION);
    }

    @ReactMethod
    public void unsubscribeFromListening() {
        audioSessionManager.releasePluggedInStatusListener();
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public void onPluggedInStatusChanged(boolean isPluggedInStatus) {
        sendEvent(HEADPHONES_STATUS_EVENT_NAME, isPluggedInStatus);
    }

    /**
     * Use WritableMap for sending event with multiple parameters.
     */
    private void sendEvent(String eventName, @Nullable Object object) {
        getReactApplicationContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, object);
    }
}
