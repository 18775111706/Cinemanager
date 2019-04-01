package net.lzzy.cinemanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddOrdersFragment extends BaseFragment {
    private OnFragmentInteractionLisener listener;
    public AddOrdersFragment(){}

    @Override
    protected void populate() {

    }

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public void search(String koy) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener=(OnFragmentInteractionLisener) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"必须实现OnFragmentInteractionListener ");
        }

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
    }
}

