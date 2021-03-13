#import "CDVadfurikun.h"

@interface CDVadfurikun ()
@property (nonatomic, strong)ADFmyMovieReward *MovieReward;
@end

@implementation CDVadfurikun

NSString* MOVIE_REWARD_APPID = @"6037731a43f084ba39000008";

- (void)init:(CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    
    //指定した広告枠で動画読み込みを開始
    [ADFmyMovieReward initializeWithAppID:MOVIE_REWARD_APPID];
    //load
    _MovieReward = [ADFmyMovieReward getInstance:MOVIE_REWARD_APPID delegate:self];
    _MovieReward.delegate = self;
    [_MovieReward load];

    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void)load:(CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    
    //アドフリくんの管理画面で発行されたアプリケーションIDを設定してください。
    _MovieReward = [ADFmyMovieReward getInstance:MOVIE_REWARD_APPID delegate:self];
    _MovieReward.delegate = self;
    [_MovieReward load];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void)play:(CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    
    [_MovieReward playWithPresentingViewController:self.viewController];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}


// ------------------------ listener ------------------------
//広告ロード成功時に呼ばれます。
- (void)AdsFetchCompleted:(NSString *)appID isTestMode:(BOOL)isTestMode_inApp {
    NSLog(@"Rewarded Video Did Receive.");
    NSString* js;
    js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%s');", "adfurikun.ready"];
    [self.commandDelegate evalJs:js];
}
//広告ロード失敗時に呼ばれます。
- (void)AdsFetchFailed:(NSString *)appID error:(NSError *)error {
    NSLog(@"Rewarded Video Did Fail to Receive. error: %@", error);
    NSString* js;
    js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%s');", "adfurikun.fail"];
    [self.commandDelegate evalJs:js];
}
//広告表示開始時に呼ばれます。
- (void)AdsDidShow:(NSString *)appID adNetworkKey:(NSString *)adNetworkKey {
    NSString* js;
    js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%s');", "adfurikun.start"];
    [self.commandDelegate evalJs:js];
}
//広告表示失敗時に呼ばれます。
- (void)AdsPlayFailed:(NSString *)appID {
    NSLog(@"AdsPlayFailed");
    NSString* js;
    js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%s');", "adfurikun.fail"];
    [self.commandDelegate evalJs:js];
}
//広告を最後まで視聴した時に呼ばれます。
- (void)AdsDidCompleteShow:(NSString *)appID {
    NSLog(@"AdsDidCompleteShow");
    NSString* js;
    js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%s');", "adfurikun.finish"];
    [self.commandDelegate evalJs:js];
}
//広告を閉じた時に呼ばれます。
- (void)AdsDidHide:(NSString *)appID {
    NSLog(@"AdsDidHide");
    NSString* js;
    js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%s');", "adfurikun.close"];
    [self.commandDelegate evalJs:js];
}


@end

