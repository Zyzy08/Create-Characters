package com.game.createcharacter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class ViewCharacter extends AppCompatActivity {

    Button btn_back;
    ImageView iv_icon;
    TextView tv_charname, tv_charclass, tv_chargender, tv_charhp, tv_charmana, tv_charphydam, tv_charmagdam, tv_charphydef, tv_charmagdef, tv_charmovspd, tv_charweapon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_character);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setBackButtonListener();
        setCharacterData();

        String filename = "selected_image.jpg";
        File file = new File(getFilesDir(), filename);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        iv_icon.setImageBitmap(bitmap);
    }

    private void initializeViews() {
        btn_back = findViewById(R.id.btn_back);
        iv_icon = findViewById(R.id.iv_icon);
        tv_charname = findViewById(R.id.tv_charname);
        tv_charclass = findViewById(R.id.tv_charclass);
        tv_chargender = findViewById(R.id.tv_chargender);
        tv_charhp = findViewById(R.id.tv_charhp);
        tv_charmana = findViewById(R.id.tv_charmana);
        tv_charphydam = findViewById(R.id.tv_charphydam);
        tv_charmagdam = findViewById(R.id.tv_charmagdam);
        tv_charphydef = findViewById(R.id.tv_charphydef);
        tv_charmagdef = findViewById(R.id.tv_charmagdef);
        tv_charmovspd = findViewById(R.id.tv_charmovspd);
        tv_charweapon = findViewById(R.id.tv_charweapon);
    }

    private void setBackButtonListener() {
        btn_back.setOnClickListener(v -> {
            Intent intent = new Intent(ViewCharacter.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void setCharacterData() {
        Intent intent = getIntent();
        if (intent != null) {
            String charName = intent.getStringExtra("charName");
            String charClass = intent.getStringExtra("charClass");
            String charGender = intent.getStringExtra("charGender");
            int charHP = intent.getIntExtra("charHP", 0);
            int charMana = intent.getIntExtra("charMana", 0);
            int charPhyDam = intent.getIntExtra("charPhyDam", 0);
            int charMagDam = intent.getIntExtra("charMagDam", 0);
            int charPhyDef = intent.getIntExtra("charPhyDef", 0);
            int charMagDef = intent.getIntExtra("charMagDef", 0);
            int charMovSpd = intent.getIntExtra("charMovSpd", 0);
            String charWeapon = intent.getStringExtra("charWeapon");

            tv_charname.setText(String.format("Character Name: %s", charName));
            tv_charclass.setText(String.format("Class: %s", charClass));
            tv_chargender.setText(String.format("Gender: %s", charGender));
            tv_charhp.setText(String.format("HP: %s", charHP));
            tv_charmana.setText(String.format("Mana: %s", charMana));
            tv_charphydam.setText(String.format("Physical Damage: %s", charPhyDam));
            tv_charmagdam.setText(String.format("Magic Damage: %s", charMagDam));
            tv_charphydef.setText(String.format("Physical Defense: %s", charPhyDef));
            tv_charmagdef.setText(String.format("Magic Defense: %s", charMagDef));
            tv_charmovspd.setText(String.format("Movement Speed: %s", charMovSpd));
            tv_charweapon.setText(String.format("Weapon: %s", charWeapon));
        }
    }
}
