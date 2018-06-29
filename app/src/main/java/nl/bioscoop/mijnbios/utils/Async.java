package nl.bioscoop.mijnbios.utils;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import nl.bioscoop.mijnbios.utils.functions.Function;
import nl.bioscoop.mijnbios.utils.functions.Function1;
import nl.bioscoop.mijnbios.utils.functions.FunctionR;

public final class Async {
    public static <T> void async(FunctionR<T> async){
        async(async, null);
    }

    public static <T> void async(FunctionR<T> async, @Nullable Function1<T> onFinished){
        new AsyncTask<Void, Void, T>() {
            @Override protected T doInBackground(Void... voids) {
                return async.invoke();
            }

            @Override protected void onPostExecute(T t) {
                if (onFinished != null) onFinished.invoke(t);
            }
        }.execute();
    }

    public static void async(Function async){
        async(() -> {
            async.invoke();
            return null;
        }, null);
    }
}
