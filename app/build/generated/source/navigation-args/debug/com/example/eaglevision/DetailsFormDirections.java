package com.example.eaglevision;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class DetailsFormDirections {
  private DetailsFormDirections() {
  }

  @NonNull
  public static NavDirections actionDetailsFormToConfirmScreen() {
    return new ActionOnlyNavDirections(R.id.action_detailsForm_to_confirm_Screen);
  }
}
