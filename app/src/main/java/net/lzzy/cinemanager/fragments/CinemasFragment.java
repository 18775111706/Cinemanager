package net.lzzy.cinemanager.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.fragment.app.Fragment;

import com.hp.hpl.sparta.xpath.ParentNodeTest;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.activities.MainActivity;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class CinemasFragment extends BaseFragment {
    private GenericAdapter<Cinema> adapter;
    private List<Cinema> cinemas;
    private CinemaFactory factory = CinemaFactory.getInstance();
    private ListView lv;
    private static final String ARG_NEW_CINEMA="argNewCinema";
    private Cinema cinema;
    private OnCinemaSelectedListener listener;
    public CinemasFragment(){}
    public CinemasFragment(Cinema cinema){
        this.cinema = cinema;
    }
    public static CinemasFragment newInstance(Cinema cinema){
        CinemasFragment fragment=new CinemasFragment();
        Bundle args=new Bundle();
        args.putParcelable(ARG_NEW_CINEMA,cinema);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void populate() {

            cinemas=factory.get();
            lv = find(R.id.activity_cinemas_lv);
            View empty = find(R.id.activity_cinemas_tv_none);
            lv.setEmptyView(empty);
            //cinemas=factory.get();
            adapter=new GenericAdapter<Cinema>(getActivity(),R.layout.cinema_item,cinemas) {
                @Override
                public void populate(ViewHolder holder, Cinema cinema) {
                    holder.setTextView(R.id.item_cinemas_name,cinema.getName())
                            .setTextView(R.id.item_cinemas_location,cinema.getLocation());
                }

                @Override
                public boolean persistInsert(Cinema cinema) {
                    return factory.addCinema(cinema);
                }

                @Override
                public boolean persistDelete(Cinema cinema) {
                    return factory.deleteCinema(cinema);
                }
            };
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onCinemaSelected(adapter.getItem(position).getId().toString());
            }
        });


        if (cinema !=null){
                save(cinema);
            }
        }
    public void save(Cinema cinema){
        adapter.add(cinema);
    }



    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_cinemas;
    }

    @Override
    public void search(String koy) {
        cinemas.clear();
        if (TextUtils.isEmpty(koy)){
            cinemas.addAll(factory.get());

        }else {
            cinemas.addAll(factory.searchCinemas(koy));

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCinemaSelectedListener){
            listener=(OnCinemaSelectedListener) context;
        }else {
            throw new ClassCastException(context.toString()+"必须实现OnCinemaSelectedListener");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    public interface OnCinemaSelectedListener{
        void onCinemaSelected(String cinemaId);
    }
}
