require 'json'

Pod::Spec.new do |s|
  # NPM package specification
  package = JSON.parse(File.read(File.join(File.dirname(__FILE__), 'package.json')))

  s.name           = "RNAudioSessionManager"
  s.version        = package['version']
  s.license        = "MIT"
  s.summary        = "Audio Session Manager library for React Native"
  s.author         = { "Ghinwa dev team" => "dev@ghinwa.com" }
  s.homepage       = "https://github.com/ghinwallc/react-native-audio-session-manager"
  s.source         = { :git => "https://github.com/ghinwallc/react-native-audio-session-manager.git" }
  s.platform       = :ios, "8.0"
  s.preserve_paths = '*.js'
  s.frameworks     = 'AVFoundation'

  s.dependency 'React'

  s.source_files = "ios/*.{h,m}"
end

