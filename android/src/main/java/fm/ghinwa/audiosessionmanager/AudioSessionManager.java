package fm.ghinwa.audiosessionmanager;

import android.bluetooth.BluetoothA2dp;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AudioSessionManager implements HeadphonesConnectionChangeReceiver.HeadphonesConnectionChangedListener{

    private static final String TAG = AudioSessionManager.class.getSimpleName();

    private final Context context;
    private final AudioManager audioManager;

    @Nullable
    private OnPluggedInStatusChangedListener onPluggedInStatusChangedListener;
    @Nullable
    private HeadphonesConnectionChangeReceiver headphonesConnectionChangeReceiver;

    public AudioSessionManager(Context context) {
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public boolean getHeadphonesPluggedInStatus() {
        return audioManager.isWiredHeadsetOn() || audioManager.isBluetoothA2dpOn();
    }

    public void setPluggedInStatusListener(@NonNull OnPluggedInStatusChangedListener onPluggedInStatusChangedListener) {
        this.onPluggedInStatusChangedListener = onPluggedInStatusChangedListener;
        if (headphonesConnectionChangeReceiver == null) {
            Logger.d(TAG, "Register broadcast receiver");
            headphonesConnectionChangeReceiver = new HeadphonesConnectionChangeReceiver(this);
            context.registerReceiver(headphonesConnectionChangeReceiver, new IntentFilter(createReceiverIntentFilter()));
        }
    }

    public void releasePluggedInStatusListener() {
        if (headphonesConnectionChangeReceiver != null) {
            Logger.d(TAG, "Unregister broadcast receiver");
            context.unregisterReceiver(headphonesConnectionChangeReceiver);
            headphonesConnectionChangeReceiver = null;
        }
    }

    @Override
    public void onConnectionChanged() {
        if (onPluggedInStatusChangedListener != null) {
            onPluggedInStatusChangedListener.onPluggedInStatusChanged(getHeadphonesPluggedInStatus());
        }
    }

    private IntentFilter createReceiverIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        intentFilter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);

        return intentFilter;
    }

    public boolean requestAudioFocus() {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        int result = am.requestAudioFocus(
                //// TODO: 20.02.2017  not in scope of mvp, but listener callbacks should be handled
                null,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }


    public interface OnPluggedInStatusChangedListener {

        void onPluggedInStatusChanged(boolean isPluggedInStatus);
    }
}
