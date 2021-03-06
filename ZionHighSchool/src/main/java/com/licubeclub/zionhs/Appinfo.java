/*
 * Zion High School Application for Android
 * Copyright (C) 2013 Youngbin Han<sukso96100@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.licubeclub.zionhs;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.app.Activity;
import android.content.SharedPreferences;

public class Appinfo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_slide_in, R.anim.zoom_out);
        setContentView(R.layout.activity_appinfo);

        //Stop ShakeDetectService
        Intent intent = new Intent(this, ShakeDetectService.class);
        stopService(intent);
        // Load Preference Value
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        CheckBox Toggle = (CheckBox)findViewById(R.id.quickexec); //checkbox (QuickLaunch Toggle)
        CheckBox NotiToggle = (CheckBox)findViewById(R.id.quickexec_noti); // QuickLaunch Notification Toggle
        Spinner spinner = (Spinner) findViewById(R.id.quickexec_select);  //spinner
        Boolean Toggle_Boolean = pref.getBoolean("toggledata", false);
        Boolean Toggle_Noti = pref.getBoolean("notitoggle", false);
        int Spinner_int = pref.getInt("quickexec_select",0);
        Toggle.setChecked(Toggle_Boolean);
        NotiToggle.setChecked(Toggle_Noti);
        spinner.setSelection(Spinner_int);

        //Get app version name from Manifest
        String app_ver = null;
        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        //Set app version name text
        TextView version = (TextView)findViewById(R.id.version);
        version.setText("Version " + app_ver);

        TextView src = (TextView)findViewById(R.id.src);
        src.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent src = new Intent(Intent.ACTION_VIEW);
                src.setData(Uri.parse("http://github.com/sukso96100/zionhs"));
                startActivity(src);
            }
        });

        TextView tutorial = (TextView)findViewById(R.id.tutorial);
        tutorial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorial = new Intent(Appinfo.this, Tutorial.class);
                startActivity(tutorial);
            }
        });

        TextView readme = (TextView)findViewById(R.id.readme);
        readme.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readme = new Intent(Appinfo.this, Doc_Readme.class);
                startActivity(readme);
            }
        });

        TextView notices = (TextView)findViewById(R.id.notices);
        notices.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notices = new Intent(Appinfo.this, Doc_Notices.class);
                startActivity(notices);
            }
        });

        TextView copying = (TextView)findViewById(R.id.copying);
        copying.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent copying = new Intent(Appinfo.this, Doc_Copying.class);
                startActivity(copying);
            }
        });

        TextView contrubutors = (TextView)findViewById(R.id.contrubutors);
        contrubutors.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contrubutors = new Intent(Appinfo.this, Doc_Contributors.class);
                startActivity(contrubutors);
            }
        });
    }

            public void onStop(){
        super.onStop();

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // Save UI State
        SharedPreferences.Editor editor = pref.edit(); // Load Editor
        CheckBox check1 = (CheckBox)findViewById(R.id.quickexec);
        CheckBox noti = (CheckBox)findViewById(R.id.quickexec_noti);
        Spinner spinner = (Spinner) findViewById(R.id.quickexec_select);
        // Input values
        int quickexec_selected_value = spinner.getSelectedItemPosition();
        editor.putInt("quickexec_select", quickexec_selected_value);
        editor.putBoolean("toggledata", check1.isChecked());
        editor.putBoolean("notitoggle", noti.isChecked());
        editor.commit(); // Save values

        final boolean quicklaunchon = getSharedPreferences("pref", Context.MODE_PRIVATE).getBoolean("toggledata", true);
                if(quicklaunchon){
                    Intent intent = new Intent(this, ShakeDetectService.class);
                    startService(intent);
                }
                else{}
    }
    protected void onDestroy(){
        super.onDestroy();

    }
}