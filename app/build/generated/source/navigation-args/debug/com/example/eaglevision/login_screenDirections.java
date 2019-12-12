package com.example.eaglevision;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class login_screenDirections {
  private login_screenDirections() {
  }

  @NonNull
  public static NavDirections actionLoginScreenToMainMenu() {
    return new ActionOnlyNavDirections(R.id.action_login_screen_to_main_menu);
  }
}
