package com.demo.firebasehw.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.firebasehw.R;
import com.demo.firebasehw.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<User> users;
    private OnUserClickListener onUserClickListener;

    public UsersAdapter() {
        users = new ArrayList<>();
    }

    public interface OnUserClickListener {
        void onUserLongClick(int position);
    }

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewName.setText(user.getName());
        holder.textViewLastname.setText(user.getLastname());
        holder.textViewAge.setText(Integer.toString(user.getAge()));
        holder.textViewSex.setText(user.getSex());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewLastname;
        TextView textViewAge;
        TextView textViewSex;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLastname = itemView.findViewById(R.id.textViewLastname);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewSex = itemView.findViewById(R.id.textViewSex);

            if (onUserClickListener != null) {
                itemView.setOnLongClickListener(view -> {
                    onUserClickListener.onUserLongClick(getAdapterPosition());
                    return true;
                });
            }
        }
    }
}