package net.minesec.rules.api;

import org.eclipse.jetty.util.BlockingArrayQueue;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public abstract class Pool<T> {

    private final Queue<Consumer<T>> queue;
    private final Queue<T> ready;
    private final int limit;
    private int active;
    private final ExecutorService executorService;

    public Pool(int limit) {
        this.queue = new BlockingArrayQueue<>();
        this.ready = new BlockingArrayQueue<>();
        this.limit = limit;
        this.active = 0;
        this.executorService = Executors.newCachedThreadPool();
    }

    /**
     * @param task a task which essentially leases a type T.
     *             this task must return only when the type T is free to use for other tasks.
     */
    public void queue(Consumer<T> task) {
        T entry = this.ready.poll();
        if (entry == null) {
            if (this.active < this.limit) {
                entry = this.create();
                this.submit(task, entry);
            } else {
                this.queue.add(task);
            }
        } else {
            this.submit(task, entry);
        }
    }

    protected abstract T create();

    private void release(T entry) {
        Consumer<T> task = this.queue.poll();
        if (task == null) {
            this.ready.add(entry);
        } else {
            this.submit(task, entry);
        }
    }

    private void submit(Consumer<T> task, T entry) {
        this.executorService.submit(() -> {
            try {
                task.accept(entry);
            } finally {
                Pool.this.release(entry);
            }
        });
    }

}
