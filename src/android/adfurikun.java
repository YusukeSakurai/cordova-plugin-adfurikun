
package com.yusukesakurai.cordova.adfurikun;

import java.util.TimeZone;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.Settings;

import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieError;
import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieReward;
import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieRewardListener;
import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieType;
import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunSdk;
import jp.tjkapp.adfurikunsdk.moviereward.MovieRewardData;

public class Adfurikun extends CordovaPlugin {

    AdfurikunMovieReward mReward;

    //リスナーを定義
    AdfurikunMovieRewardListener mListener = new AdfurikunMovieRewardListener(){
        @Override
        public void onPrepareSuccess() {
            // 動画の再生が可能になりました。
            // 状態をフラグなどで管理してください
            // webView.loadUrl("javascript:_adfurikun.ready();");
        }

        @Override
        public void onPrepareFailure(AdfurikunMovieError error) {
            // 広告の読み込みが失敗になりました。
            // もう一度広告の読み込みしてください。
        }

        @Override
        public void onStartPlaying(MovieRewardData data) {
            // 動画の再生を開始します。
            // 各アドネットワークが用意したActivityが起動して、動画再生が始まります。
        }

        @Override
        public void onFinishedPlaying(MovieRewardData data) {
            // 動画の再生が完了しました。
            // リワード 付与など、ユーザへの対応を行ってください。
        }

        @Override
        public void onFailedPlaying(MovieRewardData data) {
            // 動画の再生が失敗しました。
            // 再生開始時にネットワークへ接続していない場合も、失敗として通知します。
        }

        @Override
        public void onAdClose(MovieRewardData data) {
            // 動画広告のActivityが終了しました。
            // アプリのActivityが復帰します。
        }
    };



    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {



        if ("init".equals(action)) {
            // 広告枠ID・Activityを指定し、動画リワードのインスタンスを生成
            mReward = new AdfurikunMovieReward("6027f6f143f084766d000031", cordova.getActivity());
            mReward.setAdfurikunMovieRewardListener(mListener);
            mReward.load();

//            JSONObject r = new JSONObject();

//            callbackContext.success("aiueo");
        }
        else if ("play".equals(action)){
            mReward.play();
            return false;
        }
        return true;
    }

}
