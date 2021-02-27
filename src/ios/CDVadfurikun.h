#import <Cordova/CDV.h>
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <Cordova/CDVPlugin.h>

#import <ADFMovieReward/ADFmyMovieReward.h>
#import <ADFMovieReward/ADFMovieOptions.h>

@interface CDVadfurikun : CDVPlugin <ADFmyMovieRewardDelegate>

- (void)init:(CDVInvokedUrlCommand *)command;
- (void)load:(CDVInvokedUrlCommand *)command;
- (void)play:(CDVInvokedUrlCommand *)command;

@end
