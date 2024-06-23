package com.game.createcharacter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Spinner s_class, s_gender;
    Button btn_confirm;
    RadioButton rb_none, rb_spear, rb_vines, rb_crystal, rb_blade, rb_stinger, rb_trident, rb_scepter, rb_needle, rb_bowarrow, rb_doll, rb_tome;
    SeekBar sb_hp, sb_mana, sb_phydam, sb_magdam, sb_phydef, sb_magdef, sb_movspd;
    TextView tv_upload, tv_hp, tv_mana, tv_phydam, tv_magdam, tv_phydef, tv_magdef, tv_movspd;
    ImageView iv_upload;
    EditText et_name;
    String weaponType;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setSpinnerAdapters();
        setSeekBarListeners();

        iv_upload.setOnClickListener(v -> openGallery());

        setConfirmButtonClickListener();
    }

    private void initializeViews() {
        tv_upload = findViewById(R.id.tv_upload);
        iv_upload = findViewById(R.id.iv_upload);
        et_name = findViewById(R.id.et_name);
        s_class = findViewById(R.id.s_class);
        s_gender = findViewById(R.id.s_gender);
        tv_hp = findViewById(R.id.tv_hp);
        sb_hp = findViewById(R.id.sb_hp);
        tv_mana = findViewById(R.id.tv_mana);
        sb_mana = findViewById(R.id.sb_mana);
        tv_phydam = findViewById(R.id.tv_phydam);
        sb_phydam = findViewById(R.id.sb_phydam);
        tv_magdam = findViewById(R.id.tv_magdam);
        sb_magdam = findViewById(R.id.sb_magdam);
        tv_phydef = findViewById(R.id.tv_phydef);
        sb_phydef = findViewById(R.id.sb_phydef);
        tv_magdef = findViewById(R.id.tv_magdef);
        sb_magdef = findViewById(R.id.sb_magdef);
        tv_movspd = findViewById(R.id.tv_movspd);
        sb_movspd = findViewById(R.id.sb_movspd);

        rb_none = findViewById(R.id.rb_none);
        rb_spear = findViewById(R.id.rb_spear);
        rb_vines = findViewById(R.id.rb_vines);
        rb_crystal = findViewById(R.id.rb_crystal);
        rb_blade = findViewById(R.id.rb_blade);
        rb_stinger = findViewById(R.id.rb_stinger);
        rb_trident = findViewById(R.id.rb_trident);
        rb_scepter = findViewById(R.id.rb_scepter);
        rb_needle = findViewById(R.id.rb_needle);
        rb_bowarrow = findViewById(R.id.rb_bowarrow);
        rb_doll = findViewById(R.id.rb_doll);
        rb_tome = findViewById(R.id.rb_tome);

        btn_confirm = findViewById(R.id.btn_confirm);
    }

    private void setSpinnerAdapters() {
        String[] classes = {"Tank", "Fighter", "Assassin", "Mage", "Marksman", "Support"};
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classes);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_class.setAdapter(classAdapter);

        String[] genders = {"Unknown", "Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_gender.setAdapter(genderAdapter);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    private void setConfirmButtonClickListener() {
        btn_confirm.setOnClickListener(v -> {
            if (rb_spear.isChecked()) {
                weaponType = "Spear";
            } else if (rb_vines.isChecked()) {
                weaponType = "Vines";
            } else if (rb_crystal.isChecked()) {
                weaponType = "Crystal";
            } else if (rb_blade.isChecked()) {
                weaponType = "Blade";
            } else if (rb_stinger.isChecked()) {
                weaponType = "Stinger";
            } else if (rb_trident.isChecked()) {
                weaponType = "Trident";
            } else if (rb_scepter.isChecked()) {
                weaponType = "Scepter";
            } else if (rb_needle.isChecked()) {
                weaponType = "Needle";
            } else if (rb_bowarrow.isChecked()) {
                weaponType = "Bow & Arrow";
            } else if (rb_doll.isChecked()) {
                weaponType = "Doll";
            } else if (rb_tome.isChecked()) {
                weaponType = "Tome";
            } else {
                weaponType = "None";
            }

            String characterName = et_name.getText().toString();
            String className = s_class.getSelectedItem().toString();
            String gender = s_gender.getSelectedItem().toString();
            int hp = sb_hp.getProgress() + 1000;
            int mana = sb_mana.getProgress();
            int phyDam = sb_phydam.getProgress() + 100;
            int magDam = sb_magdam.getProgress();
            int phyDef = sb_phydef.getProgress() + 10;
            int magDef = sb_magdef.getProgress() + 10;
            int moveSpeed = sb_movspd.getProgress() + 100;
            String weapon = weaponType;

            Intent intent = new Intent(MainActivity.this, ViewCharacter.class);
            intent.putExtra("charName", characterName);
            intent.putExtra("charClass", className);
            intent.putExtra("charGender", gender);
            intent.putExtra("charHP", hp);
            intent.putExtra("charMana", mana);
            intent.putExtra("charPhyDam", phyDam);
            intent.putExtra("charMagDam", magDam);
            intent.putExtra("charPhyDef", phyDef);
            intent.putExtra("charMagDef", magDef);
            intent.putExtra("charMovSpd", moveSpeed);
            intent.putExtra("charWeapon", weapon);
            startActivity(intent);
        });
    }

    private void setSeekBarListeners() {
        sb_hp.setMax(9000);
        sb_hp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_hp.setText(String.valueOf(progress + 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_mana.setMax(5000);
        sb_mana.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_mana.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_phydam.setMax(400);
        sb_phydam.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_phydam.setText(String.valueOf(progress + 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_magdam.setMax(500);
        sb_magdam.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_magdam.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_phydef.setMax(90);
        sb_phydef.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_phydef.setText(String.valueOf(progress + 10));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_magdef.setMax(90);
        sb_magdef.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_magdef.setText(String.valueOf(progress + 10));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_movspd.setMax(200);
        sb_movspd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_movspd.setText(String.valueOf(progress + 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                iv_upload.setImageBitmap(bitmap);
                tv_upload.setVisibility(View.INVISIBLE);
                saveImageToInternalStorage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveImageToInternalStorage(Bitmap bitmap) {
        String filename = "selected_image.jpg";
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }
}
