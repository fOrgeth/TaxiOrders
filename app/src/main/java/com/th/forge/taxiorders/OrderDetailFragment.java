package com.th.forge.taxiorders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.th.forge.taxiorders.entity.Order;

import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailFragment extends Fragment {
    private static final String ORDER_KEY = "order_key";

    private Order order;

    public static OrderDetailFragment newInstance(Serializable order) {
        Bundle args = new Bundle();
        args.putSerializable(ORDER_KEY, order);
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable(ORDER_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ImageView imageView = view.findViewById(R.id.detail_image);
        String imagePath = order.getVehicle().getPhoto();
        TextView priceField = view.findViewById(R.id.detail_price);
        Currency currency = Currency.getInstance(order.getPrice().getCurrency());
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setCurrency(currency);
        priceField.setText(formatter.format(order.getPrice().getAmount()).toString());
        App.getApiService().getImage(imagePath).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
//                        File file = new File(getActivity().getFilesDir(), "01.jpg");
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
//                        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(file.lastModified()));
//                        Log.d("!!!!!", date);
//                        Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
                        imageView.setImageBitmap(bmp);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("Date: "+new SimpleDateFormat("dd.MM.yyyy").format(order.getOrderTime().getTime()));
        activity.getSupportActionBar().setSubtitle("Time: "+new SimpleDateFormat("HH:mm:ss").format(order.getOrderTime().getTime()));
        return view;
    }
}
