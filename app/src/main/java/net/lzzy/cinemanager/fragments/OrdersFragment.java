package net.lzzy.cinemanager.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.cinemanager.utils.AppUtils;
import net.lzzy.cinemanager.utils.ViewUtils;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class OrdersFragment extends BaseFragment {

    private ListView listView;
    public static final float MIN_DISTANCE=100;
    private List<Order> orders;
    private OrderFactory factory=OrderFactory.getInstance();
    private GenericAdapter<Order> adapter;
    private Order order;
    private float touchX1;
    private boolean isDelete=false;
    public OrdersFragment(){}
    public OrdersFragment(Order order){
        this.order=order;
    }


    @Override
    protected void populate() {
        listView=find(R.id.fragment_order_lv);
        orders=factory.get();
        adapter=new GenericAdapter<Order>(getActivity(),R.layout.order_item,orders) {
            @Override
            public void populate(ViewHolder viewHolder, Order order) {
                String location= CinemaFactory.getInstance().getById(order.getCinemaId().toString()).toString();
                viewHolder.setTextView(R.id.order_item_tv_name,order.getMovie())
                        .setTextView(R.id.order_item_tv_location,location);
                Button btn=viewHolder.getView(R.id.order_item_btn_delete);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("删除订单")
                                .setMessage("确定要删除吗?")
                                .setNegativeButton("取消",null)
                                .setPositiveButton("确定",(dialog, which) ->
                                        adapter.remove(order))
                                .show();
                    }
                });
                viewHolder.getConvertView().setOnTouchListener(new ViewUtils.AbstractTouchHandler() {
                    @Override
                    public boolean handleTouch(MotionEvent event) {
                        slideToDelete(event,order,btn);
                        return true;
                    }
                });
            }

            @Override
            public boolean persistInsert(Order order) {
                return factory.addOrder(order);
            }

            @Override
            public boolean persistDelete(Order order) {
                return factory.delete(order);
            }
        };
        listView.setAdapter(adapter);
        if (order!=null){
            save(order);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_orders;
    }

    @Override
    public void search(String koy) {

    }

    public void save(Order order){
        adapter.add(order);
    }

    private void slideToDelete(MotionEvent event, Order order, Button btn) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchX1=event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float touchX2=event.getX();
                if (touchX1-touchX2>MIN_DISTANCE){
                    if (!isDelete){
                        btn.setVisibility(View.VISIBLE);
                        isDelete=true;
                    }
                }else {
                    if (btn.isShown()){
                        btn.setVisibility(View.GONE);
                        isDelete=false;
                    }else {
                        clickOrder(order);
                    }
                }

                break;
            default:
                break;
        }
    }

    private void clickOrder(Order order) {
        Cinema cinema= CinemaFactory.getInstance().getById(order.getCinemaId().toString());
        String content="["+order.getMovie()+"]"+order.getMovieTime()+"\n"+cinema.toString()+"票价"+order.getPrice()+"元";
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_qrcode,null);
        ImageView img=view.findViewById(R.id.dialog_qrcode_img);
        img.setImageBitmap(AppUtils.createQRCodeBitmap(content,300,300));
        new AlertDialog.Builder(getActivity())
                .setView(view).show();
    }








}
