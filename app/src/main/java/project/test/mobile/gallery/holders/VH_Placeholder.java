package project.test.mobile.gallery.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.test.mobile.R;

/**
 * Created by Mayur on 24-10-2017.
 */

public class VH_Placeholder extends RecyclerView.ViewHolder {

    @BindView(R.id.shimmer1)
    public ShimmerFrameLayout shimmer1;

    @BindView(R.id.shimmer2)
    public ShimmerFrameLayout shimmer2;

    @BindView(R.id.shimmer3)
    public ShimmerFrameLayout shimmer3;

    @BindView(R.id.shimmer4)
    public ShimmerFrameLayout shimmer4;

    public VH_Placeholder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
