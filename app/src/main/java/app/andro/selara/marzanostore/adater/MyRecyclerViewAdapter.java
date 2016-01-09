package app.andro.selara.marzanostore.adater;

/**
 * Created by kilatan on 1/4/16.
 */
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import app.andro.selara.marzanostore.R;
import app.andro.selara.marzanostore.app.AppController;
import app.andro.selara.marzanostore.model.Resto;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Resto> mDataset;
    private static MyClickListener myClickListener;

    static ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView nama;
        TextView desc;
        TextView type;
        NetworkImageView thumbNail;

        public DataObjectHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.namat);
            desc = (TextView) itemView.findViewById(R.id.desct);
            type = (TextView) itemView.findViewById(R.id.typet);
            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            thumbNail = (NetworkImageView) itemView
                    .findViewById(R.id.thumbnail2);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<Resto> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.nama.setText(mDataset.get(position).getTitle());
        holder.desc.setText(mDataset.get(position).getDesc());
        holder.type.setText(mDataset.get(position).getType());
        holder.thumbNail.setImageUrl(mDataset.get(position).getImage(), imageLoader);

    }

    public void addItem(Resto dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}