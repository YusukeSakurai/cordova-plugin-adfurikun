
package com.yusukesakurai.cordova.adfurikun;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.util.Log;

import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieError;
import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieReward;
import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieRewardListener;
import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieType;
import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunSdk;
import jp.tjkapp.adfurikunsdk.moviereward.MovieRewardData;

public class Adfurikun extends CordovaPlugin  {
    // main activity
    Activity cordovaActivity;
    // webview
    CordovaWebView cordovaWebView;

    // adfurikun instance
    AdfurikunMovieReward mReward;

    // adfurikun リスナーを定義
    AdfurikunMovieRewardListener mListener = new AdfurikunMovieRewardListener(){

        // 動画再生準備完了
        @Override
        public void onPrepareSuccess() {
            String cmd = js("adfurikun.movie.ready");
            cordovaWebView.loadUrl(cmd);
        }
        // 動画再生準備失敗
        @Override
        public void onPrepareFailure(AdfurikunMovieError error) {
            String cmd = js("adfurikun.movie.fail");
            cordovaWebView.loadUrl(cmd);
        }
        // 動画の再生開始
        @Override
        public void onStartPlaying(MovieRewardData data) {
            String cmd = js("adfurikun.movie.start");
            cordovaWebView.loadUrl(cmd);
        }
        // 動画の再生完了
        @Override
        public void onFinishedPlaying(MovieRewardData data) {
            String cmd = js("adfurikun.movie.finish");
            cordovaWebView.loadUrl(cmd);
        }
        // 動画の再生が失敗 再生開始時にネットワークへ接続していない場合も
        @Override
        public void onFailedPlaying(MovieRewardData data) {
            String cmd = js("adfurikun.movie.fail");
            cordovaWebView.loadUrl(cmd);
        }
        // 動画広告のActivityが終了 アプリのActivityが復帰
        @Override
        public void onAdClose(MovieRewardData data) {
            String cmd = js("adfurikun.movie.close");
            cordovaWebView.loadUrl(cmd);
        }
    };

    // init
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        cordovaActivity = cordova.getActivity();
        cordovaWebView = webView;

        mReward = new AdfurikunMovieReward("6027f91f43f084746d00006d", cordovaActivity);
        mReward.setAdfurikunMovieRewardListener(mListener);
        mReward.onResume();
        mReward.load();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("init".equals(action)){
            callbackContext.success();
            return true;
        }
        if ("play".equals(action)){
            mReward.play();
            callbackContext.success();
            return true;
        }
        return false;
    }
    @Override
    public void onResume(boolean b) {
        super.onResume(b);
        if (mReward != null) {
            mReward.onResume();
        }
    }
    @Override
    public void onPause(boolean b) {
        if (mReward != null) {
            mReward.onPause();
        }
        super.onPause(b);
    }
    @Override
    public void onDestroy() {
        if (mReward != null) {
            mReward.onDestroy();
        }
        super.onDestroy();
    }

    public String js(String cmd){
        String res =
            "javascript:cordova.fireDocumentEvent('" +
            cmd+
            "');";
        return res;
    }
}
