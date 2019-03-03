package yzy.stkj.com.yzyplugindev;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import yzy.stkj.com.plugincore.PluginManger;
import yzy.stkj.com.plugincore.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void loadPlugin(View view) {
        File plugin = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
        PluginManger.getInstance(this).loadPlugin(plugin);
    }

    public void jump(View view) {
        Intent vIntent = new Intent(this, ProxyActivity.class);
        vIntent.putExtra("className", PluginManger.getInstance(this).getPackageInfo().activities[0].name);
        startActivity(vIntent);
    }
}
