package com.chiemy.demo.study;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            PackageInfo packageInfo =
                    getPackageManager()
                            .getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            MainAdapter adapter = new MainAdapter(packageInfo.activities);
            recyclerView.setAdapter(adapter);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private class MainAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        private ActivityInfo [] activities;
        public MainAdapter(ActivityInfo[] activities) {
            this.activities = activities;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ItemViewHolder.create(parent);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.render(activities[position]);
        }

        @Override
        public int getItemCount() {
            return activities.length;
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ActivityInfo activityInfo;
        public static ItemViewHolder create(ViewGroup parent) {
            return new ItemViewHolder(
                    LayoutInflater
                            .from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                Intent intent = new Intent(v.getContext(), Class.forName(activityInfo.name));
                v.getContext().startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void render(ActivityInfo info) {
            activityInfo = info;
            String name = "";
            if (info.labelRes > 0) {
                name = itemView.getContext().getString(info.labelRes);
            }
            ((TextView)itemView).setText(name);
        }

    }

}
