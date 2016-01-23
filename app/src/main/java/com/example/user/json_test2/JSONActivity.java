package com.example.user.json_test2;

/**
 * Created by USER on 23-01-16.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
public class JSONActivity extends ActionBarActivity {
    JsonParser jsonparser;
    ProgressBar pbbar;
    JSONObject jobj;
    ListView lst;
    TextView txtinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydata);
        lst = (ListView) findViewById(R.id.lst);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        txtinfo = (TextView) findViewById(R.id.txtinfo);
        pbbar.setVisibility(View.GONE);
        txtinfo.setVisibility(View.GONE);
        jsonparser = new JsonParser();
        jobj = new JSONObject();
        LoadJS js = new LoadJS();
        js.execute("");
    }
    private class LoadJS extends AsyncTask<String, String, String> {
        String resultedData = null;
        @Override
        protected String doInBackground(String... params) {
            try {
                String h = "http://qutofv2.anathothonline.us/Bannerweb.asmx/GetSingleBanner";
                resultedData = jsonparser.getJSON(h);
            } catch (Exception ex) {
                resultedData = "There's an error, that's all I know right now.. :(";
            }
            return resultedData;
        }
        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
            txtinfo.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            txtinfo.setVisibility(View.GONE);
            try {
                ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
                JSONArray jarray = new JSONArray(r);
                for (int i = 0; i < jarray.length(); i++) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    jobj = jarray.getJSONObject(i);
                    datanum.put("City", jobj.getString("caption"));
                    datanum.put("Country", jobj.getString("captionar"));
                    data.add(datanum);
                }
                String[] from = { "City", "Country" };
                int[] views = { R.id.txtcityname, R.id.txtcountryname };
                final SimpleAdapter ADA = new SimpleAdapter(JSONActivity.this,
                        data, R.layout.rows, from, views);
                lst.setAdapter(ADA);
            } catch (Exception ex) {
                Toast.makeText(JSONActivity.this, "error", Toast.LENGTH_LONG)
                        .show();
                txtinfo.setVisibility(View.VISIBLE);
                txtinfo.setText(ex.getMessage().toString());
            }
        }
    }
}
