package com.disword.diswordlib.core.thread;

import java.util.concurrent.Semaphore;

/**
 * Created by disword on 17/3/2.
 */

public abstract class QueueTask implements Runnable, Comparable<QueueTask> {
    private Semaphore semaphore;

    @Override
    public void run() {
        try {
            protectRun();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            if (semaphore != null)
                semaphore.release();
        }
    }

    public abstract void protectRun() throws Throwable;

    public void setBlockingSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public int compareTo(QueueTask another) {
        return another.definedPriority().priority - definedPriority().priority;
    }

    protected Priority definedPriority() {
        return Priority.NORMAL;
    }

    public static class Priority {

        public static final Priority HIGH = new Priority(100);
        public static final Priority NORMAL = new Priority(50);
        public static final Priority LOW = new Priority(0);
        private final int priority;

        private Priority(int priority) {

            this.priority = priority;
        }
    }
}
