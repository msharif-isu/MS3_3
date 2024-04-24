package com.example.itinerarybuddy;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.itinerarybuddy.ui.home.HomeFragment;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class AinaSystemTest {

    @Before
    public void setup(){

        FragmentScenario<HomeFragment> fragmentScenario = FragmentScenario.launchInContainer(HomeFragment.class);

    }

 
}