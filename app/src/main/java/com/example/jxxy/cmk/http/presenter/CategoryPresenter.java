package com.example.jxxy.cmk.http.presenter;


import com.example.jxxy.cmk.http.HttpMethods;
import com.example.jxxy.cmk.http.entity.CategoryEntity;
import com.example.jxxy.cmk.http.service.CategoryService;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

//CategoryPresenter
//使用Retrofit+RxJava实现用户相关的网络请求。
public class CategoryPresenter extends HttpMethods {

    public static void getTopList(Subscriber<List<CategoryEntity>> subscriber){
        Observable<List<CategoryEntity>> observable = categoryService.getTopList()
                .map(new HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable,subscriber);
    }//通过toSubscribe通知observer进行数据获取

    public static void getSecondList(Subscriber<List<CategoryEntity>> subscriber, int parentId){
        Observable<List<CategoryEntity>> observable = categoryService.getSecondList(parentId)
                .map(new HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable,subscriber);
    }


}
