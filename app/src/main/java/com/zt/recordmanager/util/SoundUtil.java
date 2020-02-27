package com.zt.recordmanager.util;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;

import com.zt.recordmanager.R;

import java.util.HashMap;

import cn.faker.repaymodel.util.error.ErrorUtil;

public class SoundUtil {
    private Activity activity;

    private AudioManager am;
    private SoundPool soundPool;
    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();

    public SoundUtil(Activity activity) {
        this.activity = activity;
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(activity, R.raw.barcodebeep, 1));
        soundMap.put(2, soundPool.load(activity, R.raw.serror, 1));
    }

    /**
     * 播放提示音
     *
     * @param id 成功1，失败2
     */
    public void playSound(int id) {
        if (am == null) {
            am = (AudioManager) activity.getSystemService(activity.AUDIO_SERVICE);// 实例化AudioManager对象
        }
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);// 返回当前AudioManager对象的音量值
        float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        try {
            soundPool.play(soundMap.get(id), volumnRatio, // 左声道音量
                    volumnRatio, // 右声道音量
                    1, // 优先级，0为最低
                    0, // 循环次数，0无不循环，-1无永远循环
                    1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
            );
        } catch (Exception e) {
            e.printStackTrace();
            ErrorUtil.showError(e);
        }
    }

}
