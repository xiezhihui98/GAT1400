package com.cz.viid.framework.context;

import com.cz.viid.framework.domain.dto.OperationLog;
import com.google.common.collect.EvictingQueue;

import java.util.ArrayList;
import java.util.List;

public class LogHolder {

    public static final EvictingQueue<OperationLog> LOG_QUEUE = EvictingQueue.create(200);


    public synchronized static void add(OperationLog log) {
        LOG_QUEUE.add(log);
    }

    public synchronized static List<OperationLog> list() {
        return new ArrayList<>(LOG_QUEUE);
    }
}
