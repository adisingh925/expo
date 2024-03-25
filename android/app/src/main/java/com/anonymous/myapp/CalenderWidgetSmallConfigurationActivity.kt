package com.anonymous.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.Window
import android.appwidget.AppWidgetManager
import android.widget.RemoteViews
import android.content.Intent
import android.app.Activity
import com.anonymous.myapp.sharedpreferences.SharedPreferences
import com.anonymous.myapp.widgetprovider.CalenderWidgetSmall
import android.content.ComponentName
import android.widget.Button

class CalenderWidgetSmallConfigurationActivity : AppCompatActivity() {

    private var appWidgetId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = getWindow()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setDimAmount(0f) // Disable dimming effect

        // Hide title
        // Hide title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_main)

        val formula1 = findViewById<Button>(R.id.formula1)
        val motogp = findViewById<Button>(R.id.motogp)
        val wec = findViewById<Button>(R.id.wec)
        val formula2 = findViewById<Button>(R.id.formula2)
        val formulae = findViewById<Button>(R.id.formulae)
        val f1academy = findViewById<Button>(R.id.f1academy)

        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val views = RemoteViews(this.packageName, R.layout.calender_widget_small)

        formula1.setOnClickListener {
            SharedPreferences.write(appWidgetId.toString(), "f1")
            val intent: Intent = Intent(this, CalenderWidgetSmall::class.java)
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            val ids: IntArray? = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(ComponentName(this, CalenderWidgetSmall::class.java))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            this.sendBroadcast(intent)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }

        motogp.setOnClickListener {
            SharedPreferences.write(appWidgetId.toString(), "motogp")
            val intent: Intent = Intent(this, CalenderWidgetSmall::class.java)
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            val ids: IntArray? = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(ComponentName(this, CalenderWidgetSmall::class.java))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            this.sendBroadcast(intent)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }

        wec.setOnClickListener {
            SharedPreferences.write(appWidgetId.toString(), "wec")
            val intent: Intent = Intent(this, CalenderWidgetSmall::class.java)
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            val ids: IntArray? = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(ComponentName(this, CalenderWidgetSmall::class.java))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            this.sendBroadcast(intent)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }

        formula2.setOnClickListener {
            SharedPreferences.write(appWidgetId.toString(), "f2")
            val intent: Intent = Intent(this, CalenderWidgetSmall::class.java)
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            val ids: IntArray? = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(ComponentName(this, CalenderWidgetSmall::class.java))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            this.sendBroadcast(intent)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }

        formulae.setOnClickListener {
            SharedPreferences.write(appWidgetId.toString(), "fe")
            val intent: Intent = Intent(this, CalenderWidgetSmall::class.java)
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            val ids: IntArray? = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(ComponentName(this, CalenderWidgetSmall::class.java))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            this.sendBroadcast(intent)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }

        f1academy.setOnClickListener {
            SharedPreferences.write(appWidgetId.toString(), "f1-academy")
            val intent: Intent = Intent(this, CalenderWidgetSmall::class.java)
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            val ids: IntArray? = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(ComponentName(this, CalenderWidgetSmall::class.java))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            this.sendBroadcast(intent)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
        super.onBackPressed()
    }
}
