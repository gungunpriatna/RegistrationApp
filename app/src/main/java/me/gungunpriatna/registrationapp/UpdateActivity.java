package me.gungunpriatna.registrationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {
    public static final String URL = "http://192.168.1.104:8181/belajar/android/api/";

    private RadioButton radioButton;
    private ProgressDialog progressDialog;

    @BindView(R.id.radioSesi)
    RadioGroup radioGroup;

    @BindView(R.id.radioPagi)
    RadioButton radioButtonPagi;

    @BindView(R.id.radioSiang)
    RadioButton radionButtonSiang;

    @BindView(R.id.editTextNPM)
    EditText editTextNPM;

    @BindView(R.id.editTextNama)
    EditText editTextNama;

    @BindView(R.id.editTextKelas)
    EditText editTextKelas;

    @OnClick(R.id.buttonUbah) void ubah() {
        //membuat progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //mengambil data dari edittext
        String npm = editTextNPM.getText().toString();
        String nama = editTextNama.getText().toString();
        String kelas = editTextKelas.getText().toString();

        int selectedId = radioGroup.getCheckedRadioButtonId();
        //mencari id radio button
        radioButton = findViewById(selectedId);
        String sesi = radioButton.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.ubah(npm, nama, kelas, sesi);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                progressDialog.dismiss();

                if (value.equals("1")){
                    Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ubah Data");

        Intent intent = getIntent();
        String npm = intent.getStringExtra("npm");
        String nama = intent.getStringExtra("nama");
        String kelas = intent.getStringExtra("kelas");
        String sesi = intent.getStringExtra("sesi");

        editTextNPM.setText(npm);
        editTextNama.setText(nama);
        editTextKelas.setText(kelas);

        if (sesi.equals("Pagi (09.00-11.00 WIB)")) {
            radioButtonPagi.setChecked(true);
        } else {
            radionButtonSiang.setChecked(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
