package ch.abertschi.toggleninja;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abertschi on 09/07/16.
 */
public class ToggleAdapter extends RecyclerView.Adapter<ToggleAdapter.ViewHolder> {
    private final View popup;
    private final Animation animShow;
    private final Animation animHide;
    private List<String> mDataset;
    private FragmentActivity context;
    private int popupIndex;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView toogleName;
        public ImageView toggleImage;

        public ViewHolder(View v) {
            super(v);
            toogleName = (TextView) v.findViewById(R.id.toggle_name);
            toggleImage = (ImageView) v.findViewById(R.id.toggle_image);
        }
    }

    public void add(int position, String item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ToggleAdapter(List<String> myDataset, FragmentActivity context
    ) {
        mDataset = myDataset;
        this.context = context;
        popup = context.findViewById(R.id.toggle_settings);
        popup.setVisibility(View.GONE);

        animShow = AnimationUtils.loadAnimation(context, R.anim.popup_show);
        animHide = AnimationUtils.loadAnimation(context, R.anim.popup_hide);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ToggleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.toggle_element, parent, false);

        // set the view's size, margins, paddings and layout parameters



        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = mDataset.get(position);
        holder.toogleName.setText(mDataset.get(position));
        holder.toggleImage.setOnClickListener(new View.OnClickListener() {
            {
            }

            @Override
            public void onClick(View v) {
                if (popup.getVisibility() != View.VISIBLE) {
                    popup.setVisibility(View.VISIBLE);
                    popup.startAnimation( animShow );
                } else {
                    popup.setVisibility(View.GONE);
                    popup.startAnimation( animHide );
                }

            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}