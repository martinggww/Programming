package lucas.trainone;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();

        //intent can put extra key-value pairs called extras
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
    public class BrowerDemo1 extends Activity{
        WebView browser;
        @Override
        public void onCreate(Bundle icicle){
            super.conCreate(icicle);
            setContentView(R.layout.main);
            brower = (WebView) findViewById(R.id.webkit);
            browser.loadUrl("http://www.google.com");
            browser.loadData("<html><body> Hello world!</body></html>, "text/html", "UTF-8");

        }

    }

    public class BrowserDemo3 extends Activity{
        WebView browser;
        public void onCreate(Bundle icicle){
            super.onCreate(icicle);
            setContentView(android.R.layout.main);
            browser = (WebView) findViewById(R.id.webkit);
            browser.setWebViewClient(new Callback());
            loadTime();

        }
    }

    void loadTime(){
        String page = "<html><body></body></html>";
        browser.loadData(page, )

    }

    private class Callback extends WebViewClient{
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            loadTime();
            return(true);
        }
    }
}
