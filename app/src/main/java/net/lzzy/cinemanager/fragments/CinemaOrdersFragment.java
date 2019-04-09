package net.lzzy.cinemanager.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.security.PublicKey;
import java.util.List;

/**
 * Created by lzzy_gxy on 2019/4/2.
 * Description:
 */
public class CinemaOrdersFragment extends BaseFragment{
    private String cinemaId;
    private static final String ARG_CINEMA_ID="argCinemaId";

    public static CinemaOrdersFragment newInstance(String cinemaId){
        CinemaOrdersFragment fragment=new CinemaOrdersFragment();
        Bundle args=new Bundle();
        args.putString(ARG_CINEMA_ID,cinemaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            cinemaId=getArguments().getString(ARG_CINEMA_ID);
        }
    }

    @Override
    protected void populate() {
        ListView listView = find(R.id.fragment_cinema_orders_lv);
        View empty=find(R.id.fragment_cinema_orders_none);
        listView.setEmptyView(empty);
        List<Order> orders= OrderFactory.getInstance().getOrdersByCinema(cinemaId);
        GenericAdapter<Order> adapter=new GenericAdapter<Order>(getActivity(),R.layout.order_item,orders) {
            @Override
            public void populate(ViewHolder viewHolder, Order order) {
                viewHolder.setTextView(R.id.order_item_tv_name,order.getMovie())
                        .setTextView(R.id.order_item_tv_location,order.getMovieTime());
            }

            @Override
            public boolean persistInsert(Order order) {
                return false;
            }

            @Override
            public boolean persistDelete(Order order) {
                return false;
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_cinema_orders;
    }

    @Override
    public void search(String kw) {

    }
}
