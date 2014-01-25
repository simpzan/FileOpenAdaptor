package simpzan.FileOpenAdaptor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;

/**
 * Created by simpzan on 1/25/14.
 */
public class KindleFileOpenActivity extends Activity {
    private File sourceFile;
    private File destinationFile;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        copyAndOpenKindle();
    }

    private void copyAndOpenKindle() {
        if (!KindleUtility.isKindleAppInstalled(this)) {
            KindleUtility.makeToast(this, "Kindle app not installed!");
            finish();
            return;
        }

        setupData();
        if (destinationFile.exists()) {
            openKindle();
            return;
        }

        new AsyncTask<File, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(File... params) {
                try {
                    KindleUtility.copyFile(params[0], params[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (!aBoolean) {
                    KindleUtility.makeToast(KindleFileOpenActivity.this, "File copy failed!");
                    finish();
                    return;
                }
                openKindle();
            }
        }.execute(sourceFile, destinationFile);

    }

    private void openKindle() {
        KindleUtility.openKindleApp(this);
        finish();
    }

    private void setupData() {
        Intent intent = getIntent();
        sourceFile = new File(intent.getData().getPath());
        destinationFile = new File(KindleUtility.KINDLE_DIR, sourceFile.getName());
    }
}