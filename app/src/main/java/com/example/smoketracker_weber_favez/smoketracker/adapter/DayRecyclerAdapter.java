package com.example.smoketracker_weber_favez.smoketracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smoketracker_weber_favez.R;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.util.RecyclerViewItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DayRecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

private List<DayEntity> data;
private RecyclerViewItemClickListener listener;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    TextView textView;
    ViewHolder(TextView textView) {
        super(textView);
        this.textView = textView;
    }
}

    public DayRecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final RecyclerAdapter.ViewHolder viewHolder = new RecyclerAdapter.ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        DayEntity item = data.get(position);
        Date datee =  item.getDate();
        String formatedDate = new SimpleDateFormat("dd-MM-yyyy").format(datee);

        holder.textView.setText(item.getId()+" "+ formatedDate);
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<DayEntity> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return DayRecyclerAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                    if (DayRecyclerAdapter.this.data instanceof UserEntity) {
                        return (DayRecyclerAdapter.this.data.get(oldItemPosition)).getDate().equals(
                                (data.get(newItemPosition)).getDate());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (DayRecyclerAdapter.this.data instanceof UserEntity) {
                        DayEntity newDay = data.get(newItemPosition);
                        DayEntity oldDay = DayRecyclerAdapter.this.data.get(newItemPosition);
                        return Objects.equals(newDay.getDate(), oldDay.getDate())
                                && Objects.equals(newDay.getId(), oldDay.getId());
                    }
                    return false;
                }
            });
            this.data = data;
            result.dispatchUpdatesTo(this);
        }
    }
}


