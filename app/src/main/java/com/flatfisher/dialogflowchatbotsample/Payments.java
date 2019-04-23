package com.flatfisher.dialogflowchatbotsample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.paypal.android.sdk.payments.*;
import android.content.Intent;
import java.math.BigDecimal;
import android.app.Activity;
import org.json.JSONException;
import android.util.Log;
import android.widget.ImageButton;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import java.util.HashMap;
import android.net.Uri;

public class Payments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Initialize Link
        HashMap<String, String> linkInitializeOptions = new HashMap<String,String>();
        linkInitializeOptions.put("key", "xxxxx");
        linkInitializeOptions.put("product", "auth");
        linkInitializeOptions.put("apiVersion", "v2"); // set this to "v1" if using the legacy Plaid API
        linkInitializeOptions.put("env", "sandbox");
        linkInitializeOptions.put("clientName", "Test App");
        linkInitializeOptions.put("selectAccount", "true");
        linkInitializeOptions.put("webhook", "http://requestb.in");
        linkInitializeOptions.put("baseUrl", "https://cdn.plaid.com/link/v2/stable/link.html");
        // If initializing Link in PATCH / update mode, also provide the public_token
        // linkInitializeOptions.put("token", "PUBLIC_TOKEN")

        // Generate the Link initialization URL based off of the configuration options.
        final Uri linkInitializationUrl = generateLinkInitializationUrl(linkInitializeOptions);

        final WebView plaidLinkWebview = (WebView) findViewById(R.id.plaidView);
        WebSettings webSettings = plaidLinkWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // WebView.setWebContentsDebuggingEnabled(true);

        // Initialize Link by loading the Link initialization URL in the Webview
        plaidLinkWebview.loadUrl(linkInitializationUrl.toString());

        // Override the Webview's handler for redirects
        // Link communicates success and failure (analogous to the web's onSuccess and onExit
        // callbacks) via redirects.
        plaidLinkWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Parse the URL to determine if it's a special Plaid Link redirect or a request
                // for a standard URL (typically a forgotten password or account not setup link).
                // Handle Plaid Link redirects and open traditional pages directly in the  user's
                // preferred browser.
                Uri parsedUri = Uri.parse(url);
                if (parsedUri.getScheme().equals("plaidlink")) {
                    String action = parsedUri.getHost();
                    HashMap<String, String> linkData = parseLinkUriData(parsedUri);

                    if (action.equals("connected")) {
                        // User successfully linked
                        Log.d("Public token: ", linkData.get("public_token"));
                        Log.d("Account ID: ", linkData.get("account_id"));
                        Log.d("Account name: ", linkData.get("account_name"));
                        Log.d("Institution id: ", linkData.get("institution_id"));
                        Log.d("Institution name: ", linkData.get("institution_name"));


//
//                       String categories =  linkData.get("transactions[categories]");
//
//
//
//                        Pie pie = AnyChart.pie();
//
//                        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
//                            @Override
//                            public void onClick(Event event) {
//                                Toast.makeText(PieChartActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        List<DataEntry> data = categories;
//                        pie.data(data);
//
//                        pie.title("Fruits imported in 2015 (in kg)");
//
//                        pie.labels().position("outside");
//
//                        pie.legend().title().enabled(true);
//                        pie.legend().title()
//                                .text("Retail channels")
//                                .padding(0d, 0d, 10d, 0d);
//
//                        pie.legend()
//                                .position("center-bottom")
//                                .itemsLayout(LegendLayout.HORIZONTAL)
//                                .align(Align.CENTER);
//
//                        anyChartView.setChart(pie);
//
//
//                        // Reload Link in the Webview
//                        // You will likely want to transition the view at this point.
//                       // plaidLinkWebview.loadUrl(linkInitializationUrl.toString());
//
//
//
//
//                        final long tweetId = 'AAPL';
//                        final long tweetId2 = 'GOOG';
//                        final long tweetId3 = 'FB';
//
//                        int result, result2, result3;
//
//                        c.inferenceInterface = new TensorFlowInferenceInterface(assetManager, modelFilename);
//
//                        private static final String MODEL_FILE =
//                                "file:///android_asset/mnist_model_graph.pb";
//                        private static final String LABEL_FILE =
//                                "file:///android_asset/graph_label_strings.txt";
//
//
//
//
//                        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
//                            @Override
//                            public void success(Result<Tweet> result) {
//                                myLayout.addView(new TweetView(EmbeddedTweetsActivity.this, tweet));
//                                result1 = recognizeTweet(result);
//                            }
//
//                            @Override
//                            public void failure(TwitterException exception) {
//                                // Toast.makeText(...).show();
//                            }
//                        });
//                        TweetUtils.loadTweet(tweetId2, new Callback<Tweet>() {
//                            @Override
//                            public void success(Result<Tweet> result) {
//
//
//                                result2 = recognizeTweet(result);
//                                myLayout.addView(new TweetView(EmbeddedTweetsActivity.this, tweet));
//                            }
//
//                            @Override
//                            public void failure(TwitterException exception) {
//                                // Toast.makeText(...).show();
//                            }
//                        });
//                        TweetUtils.loadTweet(tweetId3, new Callback<Tweet>() {
//                            @Override
//                            public void success(Result<Tweet> result) {
//                                result3 = recognizeTweet(result);
//                                myLayout.addView(new TweetView(EmbeddedTweetsActivity.this, tweet));
//                            }
//
//                            @Override
//                            public void failure(TwitterException exception) {
//                                // Toast.makeText(...).show();
//                            }
//                        });
//
//                        int max = Math.max(result1, result2, result);
//
//
//
//
//
//


                        Intent intent = new Intent(getApplicationContext(), Budget.class);
                        startActivity(intent);
                    } else if (action.equals("exit")) {
                        // User exited
                        // linkData may contain information about the user's status in the Link flow,
                        // the institution selected, information about any error encountered,
                        // and relevant API request IDs.
                        Log.d("User status in flow: ", linkData.get("status"));
                        // The request ID keys may or may not exist depending on when the user exited
                        // the Link flow.
                        Log.d("Link request ID: ", linkData.get("link_request_id"));
                        Log.d("API request ID: ", linkData.get("plaid_api_request_id"));

                        // Reload Link in the Webview
                        // You will likely want to transition the view at this point.
                       plaidLinkWebview.loadUrl(linkInitializationUrl.toString());

                    } else if (action.equals("event")) {
                        // The event action is fired as the user moves through the Link flow
                        Log.d("Event name: ", linkData.get("event_name"));
                    } else {
                        Log.d("Link action detected: ", action);
                    }
                    // Override URL loading
                    return true;
                } else if (parsedUri.getScheme().equals("https") ||
                        parsedUri.getScheme().equals("http")) {
                    // Open in browser - this is most  typically for 'account locked' or
                    // 'forgotten password' redirects
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    // Override URL loading
                    return true;
                } else {
                    // Unknown case - do not override URL loading
                    return false;
                }
            }
        });


    }


    // Generate a Link initialization URL based on a set of configuration options
    public Uri generateLinkInitializationUrl(HashMap<String,String>linkOptions) {
        Uri.Builder builder = Uri.parse(linkOptions.get("baseUrl"))
                .buildUpon()
                .appendQueryParameter("isWebview", "true")
                .appendQueryParameter("isMobile", "true");
        for (String key : linkOptions.keySet()) {
            if (!key.equals("baseUrl")) {
                builder.appendQueryParameter(key, linkOptions.get(key));
            }
        }
        return builder.build();
    }

    // Parse a Link redirect URL querystring into a HashMap for easy manipulation and access
    public HashMap<String,String> parseLinkUriData(Uri linkUri) {
        HashMap<String,String> linkData = new HashMap<String,String>();
        for(String key : linkUri.getQueryParameterNames()) {
            linkData.put(key, linkUri.getQueryParameter(key));
        }
        return linkData;
    }

    public void beginPayment(View view) {
         PayPalConfiguration config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId("AV8Jz73NP7gDgmqUDtqSYj8njKcImh_SeVFNeMjYxgrB9BLuiZJywjaBYDicBQaZXzAuHQoh70Nn4pg1");
        System.out.println("AY");

        Intent serviceConfig = new Intent(this, PayPalService.class);
        serviceConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(serviceConfig);

        PayPalPayment payment = new PayPalPayment(new BigDecimal("35"),
                "USD", "Monthly Therapy Fee", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent paymentConfig = new Intent(this, PaymentActivity.class);
        paymentConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        paymentConfig.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(paymentConfig, 0);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK){
            PaymentConfirmation confirm = data.getParcelableExtra(
                    PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null){
                try {
                    Log.i("sampleapp", confirm.toJSONObject().toString(4));
                    Intent intent = new Intent(this, MainActivity.class);

                    startActivity(intent);
                    // TODO: send 'confirm' to your server for verification

                } catch (JSONException e) {
                    Log.e("sampleapp", "no confirmation data: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("sampleapp", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("sampleapp", "Invalid payment / config set");
        }
    }

    @Override
    public void onDestroy(){
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
//    @Override
//    public List<Recognition> recognizeTweet(final float[] String) {
//        // Copy the input data into TensorFlow.
//        inferenceInterface.feed(inputName, pixels, new long[]{inputSize * inputSize});
//
//        // Run the inference call.
//        inferenceInterface.run(outputNames);
//
//        // Copy the output Tensor back into the output array.
//        inferenceInterface.fetch(outputName, outputs);
//
//        // Find the best classifications.
//        for (int i = 0; i < outputs.length; ++i) {
//        <snip>
//        }
//        return recognitions;
//    }



    }
