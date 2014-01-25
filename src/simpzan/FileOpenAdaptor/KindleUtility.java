package simpzan.FileOpenAdaptor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by simpzan on 1/25/14.
 */
public class KindleUtility {
    public static final String KINDLE_DIR = "/sdcard/kindle/";

    public static void openKindleApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("kindle:"));
        context.startActivity(intent);
    }

    public static boolean isKindleAppInstalled(Context context) {
        return isPackageInstalled("com.amazon.kindle", context);
    }

    public static void copyFile(File sourceFile, File destinationFile) throws IOException {
        FileChannel input = null;
        FileChannel output = null;

        try {
            input = new FileInputStream(sourceFile).getChannel();
            output = new FileOutputStream(destinationFile).getChannel();
            output.transferFrom(input, 0, input.size());
        } finally {
            if (input != null) input.close();
            if (output != null) output.close();
        }
    }

    static public void print(Object obj) {
        String ouputString = obj == null ? "null" : obj.toString();
        Log.e("simpzan", ouputString);
    }

    static public String readableDateTime(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy - HH:mm");
        String formatted = format.format(time);
        return formatted;
    }

    public static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    static public void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
