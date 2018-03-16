
#import "RNAudioSessionManager.h"

@implementation RNAudioSessionManager {
    bool hasListeners;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

- (instancetype)init
{
    self = [super init];
    if (self) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(routeChange:) name:AVAudioSessionRouteChangeNotification object:nil];
    }

    return self;
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}
- (NSArray<NSString *> *)supportedEvents
{
    return @[@"headphonesStatusChange"];
}
// Will be called when this module's first listener is added.
-(void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}

- (void)routeChange:(NSNotification *)notification
{
    NSLog(@">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    if (hasListeners) {
      [self sendEventWithName:@"headphonesStatusChange" body:@([self headphonesPluggedIn])];
    } else {
        NSLog(@">>>>>>>> [ NO EVENTS REGISTERED ] >>>>>>>>>>>>>>>>");
    }
    
}

RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location withCallback:(RCTResponseSenderBlock)callback)
{
  NSString *result = @"Pretending to create an event ";
  callback(@[[NSNull null], result]);
}

RCT_REMAP_METHOD(getHeadphonesConnected,
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)
{
    BOOL headphones = [self headphonesPluggedIn];
    resolve([NSNumber numberWithBool:headphones]);
}


- (BOOL)headphonesPluggedIn
{
    AVAudioSession *session = [AVAudioSession sharedInstance];
    AVAudioSessionRouteDescription *route = session.currentRoute;

    for (AVAudioSessionPortDescription *output in route.outputs) {
        if ([output.portType isEqual:AVAudioSessionPortHeadphones]
            || [output.portType isEqual:AVAudioSessionPortBluetoothHFP]) {
            return YES;
        }
    }

    return NO;
}

@end
