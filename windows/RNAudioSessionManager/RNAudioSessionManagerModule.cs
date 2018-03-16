using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Audio.Session.Manager.RNAudioSessionManager
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNAudioSessionManagerModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNAudioSessionManagerModule"/>.
        /// </summary>
        internal RNAudioSessionManagerModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNAudioSessionManager";
            }
        }
    }
}
