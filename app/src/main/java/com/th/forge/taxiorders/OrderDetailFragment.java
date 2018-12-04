package com.th.forge.taxiorders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.th.forge.taxiorders.entity.Price;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailFragment extends Fragment {
    private static final String ORDER_KEY = "order_key";
    private OnFragmentInteractionListener interactionListener;

    private Order order;

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
        ImageView imageView = view.findViewById(R.id.detail_image);
        TextView startStreet = view.findViewById(R.id.detail_start_address);
        TextView endStreet = view.findViewById(R.id.detail_end_address);
        TextView driverName = view.findViewById(R.id.detail_vehicle_driver);
        TextView vehicleModel = view.findViewById(R.id.detail_vehicle_model);
        TextView vehicleNumber = view.findViewById(R.id.detail_vehicle_reg_number);
        TextView priceField = view.findViewById(R.id.detail_price);
        if (interactionListener != null) {
            String title = getString(R.string.detail_title_field)
                    + new SimpleDateFormat("dd.MM.yyyy").format(order.getOrderTime().getTime());
            String subtitle = getString(R.string.detail_subtitle_field)
                    + new SimpleDateFormat("HH:mm").format(order.getOrderTime().getTime());
            interactionListener.onFragmentInteraction(title, subtitle);
        }
        startStreet.setText(order.getStartAddress().getAddress());
        endStreet.setText(order.getEndAddress().getAddress());
        driverName.setText(order.getVehicle().getDriverName());
        vehicleModel.setText(order.getVehicle().getModelName());
        vehicleNumber.setText(order.getVehicle().getRegNumber());

        String imagePath = order.getVehicle().getPhoto();

        Currency currency = Currency.getInstance(order.getPrice().getCurrency());
        NumberFormat formatter = NumberFormat.getInstance();
//        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setCurrency(currency);
        /*Price price = new Price();
        price.setAmount(12345);
        order.setPrice(price);*/
        priceField.setText(String.format("%s %s", formatter.format(order.getPrice().getAmount()),getResources().getString(R.string.rubleSymbol)));
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
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title, String subtitle);
    }
}
