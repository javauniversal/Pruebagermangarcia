package co.zonaapp.pruebagermangarcia.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.YoYo;
import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.FillableLoaderBuilder;
import com.github.jorgecastillo.State;
import com.github.jorgecastillo.clippingtransforms.PlainClippingTransform;
import com.github.jorgecastillo.listener.OnStateChangeListener;
import com.google.gson.Gson;
import com.nineoldandroids.animation.Animator;
import com.sdsmdg.tastytoast.TastyToast;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;
import com.viksaa.sssplash.lib.utils.UIUtil;
import com.viksaa.sssplash.lib.utils.ValidationUtil;

import java.util.HashMap;
import java.util.Map;

import co.zonaapp.pruebagermangarcia.Controllers.ControllerLogin;
import co.zonaapp.pruebagermangarcia.Model.ResponseLogin;
import co.zonaapp.pruebagermangarcia.R;
import dmax.dialog.SpotsDialog;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public abstract class AwesomeSplashCustom extends AppCompatActivity {

    private RelativeLayout mRlReveal;
    private ImageView mImgLogo;
    private AppCompatTextView mTxtTitle;
    private FillableLoader mPathLogo;
    private FrameLayout mFl;

    private ConfigSplash mConfigSplash;
    private boolean hasAnimationStarted = false;
    private int pathOrLogo = 0;
    private ControllerLogin controllerLogin;
    public RequestQueue rq;
    public SpotsDialog alertDialog;
    private boolean bandera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        rq = Volley.newRequestQueue(this);
        alertDialog = new SpotsDialog(this, R.style.Custom);

        controllerLogin = new ControllerLogin(this);

        if (controllerLogin.validateLoginUser()) {
            bandera = false;
            // Call Service
            alertDialog.show();
            new Thread(new Runnable() {
                public void run() {
                    final String email = controllerLogin.getUserLogin().getEmail();
                    final String passwordEncry = controllerLogin.getUserLogin().getPasswordEncry();
                    final String password = controllerLogin.getUserLogin().getPassword();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            loginServices(email, passwordEncry, password);
                        }
                    });
                }
            }).start();
        } else {
            mConfigSplash = new ConfigSplash();
            initSplash(mConfigSplash);

            pathOrLogo = ValidationUtil.hasPath(mConfigSplash);
            initUI(pathOrLogo);
        }
    }

    private void loginServices(final String email, final String passwordEncry, final String password) {

        String url = String.format("%1$s%2$s?email=%3$s&password=%4$s",
                getString(R.string.url_base),
                "application/login",
                email,  passwordEncry);

        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJsonLogin(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            TastyToast.makeText(AwesomeSplashCustom.this, "La aplicación ingreso sin acceso a internet.", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                            if (controllerLogin.validateLoginUser(email, password)) {
                                startActivity(new Intent(AwesomeSplashCustom.this, ActMainMenu.class));
                                finish();
                            }
                        } else if (error instanceof AuthFailureError) {
                            TastyToast.makeText(AwesomeSplashCustom.this, "Error Servidor", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        } else if (error instanceof ServerError) {
                            TastyToast.makeText(AwesomeSplashCustom.this, "Contraseña incorrecta", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            controllerLogin.deleteObject("login");
                            startActivity(new Intent(AwesomeSplashCustom.this, ActLogin.class));
                            finish();
                        } else if (error instanceof NetworkError) {
                            TastyToast.makeText(AwesomeSplashCustom.this, "Error de red", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        } else if (error instanceof ParseError) {
                            TastyToast.makeText(AwesomeSplashCustom.this, "Error al serializar los datos", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);
    }

    private void parseJsonLogin(String response) {

        Gson gson = new Gson();
        ResponseLogin responseLogin = gson.fromJson(response, ResponseLogin.class);

        if (responseLogin.isSuccess()) {
            // Login Ok
            alertDialog.dismiss();
            startActivity(new Intent(AwesomeSplashCustom.this, ActMainMenu.class));
            finish();
        }

    }

    public void initUI(int flag) {
        setContentView(com.viksaa.sssplash.lib.R.layout.activity_main_lib);

        mRlReveal = (RelativeLayout) findViewById(com.viksaa.sssplash.lib.R.id.rlColor);
        mTxtTitle = (AppCompatTextView) findViewById(com.viksaa.sssplash.lib.R.id.txtTitle);

        switch (flag) {
            case Flags.WITH_PATH:
                mFl = (FrameLayout) findViewById(com.viksaa.sssplash.lib.R.id.flCentral);
                initPathAnimation();
                break;
            case Flags.WITH_LOGO:
                mImgLogo = (ImageView) findViewById(com.viksaa.sssplash.lib.R.id.imgLogo);
                mImgLogo.setImageResource(mConfigSplash.getLogoSplash());
                break;
            default:
                break;

        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !hasAnimationStarted) {
            if (bandera) {
                startCircularReveal();
            }
        }
    }

    public void initPathAnimation() {
        int viewSize = getResources().getDimensionPixelSize(com.viksaa.sssplash.lib.R.dimen.fourthSampleViewSize);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(viewSize, viewSize);

        params.setMargins(0, 0, 0, 50);
        FillableLoaderBuilder loaderBuilder = new FillableLoaderBuilder();
        mPathLogo = loaderBuilder
                .parentView(mFl)
                .layoutParams(params)
                .svgPath(mConfigSplash.getPathSplash())
                .originalDimensions(mConfigSplash.getOriginalWidth(), mConfigSplash.getOriginalHeight())
                .strokeWidth(mConfigSplash.getPathSplashStrokeSize())
                .strokeColor(Color.parseColor(String.format("#%06X", (0xFFFFFF & getResources().getColor(mConfigSplash.getPathSplashStrokeColor())))))
                .fillColor(Color.parseColor(String.format("#%06X", (0xFFFFFF & getResources().getColor(mConfigSplash.getPathSplashFillColor())))))
                .strokeDrawingDuration(mConfigSplash.getAnimPathStrokeDrawingDuration())
                .fillDuration(mConfigSplash.getAnimPathFillingDuration())
                .clippingTransform(new PlainClippingTransform())
                .build();
        mPathLogo.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(int i) {
                if (i == State.FINISHED) {
                    startTextAnimation();
                }
            }
        });

    }

    public void startCircularReveal() {

        // get the final radius for the clipping circle
        int finalRadius = Math.max(mRlReveal.getWidth(), mRlReveal.getHeight()) + mRlReveal.getHeight() / 2;
        //bottom or top
        int y = UIUtil.getRevealDirection(mRlReveal, mConfigSplash.getRevealFlagY());
        //left or right
        int x = UIUtil.getRevealDirection(mRlReveal, mConfigSplash.getRevealFlagX());

        mRlReveal.setBackgroundColor(getResources().getColor(mConfigSplash.getBackgroundColor()));
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRlReveal, x, y, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(mConfigSplash.getAnimCircularRevealDuration());
        animator.addListener(new SupportAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }

            @Override
            public void onAnimationEnd() {

                if (pathOrLogo == Flags.WITH_PATH) {
                    mPathLogo.start();
                } else {
                    startLogoAnimation();
                }
            }
        });
        animator.start();
        hasAnimationStarted = true;
    }

    public void startLogoAnimation() {
        mImgLogo.setVisibility(View.VISIBLE);
        mImgLogo.setImageResource(mConfigSplash.getLogoSplash());

        YoYo.with(mConfigSplash.getAnimLogoSplashTechnique()).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startTextAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(mConfigSplash.getAnimLogoSplashDuration()).playOn(mImgLogo);
    }

    public void startTextAnimation() {

        mTxtTitle.setText(mConfigSplash.getTitleSplash());
        mTxtTitle.setTextSize(mConfigSplash.getTitleTextSize());
        mTxtTitle.setTextColor(getResources().getColor(mConfigSplash.getTitleTextColor()));
        if (!mConfigSplash.getTitleFont().isEmpty())
            setFont(mConfigSplash.getTitleFont());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, com.viksaa.sssplash.lib.R.id.flCentral);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTxtTitle.setLayoutParams(params);
        mTxtTitle.setVisibility(View.VISIBLE);

        YoYo.with(mConfigSplash.getAnimTitleTechnique()).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationsFinished();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(mConfigSplash.getAnimTitleDuration()).playOn(mTxtTitle);
    }


    public void setFont(String font) {
        Typeface type = Typeface.createFromAsset(getAssets(), font);
        mTxtTitle.setTypeface(type);
    }

    public abstract void initSplash(ConfigSplash configSplash);

    public abstract void animationsFinished();

}
