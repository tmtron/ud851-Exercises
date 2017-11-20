/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class WaterReminderFirebaseJobService
        // DONE (3) WaterReminderFirebaseJobService should extend from JobService
        extends JobService {

    private static AsyncTask mBackgroundTask;

    // DONE (4) Override onStartJob
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        // DONE (5) By default, jobs are executed on the main thread, so make an anonymous class extending
        //  AsyncTask called mBackgroundTask.
        mBackgroundTask = new AsyncTask() {
            // DONE (6) Override doInBackground
            @Override
            protected Object doInBackground(Object[] objects) {
                // DONE (7) Use ReminderTasks to execute the new charging reminder task you made, use
                // this service as the context (WaterReminderFirebaseJobService.this) and return null
                // when finished.
                final Context context = WaterReminderFirebaseJobService.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                return null;
            }
            // DONE (8) Override onPostExecute and called jobFinished. Pass the job parameters
            // and false to jobFinished. This will inform the JobManager that your job is done
            // and that you do not want to reschedule the job.

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters, false);
            }
        };

        // DONE (9) Execute the AsyncTask
        mBackgroundTask.execute();
        // DONE (10) Return true
        return true;
    }

    // DONE (11) Override onStopJob
    // e.g. this may be called when we have configured the job to work only on WiFi
    // the user may have started the job when WiFi was on and then (when the job
    // is still running) deactivates WiFi
    @Override
    public boolean onStopJob(JobParameters job) {
        // DONE (12) If mBackgroundTask is valid, cancel it
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        // DONE (13) Return true to signify the job should be retried
        // when WiFi is back, we want to retry the job
        return true;
    }

}
