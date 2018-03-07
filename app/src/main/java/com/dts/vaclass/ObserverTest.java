package com.dts.vaclass;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * 基本的观察者模式
 */

public class ObserverTest {

    private static final String TAG = "Test";

    static class Coder implements Observer{
        private String name;
        public Coder(String name) { this.name = name; }

        @Override
        public void update(Observable o, Object arg) {
            System.out.print(name+"内容更新："+arg);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"").append(name).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }

    static class DevTech extends Observable{
        public void postNewPublication(String content){
            setChanged();  //标识状态或者内容发生改变
            notifyObservers(content);  //通知所有观察者
        }
    }

    public static void main(String[] args) {
        DevTech mDevTech=new DevTech();  //  被观察者的角色
        for (int i = 0; i < 5; i++) {
            Coder mCoder=new Coder("打印出来\t"+i);
            //被观察者注册到可观察对象的观察者列表中
            mDevTech.addObserver(mCoder);
        }
        //发布消息
        mDevTech.postNewPublication("结束语!!!\n");
    }
}
