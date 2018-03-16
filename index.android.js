/**
 * @providesModule AudioSessionManager
 * @flow
 */
'use strict'

import { NativeModules, DeviceEventEmitter } from 'react-native'
const AudioSessionManager = NativeModules.AudioSessionManager

class AudioSessionManagerWrapper {
  constructor () {
    this._pluggedInStatusSubscriptions = []
    this._headphonesStatusListener = null
    this._onPluggedInStatusChanged = this._onPluggedInStatusChanged.bind(this)
  }

  _onPluggedInStatusChanged (status) {
    this._pluggedInStatusSubscriptions.forEach((callback) => {
      callback(status)
    })
  }

  /**
   * Adds a subscription for heaphone status changes
   * @param  {(pluggedIn: bool) => void} callback
   * Callback which will be called every time status of the headphones changes.
   * Argument passed to the callback is equal to `true` if headphggones
   * are plugged in, `false` otherwise.
   */
  addPluggedInStatusSubscription (callback: (pluggedIn: bool) => void) {
    if (!this.headphonesStatusListener) {
      this._headphonesStatusListener = DeviceEventEmitter.addListener(
        'headphonesStatus', this._onPluggedInStatusChanged
      )
    }
    this._pluggedInStatusSubscriptions.push(callback)
  }

  /**
   * Removes a subscription for heaphone status changes
   * @param  {(pluggedIn: bool) => void} callback
   * The same callback that was used in the addPluggedInStatusSubscription method
   */
  removePluggedInStatusSubscription (callback: (pluggedIn: bool) => void) {
    this._pluggedInStatusSubscriptions =
      this._pluggedInStatusSubscriptions.filter((x) => (x !== callback))

    if (this._pluggedInStatusSubscriptions.length === 0) {
      this._headphonesStatusListener.remove()

      delete this._headphonesStatusListener
    }
  }

  /**
   * Gets headphones status.
   * @param  {(pluggedIn: bool) => void} callback
   * Callback called with an argument indicating whether headphone are
   * plugged in. Callback's argument is equal to `true` if heaphones
   * are plugged in, `false` otherwise.
   */
  getPluggedInStatus (callback: (pluggedIn: bool) => void) {
    AudioSessionManager.getPluggedInStatus(
      (status) => callback && callback(status)
    )
  }

  /**
   * Activates or deactivates your app’s audio session.
   * @param  {bool} active
   * Use true to activate your app’s audio session, or NO to deactivate it.
   * @param  {(status:any) => void} callback
   * Callback called after method finishes. If an error occurs,
   * an error is passed as an argument of the callback, empty object otherwise.
   */
  setActive (active: bool, callback: (status: any) => void) {
    AudioSessionManager.setActive(
      active, (status) => callback && callback(status)
    )
  }

  unsubscribeFromListening() {
    if (this.pluggedInStatusSubscription) {
      this.pluggedInStatusSubscription.remove()
    }
    if (this.sessionInterruptionSubscription) {
      this.sessionInterruptionSubscription.remove()
    }
  }
}

module.exports = new AudioSessionManagerWrapper()
