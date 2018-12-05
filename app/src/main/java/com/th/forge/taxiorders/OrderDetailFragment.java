package com.th.forge.taxiorders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.th.forge.taxiorders.entity.Order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class OrderDetailFragment extends Fragment {
    private static final String ORDER_KEY = "order_key";

    private OnFragmentInteractionListener interactionListener;
    private Order order;
    private ImageView imageView;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title, String subtitle);
    }

    public static OrderDetailFragment newInstance(Serializable order) {
        Bundle args = new Bundle();
        args.putSerializable(ORDER_KEY, order);
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactionListener = (OnFragmentInteractionListener) context;
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
        imageView = view.findViewById(R.id.detail_image);
        TextView startStreet = view.findViewById(R.id.detail_start_address);
        TextView endStreet = view.findViewById(R.id.detail_end_address);
        TextView driverName = view.findViewById(R.id.detail_vehicle_driver);
        TextView vehicleModel = view.findViewById(R.id.detail_vehicle_model);
        TextView vehicleNumber = view.findViewById(R.id.detail_vehicle_reg_number);
        TextView priceField = view.findViewById(R.id.detail_price);
        updateTitle();
        startStreet.setText(order.getStartAddress().getAddress());
        endStreet.setText(order.getEndAddress().getAddress());
        driverName.setText(order.getVehicle().getDriverName());
        vehicleModel.setText(order.getVehicle().getModelName());
        vehicleNumber.setText(order.getVehicle().getRegNumber());
        Currency currency = Currency.getInstance(order.getPrice().getCurrency());
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setCurrency(currency);
        priceField.setText(String.format("%s %s", formatter.format(order.getPrice().getAmount()), getResources().getString(R.string.rubleSymbol)));
        new FetchImageTask().execute();
        return view;
    }

    private void updateTitle() {
        if (interactionListener != null) {
            String title = getString(R.string.detail_title_field)
                    + new SimpleDateFormat("dd.MM.yyyy").format(order.getOrderTime().getTime());
            String subtitle = getString(R.string.detail_subtitle_field)
                    + new SimpleDateFormat("HH:mm").format(order.getOrderTime().getTime());
            interactionListener.onFragmentInteraction(title, subtitle);
        }
    }

    private class FetchImageTask extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return getImage();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getImage() {
        String imagePath = order.getVehicle().getPhoto();
        File cachedImage = new File(getActivity().getFilesDir(), imagePath);
        Bitmap bmp;
        long currentTime = System.currentTimeMillis();
        if (cachedImage.exists() && (currentTime - cachedImage.lastModified() < 1 * 1000 * 60)) {
            bmp = BitmapFactory.decodeFile(cachedImage.getAbsolutePath());
        } else {
            cachedImage.delete();
            try {
                Response<ResponseBody> response = App.getApiService().getImage(imagePath).execute();
                if(response.body()!=null){
                    OutputStream os;
                    os = new FileOutputStream(cachedImage);
                    os.write(response.body().bytes());
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bmp = BitmapFactory.decodeFile(cachedImage.getAbsolutePath());
        }
        return bmp;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }
}
