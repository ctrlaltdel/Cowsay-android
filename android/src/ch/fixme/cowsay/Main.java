package ch.fixme.cowsay;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity
{    
	private Cow cow;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Context ctxt = getApplicationContext();
        final EditText txt = (EditText) findViewById(R.id.message);
        
        cow = new Cow(ctxt);

        ((Button) findViewById(R.id.btn_say)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	cowRefresh();
            }
        });
        
        TextWatcher myTextWatcher = new TextWatcher() {        	
        	public void onTextChanged(CharSequence s, int start, int before, int count) {
        		cowRefresh();
        	}
        	
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        
        txt.addTextChangedListener(myTextWatcher);
        txt.setText("Moo");
    }
    
    private void cowRefresh() {
    	Log.d("Main", "Let's refresh the cow");
    	
    	final EditText txt = (EditText) findViewById(R.id.message);
    	cow.message = txt.getText().toString();
    			
        ((TextView) findViewById(R.id.thecow)).setText(cow.asString());
    }
}
