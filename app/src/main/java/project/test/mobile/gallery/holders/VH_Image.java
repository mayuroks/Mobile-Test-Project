package project.test.mobile.gallery.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.test.mobile.R;

/**
 * Created by Mayur on 24-10-2017.
 */

public class VH_Image extends RecyclerView.ViewHolder {

    @BindView(R.id.sdvThumbnail)
    public SimpleDraweeView sdvThumbnail;

    @BindView(R.id.tvDescription)
    public TextView tvDescription;

    public VH_Image(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
