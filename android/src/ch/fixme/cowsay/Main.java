package ch.fixme.cowsay;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity
{    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Context ctxt = getApplicationContext();
        final EditText txt = (EditText) findViewById(R.id.message);
        
        ((Button) findViewById(R.id.btn_say)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Cow cow = new Cow(ctxt, txt.getText().toString(), "", 0);
                ((TextView) findViewById(R.id.thecow)).setText(cow.get_cow());
            }
        });
    }
}
