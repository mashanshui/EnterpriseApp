package com.shenhesoft.enterpriseapp.base;


import cn.droidlover.xdroidmvp.event.IBus;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description baseEvent
 */

public class BaseEvent implements IBus.IEvent{
    public int tag;
    public Object content;

    public BaseEvent(int tag){
        this.tag=tag;
    }

    public BaseEvent(int tag, Object content){
        this.tag=tag;
        this.content=content;
    }


    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public int getTag() {
        return tag;
    }
}
