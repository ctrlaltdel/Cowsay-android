package ch.fixme.cowsay;

import java.lang.Math;
import android.text.Selection;
import android.text.method.ScrollingMovementMethod;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.util.TypedValue;
import android.view.WindowManager;
import android.util.DisplayMetrics;
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
            	intent.putExtra(Intent.EXTRA_TEXT, cow.getFinalCow());
            	startActivity(Intent.createChooser(intent, "Share with"));
            	break;
        
            case MENU_SHARE_HTML:
            	Log.d("Main Menu", "Share as HTML");
            	intent.setType("text/html");
            	intent.putExtra(Intent.EXTRA_SUBJECT, "Cowsay");
            	intent.putExtra(Intent.EXTRA_TEXT, "<html><pre>" + cow.getFinalCow() + "</pre></html>");
            	startActivity(Intent.createChooser(intent, "Share with"));
            	break;
            	
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
            	break;
        }

        return false;
    }

	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
       
        // Initialize objects and ui access 
        final Context ctxt = getApplicationContext();
        cow = new Cow(ctxt);
        populateCowTypes();
        populateCowFaces();
        outputView = (TextView) findViewById(R.id.thecow);
        outputView.setMovementMethod(ScrollingMovementMethod.getInstance());
        messageView = (EditText) findViewById(R.id.message);
        messageView.setText("Moo");
        messageView.setSelection(3);

        // Real time update
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
    }
    
    private void populateCowTypes() {
        // Populate the cow type Spinner widget        
    	final String[] items = cow.getCowTypes();
        final Spinner spinner = (Spinner) findViewById(R.id.type);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setSelection(11);
       	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
				cow.style = items[position];
                cow.getCowFile();
				cowRefresh();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
    }
    
    private void populateCowFaces() {
        // Populate the cow face Spinner widget        
        Spinner s = (Spinner) findViewById(R.id.face);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.faces, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s.setAdapter(adapter);
        s.setSelection(0);
       	s.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
				cow.face = position;
				cowRefresh();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
    }
    
    private void cowRefresh() {
    	cow.message = messageView.getText().toString();
        outputView.setText(cow.getFinalCow());
    }
}
