package ru.peppercourse.tracker;
import android.Manifest;import android.app.Activity;import android.os.Bundle;import android.os.Build;import android.content.pm.PackageManager;import android.webkit.*;import android.graphics.Color;import android.view.WindowInsets;import androidx.webkit.WebViewAssetLoader;import org.json.JSONObject;
public class MainActivity extends Activity {
 private WebView web;
 @Override public void onCreate(Bundle saved){super.onCreate(saved);getWindow().setStatusBarColor(Color.rgb(25,22,17));getWindow().setNavigationBarColor(Color.rgb(25,22,17));web=new WebView(this);web.setBackgroundColor(Color.rgb(25,22,17));android.widget.FrameLayout root=new android.widget.FrameLayout(this);root.setBackgroundColor(Color.rgb(25,22,17));root.addView(web,new android.widget.FrameLayout.LayoutParams(-1,-1));setContentView(root);
 root.setOnApplyWindowInsetsListener((v,insets)->{if(Build.VERSION.SDK_INT>=30){android.graphics.Insets bars=insets.getInsets(WindowInsets.Type.systemBars()|WindowInsets.Type.displayCutout());v.setPadding(bars.left,bars.top,bars.right,bars.bottom);}return insets;});
 WebSettings settings=web.getSettings();settings.setJavaScriptEnabled(true);settings.setDomStorageEnabled(true);settings.setAllowFileAccess(false);settings.setAllowContentAccess(false);settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
 final WebViewAssetLoader loader=new WebViewAssetLoader.Builder().addPathHandler("/",new WebViewAssetLoader.AssetsPathHandler(this)).build();
 web.setWebViewClient(new WebViewClient(){@Override public WebResourceResponse shouldInterceptRequest(WebView view,WebResourceRequest request){return loader.shouldInterceptRequest(request.getUrl());}@Override public boolean shouldOverrideUrlLoading(WebView view,WebResourceRequest request){return !"appassets.androidplatform.net".equals(request.getUrl().getHost());}});
 web.addJavascriptInterface(new Bridge(),"PepperAndroid");web.loadUrl("https://appassets.androidplatform.net/index.html");}
 class Bridge {
 @JavascriptInterface public void saveState(String json){try{new JSONObject(json);getSharedPreferences("course",MODE_PRIVATE).edit().putString("state",json).apply();ReminderScheduler.schedule(MainActivity.this,json);}catch(Exception ignored){}}
 @JavascriptInterface public String getState(){return getSharedPreferences("course",MODE_PRIVATE).getString("state","");}
 @JavascriptInterface public void requestNotifications(){runOnUiThread(()->{if(Build.VERSION.SDK_INT>=33&&checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED)requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},71);else permissionResult(true);});}
 }
 private void permissionResult(boolean granted){web.evaluateJavascript("window.onPepperPermission && window.onPepperPermission("+granted+")",null);}
 @Override public void onRequestPermissionsResult(int code,String[] permissions,int[] results){super.onRequestPermissionsResult(code,permissions,results);if(code==71)permissionResult(results.length>0&&results[0]==PackageManager.PERMISSION_GRANTED);}
 @Override public void onBackPressed(){web.evaluateJavascript("(()=>{const b=document.querySelector('.modal-close');if(b){b.click();return 'closed'}const h=[...document.querySelectorAll('.bottom-nav button')][0];if(h&&!h.classList.contains('active')){h.click();return 'home'}return 'exit'})()",result->{if("\"exit\"".equals(result))finish();});}
 @Override protected void onDestroy(){web.removeJavascriptInterface("PepperAndroid");web.destroy();super.onDestroy();}
}
