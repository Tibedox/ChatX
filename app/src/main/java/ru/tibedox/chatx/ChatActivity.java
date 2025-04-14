package ru.tibedox.chatx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    ListView listView;
    EditText editMessage;
    String name;
    String message;
    List<DataFromBase> db = new ArrayList<>();
    List<String> allMessages = new ArrayList<>();
    Retrofit retrofit;
    MyApi myApi;
    int numMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainchat), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);
        editMessage = findViewById(R.id.editMessage);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("name")) {
            name = intent.getStringExtra("name");
        }
        retrofit = new Retrofit.Builder()
                .baseUrl("https://sch120.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(MyApi.class);
    }

    public void sendChatMessage(View view){
        if(editMessage.getText().toString().isEmpty()) return;
        message = editMessage.getText().toString();
        sendToDataBase();
        editMessage.setText("");
    }

    private void sendToDataBase() {
        myApi.sendQuery(name, message).enqueue(new Callback<List<DataFromBase>>() {
            @Override
            public void onResponse(Call<List<DataFromBase>> call, Response<List<DataFromBase>> response) {
                db = response.body();
                updateListView();
            }

            @Override
            public void onFailure(Call<List<DataFromBase>> call, Throwable t) {
            }
        });
    }

    private void updateListView(){
        if(numMessages < db.size()){
            allMessages.clear();
            for(DataFromBase a: db) allMessages.add(a.name+"     "+a.created+"\n"+a.message);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, allMessages);
            listView.setAdapter(adapter);
            //scrollDown();
            numMessages=db.size();
        }
    }
}