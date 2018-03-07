package com.dts.vaclass.base.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LocalIntentService extends IntentService {

    private static final String TAG = "LocalIntentService";
    
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.dts.vaclass.base.service.action.FOO";
    public static final String ACTION_BAZ = "com.dts.vaclass.base.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.dts.vaclass.base.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.dts.vaclass.base.service.extra.PARAM2";

    public LocalIntentService() {
        super("LocalIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    static Intent intent1,intent2;
    public static void startActionFoo(Context context, String param1, String param2) {
        intent1 = new Intent(context, LocalIntentService.class);
        intent1.setAction(ACTION_FOO);
        intent1.putExtra(EXTRA_PARAM1, param1);
        intent1.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent1);
        Log.i(TAG, "startActionFoo: ");
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        intent2 = new Intent(context, LocalIntentService.class);
        intent2.setAction(ACTION_BAZ);
        intent2.putExtra(EXTRA_PARAM1, param1);
        intent2.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent2);
        Log.i(TAG, "startActionBaz: ");
    }

    public static void stopService(Context context){
        if (intent1 != null) {
            context.stopService(intent1);
            intent1=null;
            Log.i(TAG, "stopService: ActionFoo");
        }
        if (intent2 != null) {
            context.stopService(intent2);
            intent2=null;
            Log.i(TAG, "stopService: ActionBaz");
        }
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param3 = intent.getStringExtra(EXTRA_PARAM1);
                final String param4 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param3, param4);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        Log.i(TAG, "handleActionFoo: "+param1+"\t\t"+param2);
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param3, String param4) {
        // TODO: Handle action Baz
        Log.i(TAG, "handleActionBaz: "+param3+"\t\t"+param4);
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
