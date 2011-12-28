package ch.fixme.cowsay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Main extends Activity
{    
	private Cow cow;
    private EditText messageView;
    private TextView outputView;
	
    // Menu 
	public static final int MENU_SHARE_TEXT = Menu.FIRST;
	public static final int MENU_SHARE_HTML = Menu.FIRST + 1;
	public static final int MENU_SHARE_IMAGE = Menu.FIRST + 2;

    /* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_SHARE_TEXT, 0, "Share as text");
        menu.add(0, MENU_SHARE_HTML, 0, "Share as HTML");
        menu.add(0, MENU_SHARE_IMAGE, 0, "Share as image");
        return true;
    }
    
    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent = new Intent(Intent.ACTION_SEND);

        switch (item.getItemId()) {
            case MENU_SHARE_TEXT:
            	Log.d("Main Menu", "Share as text");
            	intent.setType("text/plain");
            	intent.putExtra(Intent.EXTRA_SUBJECT, "Cowsay");
            	intent.putExtra(Intent.EXTRA_TEXT, cow.asString());
            	startActivity(Intent.createChooser(intent, "Share with"));
        
            case MENU_SHARE_HTML:
            	Log.d("Main Menu", "Share as HTML");
            	intent.setType("text/html");
            	intent.putExtra(Intent.EXTRA_SUBJECT, "Cowsay");
            	intent.putExtra(Intent.EXTRA_TEXT, "<html><pre>" + cow.asString() + "</pre></html>");
            	startActivity(Intent.createChooser(intent, "Share with"));
            	
            case MENU_SHARE_IMAGE:
            	Log.d("Main Menu", "Share as image");
            	
            	View thecow = findViewById(R.id.thecow);
            	Bitmap screenshot = Bitmap.createBitmap(thecow.getWidth(), thecow.getHeight(), Bitmap.Config.RGB_565);
            	thecow.draw(new Canvas(screenshot));

            	String path = Images.Media.insertImage(getContentResolver(), screenshot, "title", null);
            	Uri screenshotUri = Uri.parse(path);

            	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            	emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	emailIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            	emailIntent.setType("image/png");

            	startActivity(Intent.createChooser(emailIntent, "Send email using"));

        }

        return false;
    }

	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        final Context ctxt = getApplicationContext();
        messageView = (EditText) findViewById(R.id.message);
        outputView = (TextView) findViewById(R.id.thecow);
        
        cow = new Cow(ctxt);

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
        
        messageView.addTextChangedListener(myTextWatcher);
        messageView.setText("Moo");
        
        populateCowTypes();
    }
    
    private void populateCowTypes() {
        // Populate the cow type Spinner widget        
    	final String[] items = cow.getCowTypes();
    	
    	for (int i = 0; i < items.length; i++) {
    		String item = items[i];
    		Log.d("Main", "item: " + item);
		}
    
        final Spinner spinner = (Spinner) findViewById(R.id.type);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        
       	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
				cow.style = items[position];
				cowRefresh();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
    }
    
    private void cowRefresh() {
    	Log.d("Main", "Let's refresh the cow");
    	
    	final EditText txt = (EditText) findViewById(R.id.message);
    	cow.message = txt.getText().toString();
    	
    	String text = cow.asString();
        ((TextView) findViewById(R.id.thecow)).setText(text);
        
        String[] lines = text.split("\n");
        Integer width = 0;
        Integer height = lines.length; 
        
        for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			
			if (line.length() > width) {
				width = line.length();
			}
		}
    	cow.message = messageView.getText().toString();
        outputView.setText(cow.asString());
    }
}
