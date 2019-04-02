package net.lzzy.cinemanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.utils.AppUtils;
import net.lzzy.simpledatepicker.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddOrdersFragment extends BaseFragment {
    private Spinner spinner;
    private CustomDatePicker picker;
    private TextView tvDate;
    private EditText edtMovieName;
    private EditText edtPrice;
    public ImageView imgRQCode;
    private OnOrderCreatedListener orderCreatedListener;
    private OnFragmentInteractionLisener listener;
    public AddOrdersFragment(){}

    @Override
    protected void populate() {
        listener.hideSearch();
        initViews();
        initDatePicker();
        //find(R.id.fragment_add_order_tv);
        List<Cinema> cinemas= CinemaFactory.getInstance().get();
        spinner.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,cinemas));
        find(R.id.add_order_btn_save).setOnClickListener(v -> {
            String sCinemaName=spinner.getSelectedItem().toString();
            //region
            //String sCinemaId= CinemaFactory.getInstance().getIdByName(sCinemaName);
            //UUID uCinemaId=UUID.fromString(sCinemaId);
            //String sid=CinemaFactory.getInstance().getById(spinner.getSelectedItemPosition())
            //List<Cinema> cinemas=CinemaFactory.getInstance().get();
            //endregion
            Cinema sCinema = cinemas.get(spinner.getSelectedItemPosition());
            UUID uCinemaId=sCinema.getId();

            String movieName=edtMovieName.getText().toString();
            String price;
            try{
                price=edtPrice.getText().toString();
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(), "数字格式错误", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(movieName)||TextUtils.isEmpty(price)){
                Toast.makeText(getActivity(),"电影名称或价格不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            Order order=new Order();
            order.setMovie(movieName);
            float fPrice=Float.valueOf(price);
            order.setPrice(fPrice);
            order.setMovieTime(tvDate.getText().toString());

            order.setCinemaId(uCinemaId);
            //adapter.add(order);
            orderCreatedListener.saveOrder(order);

            edtMovieName.setText("");
            edtPrice.setText("");




        });

        find(R.id.add_order_btn_create).setOnClickListener(v -> {
            String movieName=edtMovieName.getText().toString();
            String price=edtPrice.getText().toString();
            String location=spinner.getSelectedItem().toString();
            String time=tvDate.getText().toString();
            if (TextUtils.isEmpty(movieName)||TextUtils.isEmpty(price)){
                Toast.makeText(getActivity(),"电影名称或价格不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            String content="["+movieName+"]"+time+"\n"+location+"票价"+price+"元";
            imgRQCode.setImageBitmap(AppUtils.createQRCodeBitmap(content,200,200));

        });

        find(R.id.add_order_choice_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(tvDate.getText().toString());
            }
        });
        find(R.id.add_order_btn_cancel).setOnClickListener(v -> {
            orderCreatedListener.cancelAddOrder();
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_add_order;
    }

    @Override
    public void search(String koy) {

    }
    private void initDatePicker(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        String now=simpleDateFormat.format(new Date());
        tvDate.setText(now);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,1);
        String end=simpleDateFormat.format(calendar.getTime());
        picker=new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String s) {
                tvDate.setText(s);
            }
        },now,end);


    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener=(OnFragmentInteractionLisener) context;
            orderCreatedListener=(OnOrderCreatedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"必须实现OnFragmentInteractionListener ");
        }

    }
    private void initViews() {
        spinner=find(R.id.add_order_sp);
        tvDate=find(R.id.add_order_select_time);
        edtMovieName=find(R.id.add_order_edt_movie_name);
        edtPrice=find(R.id.add_order_edt_movie_price);
        imgRQCode=find(R.id.add_order_rq_code);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            listener.hideSearch();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
        orderCreatedListener=null;
    }

    public interface OnOrderCreatedListener{
        void cancelAddOrder();
        void saveOrder(Order order);
    }
}

