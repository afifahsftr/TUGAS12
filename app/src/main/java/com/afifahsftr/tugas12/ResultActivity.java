package com.afifahsftr.tugas12;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewResult;
    private TextView emailTextView, phoneTextView;
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Menemukan TextViews
        textViewResult = findViewById(R.id.textViewResult);
        buttonReset = findViewById(R.id.buttonReset);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);

        // Mengambil data dari Intent
        String nim = getIntent().getStringExtra("nim");
        String nama = getIntent().getStringExtra("nama");
        String semester = getIntent().getStringExtra("semester");
        String period = getIntent().getStringExtra("period");
        int nilaiAkhir = getIntent().getIntExtra("nilaiAkhir", 0);
        String grade = getIntent().getStringExtra("grade");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");

        // Membuat string hasil untuk ditampilkan pada TextView
        String result = "<b>NIM:</b> " + nim + "<br><b>Nama:</b> " + nama + "<br><b>Semester:</b> " + semester
                + "<br><b>Tahun Ajaran:</b> " + period + "<br><b>Nilai Akhir:</b> " + nilaiAkhir
                + "<br><b>Grade:</b> " + grade;

        // Menampilkan hasil nilai akhir dengan format HTML
        textViewResult.setText(HtmlCompat.fromHtml(result, HtmlCompat.FROM_HTML_MODE_LEGACY));

        // Menangani tampilkan email jika ada
        if (email != null && !email.isEmpty()) {
            emailTextView.setText(email);
            emailTextView.setVisibility(View.VISIBLE);
        } else {
            emailTextView.setVisibility(View.GONE);
        }

        // Menangani tampilkan phone jika ada
        if (phone != null && !phone.isEmpty()) {
            phoneTextView.setText(phone);
            phoneTextView.setVisibility(View.VISIBLE);
        } else {
            phoneTextView.setVisibility(View.GONE);
        }

        // Aksi klik email
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
                startActivity(Intent.createChooser(emailIntent, "Send email"));
            }
        });

        // Aksi klik phone
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(dialIntent);
            }
        });

        // Aksi klik tombol Reset
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult.setText("");
                emailTextView.setText("");
                phoneTextView.setText("");
            }
        });
    }
}