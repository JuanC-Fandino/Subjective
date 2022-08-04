package com.jkapps.juancamilo.subjective;


import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import android.content.res.AssetFileDescriptor;
import android.os.Build;


public final class MediaPlayerHolder implements PlayerAdapter {

    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 250;

    private MediaPlayer mediaPlayer;
    private int id;
    private final Context mContext;
    private PlaybackInfoListener pbinfoListener;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;


    public MediaPlayerHolder(Context context){
        mContext = context.getApplicationContext();
    }



    private void initializeMediaPlayer(){
        if (mediaPlayer==null){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopUpdatingCallbackWithPosition(true);
                    pbinfoListener.onStateChanged(PlaybackInfoListener.State.COMPLETED);
                    pbinfoListener.onPlaybackCompleted();

                }
            });

        }
    }

    public void setPlaybackInfoListener(PlaybackInfoListener listener) {
        pbinfoListener = listener;
    }

    @Override
    public void loadMedia(int resourceId) {
        id = resourceId;

        initializeMediaPlayer();

        AssetFileDescriptor assetFileDescriptor =
                mContext.getResources().openRawResourceFd(id);
        try {

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
                mediaPlayer.setDataSource(assetFileDescriptor);
            }else{
                mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
            }

        } catch (Exception e) {

        }

        try {

            mediaPlayer.prepare();
        } catch (Exception e) {

        }

        initializeProgressCallback();
    }

    @Override
    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }



    @Override
    public void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            if (pbinfoListener != null) {
                pbinfoListener.onStateChanged(PlaybackInfoListener.State.PLAYING);
            }
            startUpdatingCallbackWithPosition();
        }
    }

    @Override
    public void reset() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();////
            mediaPlayer.reset();
            //loadMedia(id);
            if (pbinfoListener != null) {
                pbinfoListener.onStateChanged(PlaybackInfoListener.State.RESET);
            }
            stopUpdatingCallbackWithPosition(true);
        }
    }

    @Override
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            if (pbinfoListener != null) {
                pbinfoListener.onStateChanged(PlaybackInfoListener.State.PAUSED);
            }

        }
    }

    @Override
    public void seekTo(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

    private void startUpdatingCallbackWithPosition() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    updateProgressCallbackTask();
                }
            };
        }
        mExecutor.scheduleAtFixedRate(
                mSeekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }


    // Reports media playback position to mPlaybackProgressCallback.
    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition) {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
            if (resetUIPlaybackPosition && pbinfoListener != null) {
                pbinfoListener.onPositionChanged(0);
            }
        }
    }

    private void updateProgressCallbackTask() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            if (pbinfoListener != null) {
                pbinfoListener.onPositionChanged(currentPosition);
            }
        }
    }

    @Override
    public void initializeProgressCallback() {
        final int duration = mediaPlayer.getDuration();
        if (pbinfoListener != null) {
            pbinfoListener.onDurationChanged(duration);
            pbinfoListener.onPositionChanged(0);
        }
    }

}
