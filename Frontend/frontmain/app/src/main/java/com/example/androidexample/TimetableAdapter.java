//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.androidexample.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder> {
//
//    private List<TimetableItem> timetableItems = new ArrayList<>();
//
//    public void addTimetableData(List<TimetableItem> data) {
//        timetableItems.clear();
//        timetableItems.addAll(data);
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        TimetableItem item = timetableItems.get(position);
//        holder.tvTime.setText(item.getTime());
//        holder.tvDay.setText(item.getDay());
//
//        if (item.getMeeting() != null) {
//            Meeting meeting = item.getMeeting();
//            holder.itemView.setBackgroundColor(Color.parseColor(meeting.getColor()));
//            holder.itemView.setOnClickListener(v -> {
//                // Handle click event for the meeting
//            });
//
//        } else {
//            holder.itemView.setBackgroundColor(Color.WHITE);
//            holder.itemView.setOnClickListener(null);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return timetableItems.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView tvTime;
//        TextView tvDay;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvTime = itemView.findViewById(R.id.tvTime);
//            tvDay = itemView.findViewById(R.id.tvDay);
//        }
//    }
//}

