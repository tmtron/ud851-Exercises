package com.example.android.boardingpass;

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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.boardingpass.databinding.ActivityMainBinding;
import com.example.android.boardingpass.utilities.FakeDataUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //DONE (3) Create a data binding instance called mBinding of type ActivityMainBinding
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DONE (4) Set the Content View using DataBindingUtil to the activity_main layout
        //setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // DONE (5) Load a BoardingPassInfo object with fake data using FakeDataUtils
        final BoardingPassInfo boardingPassInfo = FakeDataUtils.generateFakeBoardingPassInfo();

        // DONE (9) Call displayBoardingPassInfo and pass the fake BoardingInfo instance
        displayBoardingPassInfo(boardingPassInfo);
    }

    private void displayBoardingPassInfo(BoardingPassInfo info) {

        // DONE (6) Use mBinding to set the Text in all the textViews using the data in info
        mainBinding.textViewPassengerName.setText(info.passengerName);
        mainBinding.textViewOriginAirport.setText(info.originCode);
        mainBinding.textViewFlightCode.setText(info.flightCode);
        mainBinding.textViewDestinationAirport.setText(info.destCode);

        // DONE (7) Use a SimpleDateFormat formatter to set the formatted value in time text views
        /* DONE MTR: java vs. android SimpleDataFormat?
         * by using the android classes we can get a smaller APK
         * BUT: the android classes only exist since Android 7
         */
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        final String boardingTime = simpleDateFormat.format(info.boardingTime);
        final String departureTime = simpleDateFormat.format(info.departureTime);
        final String arrivalTime = simpleDateFormat.format(info.arrivalTime);

        mainBinding.textViewBoardingTime.setText(boardingTime);
        mainBinding.textViewDepartureTime.setText(departureTime);
        mainBinding.textViewArrivalTime.setText(arrivalTime);

        // DONE (8) Use TimeUnit methods to format the total minutes until boarding
        final long totalMinutesUntilBoarding = info.getMinutesUntilBoarding();
        final long hoursUntilBoarding = TimeUnit.MINUTES.toHours(totalMinutesUntilBoarding);
        final long minutesLessHoursUntilBoarding = totalMinutesUntilBoarding - TimeUnit.HOURS.toMinutes(hoursUntilBoarding);

        //final String hoursAndMinutesUntilBoarding = "{hoursUntilBoarding}:{minutesUntilBoarding}";
        String hoursAndMinutesUntilBoarding = getString(R.string.countDownFormat,
                hoursUntilBoarding,
                minutesLessHoursUntilBoarding);
        mainBinding.textViewBoardingInCountdown.setText(hoursAndMinutesUntilBoarding);
        mainBinding.textViewTerminal.setText(info.departureTerminal);
        mainBinding.textViewGate.setText(info.departureGate);
        mainBinding.textViewSeat.setText(info.seatNumber);
    }
}

