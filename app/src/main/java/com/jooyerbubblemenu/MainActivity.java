package com.jooyerbubblemenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView more;
    private TopRightMenu mTopRightMenu;
    private List<MenuItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMenu();
        more = (ImageView) findViewById(R.id.more);
        mTopRightMenu = new TopRightMenu(this);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mTopRightMenu.setWidth(400)
                      .setHeight(600)
                      .setShowIcon(true)
                      .setShowAnimationStyle(true)
                      .setAnimationStyle(R.style.TopRightMenu_Anim)
                      .setShowBackground(true)
                      .setArrowPosition(345f)
                      .addMenuItems(mItems)
                      .addMenuItem(new MenuItem(R.mipmap.facetoface,"面对面快传"))
                      .addMenuItem(new MenuItem(R.mipmap.pay,"付款"))
                      .setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
                          @Override
                          public void onTopRightMenuItemClick(int position) {
                              Toast.makeText(MainActivity.this, " 点击位置 :" + position, Toast.LENGTH_SHORT).show();
                          }
                      });

                mTopRightMenu.show(v,null,null);
            }
        });
    }

    private void initMenu() {
        mItems = new ArrayList<>();
        mItems.add(new MenuItem(R.mipmap.multichat, "发起多人聊天"));
        mItems.add(new MenuItem(R.mipmap.addmember, "加好友"));
        mItems.add(new MenuItem(R.mipmap.qr_scan, "扫一扫"));
    }
}
