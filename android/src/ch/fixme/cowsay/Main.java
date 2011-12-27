package ch.fixme.cowsay;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

public class Main extends Activity
{
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final EditText txt = (EditText) findViewById(R.id.message);
        ((Button) findViewById(R.id.btn_say)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 Cow cow = new Cow(txt.getText().toString());
            }
        });
 
        
    }
}
