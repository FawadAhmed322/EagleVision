package com.example.eaglevision;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class splash_screenDirections {
  private splash_screenDirections() {
  }

  @NonNull
  public static NavDirections actionSplashScreenToLoginScreen() {
    return new ActionOnlyNavDirections(R.id.action_splash_screen_to_login_screen);
  }
}
