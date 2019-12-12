package com.example.eaglevision;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class Confirm_ScreenDirections {
  private Confirm_ScreenDirections() {
  }

  @NonNull
  public static NavDirections actionConfirmScreenToDirectoryFragment() {
    return new ActionOnlyNavDirections(R.id.action_confirm_Screen_to_directoryFragment);
  }
}
