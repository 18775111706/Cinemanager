package net.lzzy.cinemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import net.lzzy.cinemanager.R;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private LinearLayout layoutMenu;
    private TextView tvTitle;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitleMenu();
    }
    private void setTitleMenu(){
        layoutMenu=findViewById(R.id.bar_title_layout_menu);
        layoutMenu.setVisibility(View.GONE);
        findViewById(R.id.bar_title_iv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible=layoutMenu.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE;
                layoutMenu.setVisibility(visible);
            }
        });
        tvTitle=findViewById(R.id.bar_title_tv);
        tvTitle.setText(R.string.bar_title_tv_my_order);
        searchView=findViewById(R.id.bar_title_sv);
        findViewById(R.id.bar_title_tv_my_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_add_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_add_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}

