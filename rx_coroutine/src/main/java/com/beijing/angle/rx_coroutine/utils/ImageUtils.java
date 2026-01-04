package com.beijing.angle.rx_coroutine.utils;

import static com.blankj.utilcode.util.ImageUtils.save2Album;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.beijing.angle.rx_coroutine.base.BaseFragment;
import com.beijing.angle.rx_coroutine.lifecycle.RxActivity;
import com.beijing.angle.rx_coroutine.lifecycle.RxFragment;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.util.PermissionConstants;
import com.lxj.xpopup.util.XPermission;
import com.lxj.xpopup.util.XPopupUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/26 18:01
 * @Link: https://github.com/AngleCoding
 */

public class ImageUtils {


    private static ImageUtils imageUtils = null;

    public static ImageUtils getInstance() {
        if (imageUtils == null) {
            synchronized (ImageUtils.class) {
                if (imageUtils == null) {
                    imageUtils = new ImageUtils();
                }
            }
        }
        return imageUtils;
    }


    /**
     * 保存图片
     *
     * @param resources R.mipmap
     */
    public void saveResourcesPic(RxActivity activity, int resources) {
        Observable.create((ObservableOnSubscribe<File>) emitter -> {
                    Bitmap bitmap = BitmapFactory.decodeResource(activity.getApplicationContext().getResources(),
                            resources);
                    emitter.onNext(save2Album(bitmap, Bitmap.CompressFormat.PNG));

                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(activity.bindToLifecycle())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull File b) {
                        if (b != null) {
                            ToastUtils.showShort("图片保存成功");
                        } else {
                            ToastUtils.showShort("图片保存失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    /**
     * 保存图片
     *
     * @param resources R.mipmap
     */
    public void saveResourcesPic(RxFragment fragment, int resources) {
        Observable.create((ObservableOnSubscribe<File>) emitter -> {
                    Bitmap bitmap = BitmapFactory.decodeResource(fragment.getContext().getResources(),
                            resources);
                    emitter.onNext(save2Album(bitmap, Bitmap.CompressFormat.PNG));

                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.bindToLifecycle())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull File b) {
                        if (b != null) {
                            ToastUtils.showShort("图片保存成功");
                        } else {
                            ToastUtils.showShort("图片保存失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 保存图片 带通知栏
     *
     * @param resources R.mipmap
     */
    public void saveResourcesPicShowNotification(RxActivity activity, int resources, int appIcon) {
        Observable.create((ObservableOnSubscribe<File>) emitter -> {
                    Bitmap bitmap = BitmapFactory.decodeResource(activity.getApplicationContext().getResources(),
                            resources);
                    emitter.onNext(save2Album(bitmap, Bitmap.CompressFormat.PNG));

                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(activity.bindToLifecycle())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull File b) {
                        if (b != null) {
                            NotificationUtils.INSTANCE.showNotification(activity.getApplicationContext(), appIcon, null, "图片保存成功");
                        } else {
                            ToastUtils.showShort("图片保存失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 保存图片 带通知栏
     *
     * @param resources R.mipmap
     */
    public void saveResourcesPicShowNotification(RxFragment fragment, Context mContext, int resources, int appIcon) {
        Observable.create((ObservableOnSubscribe<File>) emitter -> {
                    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                            resources);
                    emitter.onNext(save2Album(bitmap, Bitmap.CompressFormat.PNG));

                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.bindToLifecycle())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull File b) {
                        if (b != null) {
                            NotificationUtils.INSTANCE.showNotification(mContext, appIcon, null, "图片保存成功");
                        } else {
                            ToastUtils.showShort("图片保存失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
