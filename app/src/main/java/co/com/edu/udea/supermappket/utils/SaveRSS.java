package co.com.edu.udea.supermappket.utils;

        import android.content.Context;
        import android.util.Log;
        import co.com.edu.udea.supermappket.dto.ProProducto;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.util.List;

public class SaveRSS {

    private static Context context;
    public SaveRSS(Context c) {
        super();
        // TODO Auto-generated constructor stub
        SaveRSS.context = c;
    }

    public static boolean guardarRSS(List<ProProducto> lRSS, String id) {
        boolean rssSave = false;

        File f = null;
        f = new File(context.getFilesDir(), "Rss_" + id
                + ".rss");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(lRSS);
            os.close();
            Log.i("File Manager", "Se guardaron los productos");
            rssSave = true;
        } catch (Exception e) {
            rssSave = false;
        }

        return rssSave;
    }

    public static List<ProProducto> cargarRSS(String id) throws IOException,
            ClassNotFoundException {
        File f = new File(context.getFilesDir(), "Rss_" + id
                + ".rss");
        if (!f.exists()) {
            f = new File(context.getFilesDir(), "Rss_" + id
                    + ".rss");
        }

        if (f.exists()) {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream is = new ObjectInputStream(fis);
            List<ProProducto> lRSS = (List<ProProducto>) is.readObject();
            is.close();
            return lRSS;
        }
        return null;
    }

    public static boolean borrarRSS(String id) {
        boolean rssSave = false;

        File f = null;
        f = new File(context.getFilesDir(), "Rss_" + id
                + ".rss");
        if (f.exists()) {
            f.delete();
            rssSave = true;
        }

        return rssSave;
    }
}