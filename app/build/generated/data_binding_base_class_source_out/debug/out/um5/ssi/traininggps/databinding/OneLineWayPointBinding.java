// Generated by view binder compiler. Do not edit!
package um5.ssi.traininggps.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import um5.ssi.traininggps.R;

public final class OneLineWayPointBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnDeleteLocation;

  @NonNull
  public final Button btnEdit;

  @NonNull
  public final Button btnLocationFocus;

  @NonNull
  public final View divider2;

  @NonNull
  public final ConstraintLayout oneLineWayPoint;

  @NonNull
  public final TextView tvDate;

  @NonNull
  public final TextView tvTitle;

  private OneLineWayPointBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnDeleteLocation, @NonNull Button btnEdit, @NonNull Button btnLocationFocus,
      @NonNull View divider2, @NonNull ConstraintLayout oneLineWayPoint, @NonNull TextView tvDate,
      @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.btnDeleteLocation = btnDeleteLocation;
    this.btnEdit = btnEdit;
    this.btnLocationFocus = btnLocationFocus;
    this.divider2 = divider2;
    this.oneLineWayPoint = oneLineWayPoint;
    this.tvDate = tvDate;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static OneLineWayPointBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static OneLineWayPointBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.one_line_way_point, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static OneLineWayPointBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_delete_location;
      Button btnDeleteLocation = rootView.findViewById(id);
      if (btnDeleteLocation == null) {
        break missingId;
      }

      id = R.id.btn_edit;
      Button btnEdit = rootView.findViewById(id);
      if (btnEdit == null) {
        break missingId;
      }

      id = R.id.btn_location_focus;
      Button btnLocationFocus = rootView.findViewById(id);
      if (btnLocationFocus == null) {
        break missingId;
      }

      id = R.id.divider2;
      View divider2 = rootView.findViewById(id);
      if (divider2 == null) {
        break missingId;
      }

      ConstraintLayout oneLineWayPoint = (ConstraintLayout) rootView;

      id = R.id.tv_date;
      TextView tvDate = rootView.findViewById(id);
      if (tvDate == null) {
        break missingId;
      }

      id = R.id.tv_title;
      TextView tvTitle = rootView.findViewById(id);
      if (tvTitle == null) {
        break missingId;
      }

      return new OneLineWayPointBinding((ConstraintLayout) rootView, btnDeleteLocation, btnEdit,
          btnLocationFocus, divider2, oneLineWayPoint, tvDate, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}