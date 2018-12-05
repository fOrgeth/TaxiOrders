package com.th.forge.taxiorders;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.th.forge.taxiorders.entity.Order;
import com.th.forge.taxiorders.repo.ImageRepository;
import com.th.forge.taxiorders.utils.CurrencyParser;
import com.th.forge.taxiorders.utils.DateTimeParser;

import java.io.Serializable;


public class OrderDetailFragment extends Fragment {
    private static final String ORDER_KEY = "order_key";
    private static final String LOG_TAG = "DETAILFRGMNT";
    private static final String SAVE_IMAGE = "SAVE_IMAGE";

    private OnFragmentInteractionListener interactionListener;
    private Order order;
    private Bitmap image;

    /* ToDo ButterKnife */
    private ImageView imageView;
    private TextView startStreet;
    private TextView endStreet;
    private TextView driverName;
    private TextView vehicleModel;
    private TextView vehicleNumber;
    private TextView priceField;

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
        if (savedInstanceState != null) {
            Log.d(LOG_TAG, "onCreateView, saved != null");
            image = savedInstanceState.getParcelable(SAVE_IMAGE);
        } else {
            Log.d(LOG_TAG, "onCreateView, saved = null");
            new FetchImageTask(order.getVehicle().getPhoto()).execute();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        initUi(view);
        setData();
        return view;
    }

    private void initUi(View view) {
        updateTitle();
        imageView = view.findViewById(R.id.detail_image);
        startStreet = view.findViewById(R.id.detail_start_address);
        endStreet = view.findViewById(R.id.detail_end_address);
        driverName = view.findViewById(R.id.detail_vehicle_driver);
        vehicleModel = view.findViewById(R.id.detail_vehicle_model);
        vehicleNumber = view.findViewById(R.id.detail_vehicle_reg_number);
        priceField = view.findViewById(R.id.detail_price);
    }

    private void updateTitle() {
        if (interactionListener != null) {
            String title = String.format("%s %s", getString(R.string.detail_title_field),
                    DateTimeParser.getReadableString("dd.MM.yyyy", order.getOrderTime().getTime()));
            String subtitle = String.format("%s %s", getString(R.string.detail_subtitle_field),
                    DateTimeParser.getReadableString("HH:mm", order.getOrderTime().getTime()));
            interactionListener.onFragmentInteraction(title, subtitle);
        }
    }

    private void setData() {
        imageView.setImageBitmap(image);
        startStreet.setText(order.getStartAddress().getAddress());
        endStreet.setText(order.getEndAddress().getAddress());
        driverName.setText(order.getVehicle().getDriverName());
        vehicleModel.setText(order.getVehicle().getModelName());
        vehicleNumber.setText(order.getVehicle().getRegNumber());
        priceField.setText(CurrencyParser
                .getFormattedPrice(order.getPrice().getAmount(),
                order.getPrice().getCurrency(),getResources().getString(R.string.rubleSymbol)));
    }

    private class FetchImageTask extends AsyncTask<Void, Void, Bitmap> {

        private String imagePath;
        FetchImageTask(String imagePath) {
            this.imagePath = imagePath;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Log.d(LOG_TAG, "I'm HERE!!!");
            image = ImageRepository.getImage(getActivity().getFilesDir(), imagePath);
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstance");
        if (image != null) {
            outState.putParcelable(SAVE_IMAGE, image);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }
}
