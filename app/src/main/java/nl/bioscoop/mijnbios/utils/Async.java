package nl.bioscoop.mijnbios.utils;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

public class Async {
    public static <T> void async(FunctionNoParams<T> async){
        async(async, null);
    }

    public static <T> void async(FunctionNoParams<T> async, @Nullable FunctionNoResult<T> onFinished){
        new AsyncTask<Void, Void, T>() {
            @Override protected T doInBackground(Void... voids) {
                return async.invoke();
            }

            @Override protected void onPostExecute(T t) {
                if (onFinished != null) onFinished.invoke(t);
            }
        }.execute();
    }

    public interface Function<P, R> {
        R invoke(P p);
    }

    public interface FunctionNoParams<R> {
        R invoke();
    }

    public interface FunctionNoResult<P> {
        void invoke(P p);
    }

    public interface SimpleFunction {
        void invoke();
    }
}
