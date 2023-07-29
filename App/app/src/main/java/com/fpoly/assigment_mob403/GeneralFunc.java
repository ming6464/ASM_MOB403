package com.fpoly.assigment_mob403;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GeneralFunc {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static Bitmap LoadImageFromLink(String link){
        Bitmap bitmap = null;
        URL mUrl;
        try {
            mUrl = new URL(link);

            bitmap = BitmapFactory.decodeStream(mUrl.openConnection().getInputStream());

        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void LoadImageFromLink(String link, ImageView _img_anh1){
        Runnable newRunalbe = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = GeneralFunc.LoadImageFromLink(link);
                _img_anh1.post(new Runnable() {
                    @Override
                    public void run() {
                        _img_anh1.setImageBitmap(bitmap);
                    }
                });
            }
        };

        Thread newThread = new Thread(newRunalbe);
        newThread.start();
    }

    public static List<Bitmap> LoadImageFuture(List<String> links){
        List<Bitmap> bitmaps = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(links.size());
        for(String url : links){

            try {
                Callable<Bitmap> callable = new Callable() {
                    @Override
                    public Object call() throws Exception {
                        Bitmap bitmap = LoadImageFromLink(url);
                        return bitmap;
                    }
                };
                Future<Bitmap> future = executorService.submit(callable);
                if(future.get() != null){
                    bitmaps.add(future.get());
                }
            }catch (Exception e){

            }
        }

        return bitmaps;
    }

    public static String ConvertToStringDate(long milli){
        return sdf.format(new Date(milli));
    }

    public static String ConvertToDateString(Date date){
        return sdf.format(date);
    }

    public static long ConvertStringDateToMillisecond(String StringDate){
        long milliseconds = 0;
        try {
            Date date = sdf.parse(StringDate);
            milliseconds = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    public static Date ConvertStringDateToDate(String StringDate){
        Date date1 = new Date();
        try {
            date1 = sdf.parse(StringDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static void ChangeColorButton(Button btn,String colorCode){
        btn.setBackgroundColor(Color.parseColor(colorCode));
    }

}
