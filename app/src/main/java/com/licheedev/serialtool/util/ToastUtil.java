package com.licheedev.serialtool.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.licheedev.serialtool.R;
import java.lang.ref.WeakReference;

/**
 * Toast工具类
 */
public class ToastUtil {

    private static WeakReference<Toast> mToastRef = null;

    /**
     * 自定义Toast
     */
    public static void showOne(Context context, String text) {
        Toast toast;
        if (mToastRef != null && (toast = mToastRef.get()) != null) {
            toast.setDuration(Toast.LENGTH_SHORT);
            TextView tv = (TextView) toast.getView().findViewById(R.id.tv_toast_text);
            tv.setText(text);
        } else {

            toast = new Toast(context);

            LayoutInflater inflate =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflate.inflate(R.layout.custom_toast, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_toast_text);
            tv.setText(text);
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);
            
            mToastRef = new WeakReference<>(toast);
        }

        toast.show();
    }

    /**
     * 自定义Toast
     */
    public static void showOne(Context context, int resid) {
        showOne(context, context.getResources().getString(resid));
    }

    public static void show(Context context, String text) {
        Toast toast = new Toast(context);
        LayoutInflater inflate =
            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.custom_toast, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_toast_text);
        tv.setText(text);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void show(Context context, int resid) {
        show(context, context.getResources().getString(resid));
    }
}
