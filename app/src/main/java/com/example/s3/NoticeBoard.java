package com.example.s3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class NoticeBoard extends AppCompatActivity {
    private ArrayList<NoticeItem> exampleList;
private ImageView back;
    private RecyclerView mRecyclerView;
    private NoticeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
ImageView nxt;
    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
       /* back=findViewById(R.id.b);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        nxt=findViewById(R.id.r);
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(NoticeBoard.this,Attendance.class);
                startActivity(i);
            }
        });
        createExampleList();
        buildRecyclerView();

    }
        public void changeItem(int position, String text) {
            exampleList.get(position).changeText1(text);
            mAdapter.notifyItemChanged(position);
        }
        public void createExampleList(){

            exampleList = new ArrayList<>();
            exampleList.add(new NoticeItem(R.drawable.warn, "Event 1", "Detail"));
            exampleList.add(new NoticeItem(R.drawable.clean, "Event 2", "Detail"));
            exampleList.add(new NoticeItem(R.drawable.tree, "Event 3", "Detail"));
            exampleList.add(new NoticeItem(R.drawable.party, "Event 4", "Detail"));
            exampleList.add(new NoticeItem(R.drawable.warn, "Event 5", "Detail"));


        }
        public void buildRecyclerView() {
            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new NoticeAdapter(exampleList);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);



            mAdapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    changeItem(position, "Clicked");
                }
            });
        }
    }
