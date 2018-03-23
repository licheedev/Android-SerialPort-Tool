package com.licheedev.serialtool.comn;

import android.os.HandlerThread;
import android.serialport.SerialPort;
import com.licheedev.myutils.LogPlus;
import com.licheedev.serialtool.comn.message.LogManager;
import com.licheedev.serialtool.comn.message.SendMessage;
import com.licheedev.serialtool.util.ByteUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class SerialPortManager {

    private static final String TAG = "SerialPortManager";

    private SerialReadThread mReadThread;
    private OutputStream mOutputStream;
    private HandlerThread mWriteThread;
    private Scheduler mSendScheduler;

    private static class InstanceHolder {

        public static SerialPortManager sManager = new SerialPortManager();
    }

    public static SerialPortManager instance() {
        return InstanceHolder.sManager;
    }

    private SerialPort mSerialPort;

    private SerialPortManager() {
    }

    /**
     * 打开串口
     *
     * @param device
     * @return
     */
    public SerialPort open(Device device) {
        return open(device.getPath(), device.getBaudrate());
    }

    /**
     * 打开串口
     *
     * @param devicePath
     * @param baudrateString
     * @return
     */
    public SerialPort open(String devicePath, String baudrateString) {
        if (mSerialPort != null) {
            close();
        }

        try {
            File device = new File(devicePath);
            int baurate = Integer.parseInt(baudrateString);
            mSerialPort = new SerialPort(device, baurate, 0);

            mReadThread = new SerialReadThread(mSerialPort.getInputStream());
            mReadThread.start();

            mOutputStream = mSerialPort.getOutputStream();

            mWriteThread = new HandlerThread("write-thread");
            mWriteThread.start();
            mSendScheduler = AndroidSchedulers.from(mWriteThread.getLooper());

            return mSerialPort;
        } catch (Throwable tr) {
            LogPlus.e(TAG, "打开串口失败", tr);
            close();
            return null;
        }
    }

    /**
     * 关闭串口
     */
    public void close() {
        if (mReadThread != null) {
            mReadThread.close();
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mWriteThread != null) {
            mWriteThread.quit();
        }

        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    /**
     * 发送数据
     *
     * @param datas
     * @return
     */
    private void sendData(byte[] datas) throws Exception {
        mOutputStream.write(datas);
    }

    /**
     * (rx包裹)发送数据
     *
     * @param datas
     * @return
     */
    private Observable<Object> rxSendData(final byte[] datas) {

        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    sendData(datas);
                    emitter.onNext(new Object());
                } catch (Exception e) {

                    LogPlus.e("发送：" + ByteUtil.bytes2HexStr(datas) + " 失败", e);

                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                        return;
                    }
                }
                emitter.onComplete();
            }
        });
    }

    /**
     * 发送命令包
     */
    public void sendCommand(final String command) {

        // TODO: 2018/3/22  
        LogPlus.i("发送命令：" + command);

        byte[] bytes = ByteUtil.hexStr2bytes(command);
        rxSendData(bytes).subscribeOn(mSendScheduler).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                
            }

            @Override
            public void onNext(Object o) {
                LogManager.instance().post(new SendMessage(command));
            }

            @Override
            public void onError(Throwable e) {
                LogPlus.e("发送失败", e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
