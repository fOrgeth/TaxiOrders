package com.th.forge.taxiorders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.th.forge.taxiorders.api.RetroClient;
import com.th.forge.taxiorders.api.RoxieApiService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailFragment extends Fragment {
    public static OrderDetailFragment newInstance(){
        return new OrderDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ImageView imageView = view.findViewById(R.id.detail_image);
        RoxieApiService api = RetroClient.getApiService();

        Call<ResponseBody> call = api.getImage("01.jpg");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
//                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        File file = new File(getActivity().getFilesDir(), "01.jpg");
                        /*Log.d("OrdDetFrag",file.getPath());
                        OutputStream os;
                        try {
                            os = new FileOutputStream(file);
                            os.write(response.body().bytes());
                            os.flush();
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(file.lastModified()));
                        Log.d("!!!!!",date);
                        Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
                        imageView.setImageBitmap(bmp);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return view;
    }
}
