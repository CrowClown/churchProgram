package nfc.itu.mbds.churchprog.outils;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nfc.itu.mbds.churchprog.modele.Contenu;
import nfc.itu.mbds.churchprog.modele.Programme;

/**
 * Created by T7 on 27/03/2017.
 */

public class Utile {


    public static String loadJSONFromAsset(Activity activity, String path) throws IOException {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return json;
    }

    public static List<Programme> JsonToListObj(JSONArray array){

        List<Programme> prog = new ArrayList<>();
        try {

            for (int i = 0; i < array.length(); i++) {
                Programme temp = new Programme();
                JSONObject obj = array.getJSONObject(i);
                temp.setProg_id(obj.getInt("prog_id"));
                temp.setProg_date(obj.getString("prog_date"));
                temp.setProg_desc(obj.getString("prog_desc"));

                List<Contenu> l_contenu = new ArrayList<>();
                JSONArray array_c = obj.getJSONArray("contenu");
                for (int j = 0; j < array_c.length(); j++) {
                    Contenu temp_c = new Contenu();
                    JSONObject c_obj = array_c.getJSONObject(j);
                    temp_c.setC_id(c_obj.getInt("c_id"));
                    temp_c.setC_type(c_obj.getString("c_type"));
                    temp_c.setC_titre(c_obj.getString("c_titre"));
                    temp_c.setC_contenu(c_obj.getString("c_contenu"));
                    temp_c.setC_ordre(c_obj.getInt("c_ordre"));
                    temp_c.setId_prog(temp.getProg_id());
                    l_contenu.add(temp_c);
                }
                temp.setContenu(l_contenu);

                prog.add(temp);
            }
        }catch(Exception e){
            e.printStackTrace();
            //Log.e("EXCEPTION: ", ">>> " + e.getMessage());
        }
        return prog;
    }

    public static List<Programme> loadData(Activity activity) {
        List<Programme> prog = null;
        try {
            prog = new ArrayList<>();
            String json = loadJSONFromAsset(activity, "data_2017-03-05.json");

            //Log.e("ASSETS: ", ">>> " + json);
            JSONArray array = new JSONArray(json);
            prog = JsonToListObj(array);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return prog;
    }
}