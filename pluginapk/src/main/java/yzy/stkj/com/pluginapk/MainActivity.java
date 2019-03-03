package yzy.stkj.com.pluginapk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import yzy.stkj.com.plugincore.BasePluginActivity;

public class MainActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button vButton = findViewById(R.id.show);
        vButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("yzy", "showTost: ");
                Toast.makeText(that, "我是插件中的土司", Toast.LENGTH_LONG).show();
            }
        });
    }

}
