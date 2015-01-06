package com.paytouch.jalal.actors.util;

import android.widget.AbsListView;

/**
 * Created by Ben Cull
 * http://benjii.me/2010/08/endless-scrolling-listview-in-android/
 *
 * Edited by Jalal Souky
 * Minor changes were made as pagination is done by the fragment and a callback was defined
 * to be implemented by the ActorsFragment.
 */
public class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int mVisibleThreshold = 2;
    private int mPreviousTotal = 0;
    private boolean mIsLoading = true;

    private OnEndReachedListener mOnEndReachedListener;

    public EndlessScrollListener(OnEndReachedListener listener) {
        mOnEndReachedListener = listener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (mIsLoading) {
            if (totalItemCount > mPreviousTotal) {
                mIsLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }

        if (!mIsLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + mVisibleThreshold)) {
            mOnEndReachedListener.onEndReached();
            mIsLoading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public interface OnEndReachedListener {
        public void onEndReached();
    }
}