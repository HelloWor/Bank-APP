package com.example.bankapp;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;



   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

       Retrofit retrofit = null;
       try {
           retrofit = new Retrofit.Builder()
                   .baseUrl(AESUtils.decrypt("B6ADEAC5984F0DFCB52078DF1AB96862C328F5116C9340E96871BA4482CAC5AEC3A1C9CA9B8BD5EBB3766598C48C98FB08EC3A83130D1DCD858DF0E95E97B605"))
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
       } catch (Exception e) {
           e.printStackTrace();
       }


       GetData getdata = retrofit.create(GetData.class);

        Call<List<PlaceHolderPost>> call = getdata.getAccounts();

        call.enqueue(new Callback<List<PlaceHolderPost>>() {
            @Override
            public void onResponse(Call<List<PlaceHolderPost>> call, Response<List<PlaceHolderPost>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                String globalcontent = "";
                List<PlaceHolderPost> posts = response.body();
                for (PlaceHolderPost post : posts) {
                    String content = "";
                    content += post.getText()+"\n" ;
                    content += "Amount: "+ post.getAmount()+" ";
                    content += post.getCurrency()+"\n";
                    content += "Iban: " + post.getIban()+"\n\n";
                    globalcontent+=content;
                    textViewResult.append(content);

                }

                try {

                    FileOutputStream fileout=openFileOutput("161754P164160.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

                    // Command ton encrypt the datas
                    String encrypted = "";
                    try {
                        encrypted = AESUtils.encrypt(globalcontent);
                        Log.d("TEST", "encrypted:" + encrypted);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    outputWriter.write(encrypted);
                    outputWriter.close();




                } catch (Exception e) {
                    e.printStackTrace();
                }





                View refreshButton = findViewById(R.id.button_refresh);

                refreshButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                }









            @Override
            public void onFailure(Call<List<PlaceHolderPost>> call, Throwable t) {
                textViewResult.setText("Your internet connection is offline");
                View refreshButton = findViewById(R.id.button_refresh);

                refreshButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });
            }

            
            
            
            
            
        });
    }
}