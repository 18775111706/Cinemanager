package net.lzzy.cinemanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;
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
    private List<Cinema> cinemas;
    private CinemaFactory factory = CinemaFactory.getInstance();
    private ListView lv;

    @Override
    protected void populate() {
            cinemas=factory.get();
            lv = find(R.id.activity_cinemas_lv);
            View empty = find(R.id.activity_cinemas_tv_none);
            lv.setEmptyView(empty);
            cinemas=factory.get();
            GenericAdapter<Cinema> adapter=new GenericAdapter<Cinema>(getActivity(),R.layout.cinema_item,cinemas) {
                @Override
                public void populate(ViewHolder holder, Cinema cinema) {
                    holder.setTextView(R.id.activity_cinemas_lv,cinema.getName())
                            .setTextView(R.id.activity_cinemas_tv_none,cinema.getLocation());
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
        }



    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_cinemas;
    }
}
