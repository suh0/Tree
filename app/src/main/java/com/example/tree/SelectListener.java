package com.example.tree;

import android.widget.LinearLayout;
import android.widget.TextView;

public interface SelectListener {
    void onItemClicked(ProductBgm productBgm, LinearLayout layout, TextView txtPrice);
}

