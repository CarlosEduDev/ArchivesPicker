package com.ceasdev.archivespicker.data.works.abstraction;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**Autor Carlos Eduardo**/

public abstract class SimpleWork<Params, Post, Result> implements Runnable {

    private Handler handler;
    private ExecutorService executorService;
    private Params[] params;
    private boolean destroyed, working;

    public SimpleWork() {
        this.handler = new Handler(Looper.getMainLooper());
        this.executorService = Executors.newSingleThreadExecutor();
        this.destroyed = false;
        this.working = false;
    }

    /**
     * Chamado antes da execução do trabalho
     **/
    protected void onPrepareWork() {}

    /**
     * Postar progresso na thread principal
     **/
    protected void onPostProgressWork(Post... post) {}

    /**
     * Obter resultado mesmo quando o trabalho for destruido
     **/
    protected void onDestroyWork(Result result) {}

    /**
     * Realizar trabalho em outra thread
     **/
    protected abstract Result doWorkInBackground(Params... params) throws Exception;

    /**
     * Obter resultado do trabalho
     **/
    protected abstract void onResultWork(Result result);

    /**
     * Capturar Exceptions
     **/
    protected abstract void onFailureWork(Exception exception);




    @Override
    public void run() {
        try {
            resultWork(doWorkInBackground(params));
        } catch (Exception exception) {
            failureWork(exception);
        } finally {
            working = false;
        }

    }

    public void execute(Params... params) {
        if (destroyed) throw new WorkDestroyedException();
        this.params = params;
        working = true;
        onPrepareWork();
        executorService.execute(this);
    }

    public boolean isWorking() {
        return working;
    }


    public void destroyWork() {
        this.destroyed = true;
    }


    public boolean isWorkDestroyed() {
        return destroyed;
    }



    protected void postProgressWork(final Post... post) {
        handler.post(new Runnable(){
                @Override
                public void run() {
                    onPostProgressWork(post);
                }
            });
    }


    private void resultWork(final Result result) {
        handler.post(new Runnable(){
                @Override
                public void run() {
                    if (destroyed)
                        onDestroyWork(result);
                    else
                        onResultWork(result);
                }
            });
    }


    private void failureWork(final Exception exception) {
        handler.post(new Runnable(){
                @Override
                public void run() {
                    onFailureWork(exception);
                }
            });
    }



    public class WorkDestroyedException extends RuntimeException {
        public WorkDestroyedException() {
            super("Unable to run a job that is destroyed");
        }
    }

}
