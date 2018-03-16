package fm.ghinwa.audiosessionmanager;

import android.bluetooth.BluetoothA2dp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HeadphonesConnectionChangeReceiver extends BroadcastReceiver {

    private static final String TAG = HeadphonesConnectionChangeReceiver.class.getSimpleName();

    private static final String HEADPHONES_STATE = "state";
    private static final int UNKNOWN_STATE = -1;
    private static final int UNPLUGGED = 0;
    private static final int PLUGGED = 1;

    private final HeadphonesConnectionChangedListener listener;

    public HeadphonesConnectionChangeReceiver(HeadphonesConnectionChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d(TAG, "Received intent with action: " + intent.getAction());
        switch (intent.getAction()) {
            case Intent.ACTION_HEADSET_PLUG:
                handleHeadsetPlug(intent.getIntExtra(HEADPHONES_STATE, UNKNOWN_STATE));
                break;
            case BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED:
                handleBluetoothA2dp(intent.getIntExtra(BluetoothA2dp.EXTRA_STATE, UNKNOWN_STATE));
        }
    }

    private void handleHeadsetPlug(int state) {
        switch (state) {
            case UNPLUGGED:
                Logger.d(TAG, "Headset is unplugged");
                listener.onConnectionChanged();
                break;
            case PLUGGED:
                Logger.d(TAG, "Headset is plugged");
                listener.onConnectionChanged();
                break;
            default:
                throw new IllegalStateException("Unknown headset state!");
        }
    }

    private void handleBluetoothA2dp(int state) {
        switch (state) {
            case BluetoothA2dp.STATE_DISCONNECTED:
                Logger.d(TAG, "The profile is in disconnected state");
                listener.onConnectionChanged();
                break;
            case BluetoothA2dp.STATE_CONNECTED:
                Logger.d(TAG, "The profile is in connected state");
                listener.onConnectionChanged();
                break;
            default:
                Logger.d(TAG, "The profile is in state: " + state);
        }
    }

    public interface HeadphonesConnectionChangedListener {
        void onConnectionChanged();
    }
}
