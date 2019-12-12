package com.example.eaglevision;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class main_menuDirections {
  private main_menuDirections() {
  }

  @NonNull
  public static NavDirections actionMainMenuToUploadMediaScreen() {
    return new ActionOnlyNavDirections(R.id.action_main_menu_to_upload_media_screen);
  }

  @NonNull
  public static NavDirections actionMainMenuToDirectoryFragment() {
    return new ActionOnlyNavDirections(R.id.action_main_menu_to_directoryFragment);
  }

  @NonNull
  public static NavDirections actionMainMenuToNotify() {
    return new ActionOnlyNavDirections(R.id.action_main_menu_to_notify);
  }

  @NonNull
  public static NavDirections actionMainMenuToUploadAcqusation() {
    return new ActionOnlyNavDirections(R.id.action_main_menu_to_upload_acqusation);
  }
}
