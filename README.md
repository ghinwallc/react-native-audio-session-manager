# Work in progress.
We can't recommend you use this module in production yet.

# react-native-audio-session-manager

## Getting started

`$ npm install @ghinwa/react-native-audio-session-manager --save`

### Mostly automatic installation

`$ react-native link @ghinwa/react-native-audio-session-manager`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-audio-session-manager` and add `RNAudioSessionManager.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNAudioSessionManager.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNAudioSessionManagerPackage;` to the imports at the top of the file
  - Add `new RNAudioSessionManagerPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-audio-session-manager'
  	project(':react-native-audio-session-manager').projectDir = new File(rootProject.projectDir, 	'../node_modules/@ghinwa/react-native-audio-session-manager/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-audio-session-manager')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNAudioSessionManager.sln` in `node_modules/react-native-audio-session-manager/windows/RNAudioSessionManager.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Audio.Session.Manager.RNAudioSessionManager;` to the usings at the top of the file
  - Add `new RNAudioSessionManagerPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNAudioSessionManager from 'react-native-audio-session-manager';

RNAudioSessionManager;
```
## API
- `getHeadphonesConnected` returns promise (bool)
returns a promise with value `true` when the headphones are connected and `false` otherwise.
On **ios** it uses `AVAudioSessionPortHeadphones` and `AVAudioSessionPortBluetoothHFP` to resolve if headphones are connected.

- `onHeadphonesStatusChange(callback)`
Executes callback everytime the headphones state changes
```
onHeadphonesStatusChange((connected) => {
	if (connected) {
		//
	}
})
```
