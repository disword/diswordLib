package com.disword.diswordlib.core.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by disword on 17/3/2.
 */

class PriorityFifoBlockingQueue {
    private final PriorityBlockingQueue<FIFOEntry> q = new PriorityBlockingQueue<>();

    public void add(QueueTask task) {
        q.add(new FIFOEntry(task));
    }

    public QueueTask take() throws InterruptedException {
        return q.take().getEntry();
    }

    public boolean isEmpty() {
        return q.isEmpty();
    }

    public boolean remove(QueueTask task) {
        for (FIFOEntry entry : q) {
            if (entry.getEntry() == task) {
                return q.remove(entry);
            }
        }
        return false;
    }

    private static class FIFOEntry implements Comparable<FIFOEntry> {

        static final AtomicLong SEQUENCE = new AtomicLong(0);

        final long seqNum;

        final QueueTask entry;

        public FIFOEntry(QueueTask entry) {
            seqNum = SEQUENCE.getAndIncrement();
            this.entry = entry;
        }

        public QueueTask getEntry() {
            return entry;
        }

        public int compareTo(@NonNull FIFOEntry other) {
            int res = entry.compareTo(other.entry);
            if (res == 0 && other.entry != this.entry) {
                res = (seqNum < other.seqNum ? -1 : 1);
            }
            return res;
        }
    }
}
