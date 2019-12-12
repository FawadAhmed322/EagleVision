package com.example.eaglevision;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class upload_media_screenDirections {
  private upload_media_screenDirections() {
  }

  @NonNull
  public static NavDirections actionUploadMediaScreenToDetailsForm() {
    return new ActionOnlyNavDirections(R.id.action_upload_media_screen_to_detailsForm);
  }
}
