package com.example.liferestart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liferestart.entity.EventToAge;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private static final String TAG = "EventAdapter";
    public EventAdapter(List<EventToAge> mEventList) {
        this.mEventList = mEventList;
        //Log.d(TAG, mEventList.get(0).toString());
    }

    public EventAdapter() {
        this.mEventList = new ArrayList<EventToAge>();
    }

    private List<EventToAge> mEventList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_to_age, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.eventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                EventToAge eventToAge = mEventList.get(position);
                Toast.makeText(view.getContext(), eventToAge.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //Log.d(TAG, mEventList.get(0).toString());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventToAge eventToAge = mEventList.get(position);
        holder.setData(eventToAge);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView eventView;
        TextView ageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            eventView = itemView.findViewById(R.id.eventText);
            ageView = itemView.findViewById(R.id.eventAge);
        }
        public void setData(EventToAge eventToAge){
            eventView.setText(eventToAge.getEvent().getEventDescription());
            ageView.setText(""+eventToAge.getAge()+"岁:");
        }
    }

    public void addEventToAge(EventToAge eventToAge){
        if(mEventList==null){
            mEventList = new ArrayList<EventToAge>();
        }
        mEventList.add(eventToAge);
        notifyItemInserted(mEventList.size());
    }
    public void addEventToAgeList(List<EventToAge> eventList){
        for(EventToAge eventToAge:eventList)
            addEventToAge(eventToAge);

    }
}
