package com.afifahsftr.tugas12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputNIM, inputNama, inputPresensi, inputTugas, inputUTS, inputUAS, inputEmail, inputPhone;
    private RadioGroup radioGroupSemester;
    private Spinner spinnerPeriod, spinnerFakultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNIM = findViewById(R.id.input_nim);
        inputNama = findViewById(R.id.input_nama);
        inputPresensi = findViewById(R.id.input_presensi);
        inputTugas = findViewById(R.id.input_tugas);
        inputUTS = findViewById(R.id.input_uts);
        inputUAS = findViewById(R.id.input_uas);
        radioGroupSemester = findViewById(R.id.radioGroupSemester);
        spinnerPeriod = findViewById(R.id.spinner_tahun_ajaran);
        spinnerFakultas = findViewById(R.id.spinner_fakultas);  // Spinner Fakultas
        inputEmail = findViewById(R.id.input_email);  // EditText Email
        inputPhone = findViewById(R.id.input_phone);  // EditText Phone

        Button btnHitung = findViewById(R.id.btn_hitung);

        // Setup Spinner Tahun Ajaran
        String[] tahunAjaran = {"2021/2022", "2022/2023", "2023/2024", "2024/2025"};
        ArrayAdapter<String> periodAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tahunAjaran);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(periodAdapter);

        // Setup Spinner Fakultas
        ArrayAdapter<CharSequence> fakultasAdapter = ArrayAdapter.createFromResource(this,
                R.array.fakultas_array, android.R.layout.simple_spinner_item);
        fakultasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFakultas.setAdapter(fakultasAdapter);

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    calculateGrade();
                }
            }
        });
    }

    private boolean validateInputs() {
        // Validasi untuk semua input
        if (inputNIM.getText().toString().trim().isEmpty()) {
            inputNIM.setError("NIM wajib diisi");
            return false;
        }
        if (!inputNIM.getText().toString().matches("\\d+")) {
            inputNIM.setError("NIM hanya boleh berupa angka");
            return false;
        }
        if (inputNama.getText().toString().trim().isEmpty()) {
            inputNama.setError("Nama wajib diisi");
            return false;
        }
        if (!inputNama.getText().toString().matches("[a-zA-Z\\s]+")) {
            inputNama.setError("Nama hanya boleh berisi huruf");
            return false;
        }
        if (radioGroupSemester.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Semester wajib dipilih", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinnerPeriod.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Tahun Ajaran wajib dipilih", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinnerFakultas.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Fakultas wajib dipilih", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateNumericInput(inputPresensi, "Nilai presensi")) return false;
        if (!validateNumericInput(inputTugas, "Nilai tugas")) return false;
        if (!validateNumericInput(inputUTS, "Nilai UTS")) return false;
        if (!validateNumericInput(inputUAS, "Nilai UAS")) return false;
        if (inputEmail.getText().toString().trim().isEmpty()) {
            inputEmail.setError("Email wajib diisi");
            return false;
        }
        if (inputPhone.getText().toString().trim().isEmpty()) {
            inputPhone.setError("Phone wajib diisi");
            return false;
        }
        return true;
    }

    private boolean validateNumericInput(EditText editText, String fieldName) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            editText.setError(fieldName + " wajib diisi");
            return false;
        }
        try {
            int numericValue = Integer.parseInt(value);
            if (numericValue < 10 || numericValue > 100) {
                editText.setError(fieldName + " harus antara 10 dan 100");
                return false;
            }
        } catch (NumberFormatException e) {
            editText.setError(fieldName + " harus berupa angka");
            return false;
        }
        return true;
    }

    private void calculateGrade() {
        int presensi = Integer.parseInt(inputPresensi.getText().toString());
        int tugas = Integer.parseInt(inputTugas.getText().toString());
        int uts = Integer.parseInt(inputUTS.getText().toString());
        int uas = Integer.parseInt(inputUAS.getText().toString());

        double nilaiAkhir = (0.1 * presensi) + (0.2 * tugas) + (0.3 * uts) + (0.4 * uas);
        int nilaiAkhirInt = (int) Math.ceil(nilaiAkhir);

        String grade;
        if (nilaiAkhirInt >= 85) grade = "A";
        else if (nilaiAkhirInt >= 70) grade = "B";
        else if (nilaiAkhirInt >= 50) grade = "C";
        else grade = "D";

        RadioButton selectedSemester = findViewById(radioGroupSemester.getCheckedRadioButtonId());
        String semester = selectedSemester.getText().toString();
        String period = spinnerPeriod.getSelectedItem().toString();
        String fakultas = spinnerFakultas.getSelectedItem().toString();  // Fakultas yang dipilih
        String email = inputEmail.getText().toString();  // Email yang dimasukkan
        String phone = inputPhone.getText().toString();  // Phone yang dimasukkan

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("nim", inputNIM.getText().toString());
        intent.putExtra("nama", inputNama.getText().toString());
        intent.putExtra("semester", semester);
        intent.putExtra("period", period);
        intent.putExtra("fakultas", fakultas);  // Fakultas ke ResultActivity
        intent.putExtra("email", email);  // Email ke ResultActivity
        intent.putExtra("phone", phone);  // Phone ke ResultActivity
        intent.putExtra("nilaiAkhir", nilaiAkhirInt);
        intent.putExtra("grade", grade);
        startActivity(intent);
    }
}