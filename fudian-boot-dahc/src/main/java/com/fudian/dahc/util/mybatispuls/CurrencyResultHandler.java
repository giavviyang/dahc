package com.fudian.dahc.util.mybatispuls;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.LinkedList;

/**
 * 流处理批量处理
 * 2023/3/9
 * @author wenbo
 */
public class CurrencyResultHandler<T> implements ResultHandler<T> {
    /**
     * 这是每批处理的大小
     */
    private final static int BATCH_SIZE = 5;
    private int size;
    /**
     * 存储每批数据的临时容器
     */
    public LinkedList<T> gxids = new LinkedList<>();


    @Override
    public void handleResult(ResultContext<? extends T> resultContext) {
        // 这里获取流式查询每次返回的单条结果
        T resultObject = resultContext.getResultObject();
        // 你可以看自己的项目需要分批进行处理或者单个处理，这里以分批处理为例
        gxids.add(resultObject);
        size++;
        if (size == BATCH_SIZE) {
            handle();
        }
    }

    public void handle() {
        try {
            // 在这里可以对你获取到的批量结果数据进行需要的业务处理

        } finally {
            // 处理完每批数据后后将临时清空
            size = 0;
            gxids.clear();
        }
    }

    /**
     * 这个方法给外面调用，用来完成最后一批数据处理
      */
    public void end() {
        System.out.println("这个方法给外面调用，用来完成最后一批数据处理");
        handle();// 处理最后一批不到BATCH_SIZE的数据
    }

}
