package com.th.forge.taxiorders

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.th.forge.taxiorders.entity.Order
import com.th.forge.taxiorders.repo.ImageRepository
import com.th.forge.taxiorders.utils.CurrencyParser
import com.th.forge.taxiorders.utils.DateTimeParser

import java.io.Serializable


class OrderDetailFragment : Fragment() {

    private var interactionListener: OnFragmentInteractionListener? = null
    private var order: Order? = null
    private var image: Bitmap? = null

    /* ToDo ButterKnife */
    private var imageView: ImageView? = null
    private var startStreet: TextView? = null
    private var endStreet: TextView? = null
    private var driverName: TextView? = null
    private var vehicleModel: TextView? = null
    private var vehicleNumber: TextView? = null
    private var priceField: TextView? = null

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(title: String, subtitle: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        interactionListener = context as OnFragmentInteractionListener?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            order = arguments!!.getSerializable(ORDER_KEY) as Order
        }
        if (savedInstanceState != null) {
            Log.d(LOG_TAG, "onCreateView, saved != null")
            image = savedInstanceState.getParcelable(SAVE_IMAGE)
        } else {
            Log.d(LOG_TAG, "onCreateView, saved = null")
            FetchImageTask(order!!.vehicle!!.photo).execute()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_order_detail, container, false)
        initUi(view)
        setData()
        return view
    }

    private fun initUi(view: View) {
        updateTitle()
        imageView = view.findViewById(R.id.detail_image)
        startStreet = view.findViewById(R.id.detail_start_address)
        endStreet = view.findViewById(R.id.detail_end_address)
        driverName = view.findViewById(R.id.detail_vehicle_driver)
        vehicleModel = view.findViewById(R.id.detail_vehicle_model)
        vehicleNumber = view.findViewById(R.id.detail_vehicle_reg_number)
        priceField = view.findViewById(R.id.detail_price)
    }

    private fun updateTitle() {
        if (interactionListener != null) {
            val title = String.format("%s %s", getString(R.string.detail_title_field),
                    DateTimeParser.getReadableString("dd.MM.yyyy", order!!.orderTime!!.time))
            val subtitle = String.format("%s %s", getString(R.string.detail_subtitle_field),
                    DateTimeParser.getReadableString("HH:mm", order!!.orderTime!!.time))
            interactionListener!!.onFragmentInteraction(title, subtitle)
        }
    }

    private fun setData() {
        imageView!!.setImageBitmap(image)
        startStreet!!.text = order!!.startAddress!!.address
        endStreet!!.text = order!!.endAddress!!.address
        driverName!!.text = order!!.vehicle!!.driverName
        vehicleModel!!.text = order!!.vehicle!!.modelName
        vehicleNumber!!.text = order!!.vehicle!!.regNumber
        priceField!!.text = CurrencyParser
                .getFormattedPrice(order!!.price!!.amount,
                        order!!.price!!.currency, resources.getString(R.string.rubleSymbol))
    }

    private inner class FetchImageTask internal constructor(private val imagePath: String) : AsyncTask<Void, Void, Bitmap>() {

        override fun doInBackground(vararg voids: Void): Bitmap? {
            Log.d(LOG_TAG, "I'm HERE!!!")
            image = ImageRepository.getImage(activity!!.filesDir, imagePath)
            return image
        }

        override fun onPostExecute(bitmap: Bitmap) {
            imageView!!.setImageBitmap(bitmap)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "onSaveInstance")
        if (image != null) {
            outState.putParcelable(SAVE_IMAGE, image)
        }
    }

    override fun onDetach() {
        super.onDetach()
        interactionListener = null
    }

    companion object {
        private val ORDER_KEY = "order_key"
        private val LOG_TAG = "DETAILFRGMNT"
        private val SAVE_IMAGE = "SAVE_IMAGE"

        fun newInstance(order: Serializable): OrderDetailFragment {
            val args = Bundle()
            args.putSerializable(ORDER_KEY, order)
            val fragment = OrderDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
