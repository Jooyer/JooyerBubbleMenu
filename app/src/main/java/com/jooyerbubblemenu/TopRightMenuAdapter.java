package com.jooyerbubblemenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 菜单适配器
 * Created by Jooyer on 2017/2/10
 */
public class TopRightMenuAdapter extends RecyclerView.Adapter<TopRightMenuAdapter.TopRightMenuHolder> {
    private Context mContext;
    private List<MenuItem> mItems;
    private boolean isShowIcon;
    private TopRightMenu mTopRightMenu;
    private OnTopRightMenuItemClickListener mItemClickListener;

    public TopRightMenuAdapter(Context context, List<MenuItem> items, boolean isShowIcon, TopRightMenu topRightMenu) {
        mContext = context;
        mItems = items;
        this.isShowIcon = isShowIcon;
        mTopRightMenu = topRightMenu;
    }

    @Override
    public TopRightMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopRightMenuHolder(LayoutInflater.from(mContext).inflate(R.layout.top_right_menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final TopRightMenuHolder holder, int position) {
        final MenuItem menuItem = mItems.get(position);
        if (isShowIcon) {
            holder.iv_menu_item_icon.setVisibility(View.VISIBLE);
            int resId = menuItem.getIcon();
            holder.iv_menu_item_icon.setBackgroundResource(resId < 0 ? 0:resId);
        }else {
            holder.iv_menu_item_icon.setVisibility(View.GONE);
        }

        holder.tv_menu_item_text.setText(menuItem.getContent());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mItemClickListener) {
                    mItemClickListener.onTopRightMenuItemClick(holder.getAdapterPosition());
                    mTopRightMenu.dismiss();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    static class TopRightMenuHolder extends RecyclerView.ViewHolder {
        private ImageView iv_menu_item_icon;
        private TextView tv_menu_item_text;
        public TopRightMenuHolder(View itemView) {
            super(itemView);
            iv_menu_item_icon  = (ImageView) itemView.findViewById(R.id.iv_menu_item_icon);
            tv_menu_item_text  = (TextView) itemView.findViewById(R.id.tv_menu_item_text);
        }
    }

    public void setItemData(List<MenuItem> itemList) {
        mItems = itemList;
        notifyDataSetChanged();
    }

    public void setShowIcon(boolean isShowIcon) {
        this.isShowIcon = isShowIcon;
        notifyDataSetChanged();
    }

    public void setOnTopRightMenuItemClickListener(OnTopRightMenuItemClickListener listener) {
        mItemClickListener = listener;
    }

}
