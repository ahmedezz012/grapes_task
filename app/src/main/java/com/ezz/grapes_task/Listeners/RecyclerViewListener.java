package com.ezz.grapes_task.Listeners;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class RecyclerViewListener extends RecyclerView.OnScrollListener{

    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int Must_Be_Visiable = 5;

    private int prev_Item_Count = 0;

    private boolean isLoading = true;

    public RecyclerViewListener() {
        super();
    }

    public RecyclerViewListener(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int total_Items = staggeredGridLayoutManager.getItemCount();
        int x[]=staggeredGridLayoutManager.findLastVisibleItemPositions(null);
        int last_Visible = get_The_Last_Visible_Item(staggeredGridLayoutManager.findLastVisibleItemPositions(null));
        if (isLoading  &&  (prev_Item_Count<total_Items))
        {
            isLoading = false;

            prev_Item_Count = total_Items;
        }
        if(!isLoading && ((last_Visible+Must_Be_Visiable)>total_Items))
        {
            isLoading = true;

            LoadMoreProducts();
        }

    }
    public int get_The_Last_Visible_Item(int []x)
    {
        int last = x[0];
        for (int i=1;i<x.length;i++)
        {
            if(x[i]>last)
                last=x[i];
        }
        return last;
    }
    public void reset(){

        isLoading = true;

        prev_Item_Count = 0;
    }

    public abstract void LoadMoreProducts();
}
