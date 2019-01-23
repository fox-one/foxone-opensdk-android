package com.fox.one.support.common.concurrent;

/**
 * Created by zhangyinghao on 2018/4/16.
 */
public abstract class Task<Input,Output> {

    public Input mInput;
    public Task(Input input){
        mInput = input;
    }
    protected abstract Output onDoInBackground(Input param);

    protected abstract void onPostExecuteForeground(Output result);
}
