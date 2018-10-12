package com.example.hiephoangvan.demo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiephoangvan.demo.adapter.EmployeeAdapter;
import com.example.hiephoangvan.demo.api.RetrofitInstance;
import com.example.hiephoangvan.demo.api.Service;
import com.example.hiephoangvan.demo.model.Employee;
import com.example.hiephoangvan.demo.model.EmployeeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view_employee_list) RecyclerView recyclerView;
    EmployeeAdapter adapter;
    List<Employee> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new EmployeeAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);
        Call<EmployeeList> call = service.getEmployeeData(100);
        call.enqueue(new Callback<EmployeeList>() {
            @Override
            public void onResponse(Call<EmployeeList> call, Response<EmployeeList> response) {
                if (response.isSuccessful()){
                    updateList(response.body());
                } else {
                    Toast.makeText(MainActivity.this,"Response error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeList> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public void updateList(EmployeeList list){
        this.list.clear();
        this.list.addAll(list.getEmployeeList());
        this.adapter.notifyDataSetChanged();
    }
}
