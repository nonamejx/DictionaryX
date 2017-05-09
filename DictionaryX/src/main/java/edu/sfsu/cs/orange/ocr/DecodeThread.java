package edu.sfsu.cs.orange.ocr;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.CountDownLatch;

final class DecodeThread extends Thread {

    private final CaptureActivity activity;
    private final CountDownLatch handlerInitLatch;
    private Handler handler;

    DecodeThread(CaptureActivity activity) {
        this.activity = activity;
        handlerInitLatch = new CountDownLatch(1);
    }

    Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(activity);
        handlerInitLatch.countDown();
        Looper.loop();
    }
}