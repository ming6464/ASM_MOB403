package com.fpoly.assigment_mob403;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import java.net.URL;
import java.text.Normalizer;
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
import java.util.regex.Pattern;

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

    public static void ShowTwoOptionDsialog(Context context,String name1,Runnable action1,String name2,Runnable action2,String title,String mess) {
        // Xây dựng dialog với AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Thiết lập tiêu đề của dialog
        builder.setTitle(title);

        // Thiết lập thông báo trong dialog
        builder.setMessage(mess);

        if(name1 == null || name1.isEmpty()) name1 = "Yes";
        if(name2 == null || name2.isEmpty()) name2 = "No";

        // Thiết lập nút "Yes" và xử lý sự kiện khi nhấn vào nó
        builder.setPositiveButton(name1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(action1 != null) action1.run();
            }
        });

        // Thiết lập nút "No" và xử lý sự kiện khi nhấn vào nó
        builder.setNegativeButton(name2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(action2 != null) action2.run();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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


    public static void ShowThreeOptionsDialog(Context context,String title,String mes,String name1,Runnable action1,String name2,Runnable action2,String name3,Runnable action3) {
        // Xây dựng dialog với AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Thiết lập tiêu đề của dialog
        builder.setTitle(title);

        // Thiết lập thông báo trong dialog
        builder.setMessage(mes);

        // Thiết lập nút "Yes" và xử lý sự kiện khi nhấn vào nó

        if(name1 == null || name1.isEmpty()) name1 = "Yes";
        if(name2 == null || name2.isEmpty()) name2 = "No";
        if(name3 == null || name3.isEmpty()) name3 = "Cancle";

        builder.setPositiveButton(name1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(action1 != null) action1.run();
            }
        });

        // Thiết lập nút "No" và xử lý sự kiện khi nhấn vào nó
        builder.setNegativeButton(name2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng chọn "No"
                // Ví dụ: gọi hàm hoặc thực hiện hành động cần thiết
                if(action2 != null) action2.run();
            }
        });

        // Thiết lập nút "Cancel" và xử lý sự kiện khi nhấn vào nó
        builder.setNeutralButton(name3, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng chọn "Cancel"
                // Ví dụ: gọi hàm hoặc thực hiện hành động cần thiết
                if(action3 != null) action3.run();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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

    public static String removeDiacritics(String input) {
        String nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
    }

}
