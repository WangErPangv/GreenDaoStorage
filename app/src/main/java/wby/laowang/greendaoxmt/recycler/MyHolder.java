package wby.laowang.greendaoxmt.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import wby.laowang.greendaoxmt.R;

public class MyHolder extends RecyclerView.ViewHolder {

    public TextView rec_name;
    public TextView rec_age;

    public MyHolder(View itemView) {
        super(itemView);

        rec_name = itemView.findViewById(R.id.rec_name);
        rec_age = itemView.findViewById(R.id.rec_age);
    }
}
