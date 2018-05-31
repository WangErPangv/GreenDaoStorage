package wby.laowang.greendaoxmt.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import wby.laowang.greendaoxmt.DUser;
import wby.laowang.greendaoxmt.R;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements View.OnClickListener {

    private List<DUser> list;
    private Context context;
    private OnItemLinister onItemLinister;

    public MyAdapter(List<DUser> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (onItemLinister != null){
            onItemLinister.OnSetItem((Integer) v.getTag());
        }
    }

    public interface OnItemLinister{
        void OnSetItem(int postion);

    }

    public void setOnItemLinister(OnItemLinister onItemLinister) {
        this.onItemLinister = onItemLinister;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rec_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        view.setOnClickListener(this);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        holder.rec_name.setText("ID: "+list.get(position).getId()+"   "+list.get(position).getName());
        holder.rec_age.setText(list.get(position).getAge());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
