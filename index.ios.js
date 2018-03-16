
import { NativeModules, NativeEventEmitter } from 'react-native';

const { RNAudioSessionManager } = NativeModules;
// const subscribedToOnHeadphonesChange = false
export const onHeadphonesChange = (callback) => {
  // if (subscribedToOnHeadphonesChange) return
  // subscribedToOnHeadphonesChange = true
  const eventEmmiter = new NativeEventEmitter(RNAudioSessionManager)
  console.log('add event listener')
  const subscription = eventEmmiter.addListener(
    'headphonesStatusChange',
    (status) => {
      console.log('status is ', status)
      callback(status)
    }
  );
}

export default RNAudioSessionManager;
