package com.liangduo.kr36.tool;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.liangduo.kr36.base.BaseApplication;

/**
 * Created by liangduo on 16/5/16.
 */
public class VolleySingle {
    private static Context context;
    private RequestQueue queue;//请求队

    private static VolleySingle ourInstance = new VolleySingle();

    //获取单例的对象
    public static VolleySingle getInstance() {
        return ourInstance;
    }

    private VolleySingle() {
        //获取Application里面的context
        context = BaseApplication.getContext();
        queue = getQueue();//初始化我的请求队列
    }

    //提供一个方法获取请求队列
    public RequestQueue getQueue(){
        if (queue == null ){
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    private static final  String TAG = "VolleySingleton";

    //添加请求
    public <T> void _addRequest(Request<T> request){
        request.setTag(TAG);//我IE我的请求添加标签,方便管理
        queue.add(request);//将请求添加到队列当中
    }

    public  <T> void _addRequest(Request<T> request,Object tag){
        request.setTag(tag);
        queue.add(request);
    }

    //创建StringRequest
    //三个参数分别是 url 接口网址
    //成功时的回调,失败时的回调
    private void _addRequest(String url,
                             Response.Listener<String> listener,
                             Response.ErrorListener errorListener){
        StringRequest stringRequest = new StringRequest(url, listener,errorListener);
        //将请求加入队列
        _addRequest(stringRequest);
    }

    public <T> void _addRequest(String url,Class<T> mClass,Response.Listener<T> listener,Response.ErrorListener errorListener){
        GsonRequest gsonRequest  = new GsonRequest(Request.Method.GET,
                url,errorListener,listener,mClass);
        _addRequest(gsonRequest);
    }

    //将请求队列移除
    public void removeRequest(Object tag){
        queue.cancelAll(tag);//根据不同的tag移除队列
    }

    public static void addRequest(String url,Response.Listener<String> listener,
                                  Response.ErrorListener errorListener){
        //获取单例对象,调用添加请求的方法(StringRequest的请求)
        getInstance()._addRequest(url,listener,errorListener);
    }

    public static <T> void addRequest(String url,Class<T> mClass,Response.Listener<T> listener,
                                      Response.ErrorListener errorListener){
        //同上方法将GsonRequest请求加入单例的队列里
        getInstance()._addRequest(url,mClass,listener,errorListener);
    }
}



    /**
     * 使用这个类的方法:
     * 在需要解析网络数据的地方
     * VolleySingle.addRequest("双引号里放网址",new Response.Listener<String>(){
     *    @Override
          public void onResponse(String response) {
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
          }
        });
     */


