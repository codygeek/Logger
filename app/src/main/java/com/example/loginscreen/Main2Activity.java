package com.example.loginscreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.share.internal.ShareDialogFeature;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private ShareDialog shareDialog;
    private String TAG = "MainActivity";
    private String name, surname, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle inBundle = getIntent().getExtras();
        name = inBundle != null ? inBundle.getString("name") : null;
        surname = inBundle != null ? inBundle.getString("surname") : null;
        imageUrl = inBundle != null ? inBundle.getString("imageUrl") : null;

        TextView nameView = (TextView) findViewById(R.id.nameAndSurname);
        nameView.setText("" + name + " " + surname);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                share();
                break;

            case R.id.getPosts:
                getPosts();
                break;

            case R.id.logout:
                logout();
                break;
        }
    }

    private void share() {
        shareDialog = new ShareDialog(this);
        List<String> ids = new ArrayList<String>();
        ids.add("{USER_ID}");
        ids.add("{USER_ID}");
        ids.add("{USER_ID}");

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://www.sitepoint.com"))
                .setContentTitle("This is a content title")
                .setContentDescription("This is a description")
                .setShareHashtag(new ShareHashtag.Builder().setHashtag("#sitepoint").build())
                .setPeopleIds(ids)
                .setPlaceId("{PLACE_ID}")
                .build();

        shareDialog.show(content);
    }

    private void getPosts() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(), "/me/posts", null, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e(TAG, response.toString());
                    }
                }
        ).executeAsync();
    }

    private void logout() {
        LoginManager.getInstance().logOut();
        Intent login = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(login);
        finish();
    }
}