package com.th.forge.taxiorders.repo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.th.forge.taxiorders.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ImageRepository {
    private static final long MINUTE = 1000 * 60;
    private static File cachedImage;

    public static Bitmap getImage(File fileDir, String imagePath) {
        Log.d("ImageRepo","getImage");
        cachedImage = new File(fileDir, imagePath);
        if (isImageExistsAndNotExpired()) {
            return BitmapFactory.decodeFile(cachedImage.getAbsolutePath());
        }
        cachedImage.delete();
        getResponseAndWriteImage(imagePath);
        return BitmapFactory.decodeFile(cachedImage.getAbsolutePath());
    }

    private static void getResponseAndWriteImage(String imagePath) {
        try {
            Response<ResponseBody> response = App.getApiService().getImage(imagePath).execute();
            if (response.body() != null) {
                writeImageToDisk(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeImageToDisk(Response<ResponseBody> response) throws IOException {
        OutputStream os;
        os = new FileOutputStream(cachedImage);
        os.write(response.body().bytes());
        os.flush();
        os.close();
    }

    /* ToDo need background service*/
    private static boolean isImageExistsAndNotExpired() {
        long currentTime = System.currentTimeMillis();
        return cachedImage.exists() && (currentTime - cachedImage.lastModified() < 10 * MINUTE);
    }
}
