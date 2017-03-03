package com.disword.diswordlib.core.thread;

import java.util.concurrent.Semaphore;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by disword on 17/3/2.
 */

public class SingleTaskManager {

    private final PriorityFifoBlockingQueue queue = new PriorityFifoBlockingQueue();

    public void queue(QueueTask task){
        queue.add(task);
    }

    private void remove(QueueTask task){
        queue.remove(task);
    }

    public SingleTaskManager(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        QueueTask task = queue.take();
                        final Semaphore semaphore = new Semaphore(0);
                        task.setBlockingSemaphore(semaphore);

                        Observable.just(task)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Action1<QueueTask>() {
                                    @Override
                                    public void call(QueueTask task) {
                                        task.run();
                                    }
                                });
                        semaphore.acquire();
                        remove(task);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
}
