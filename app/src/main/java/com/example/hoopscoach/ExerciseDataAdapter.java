package com.example.hoopscoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseDataAdapter extends RecyclerView.Adapter<ExerciseDataAdapter.ViewHolder2>{

    private final LayoutInflater inflater;
    private final List<ExerciseData> data;

    ExerciseDataAdapter(Context context, List<ExerciseData> data){
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExerciseDataAdapter.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ExerciseDataAdapter.ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseDataAdapter.ViewHolder2 holder, int position) {
        ExerciseData exerciseData = data.get(position);
        holder.title.setText(exerciseData.title);
        holder.number.setText(String.valueOf(exerciseData.number));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder{

        final TextView title;
        final TextView number;
        final EditText result;

        public ViewHolder2(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_exerciseName);
            number = itemView.findViewById(R.id.textView16);
            result = itemView.findViewById(R.id.editTextNumber);
        }
    }
}
