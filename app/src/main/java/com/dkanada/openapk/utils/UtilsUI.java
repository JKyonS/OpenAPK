package com.dkanada.openapk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dkanada.openapk.MLManagerApplication;
import com.dkanada.openapk.activities.AboutActivity;
import com.dkanada.openapk.R;
import com.dkanada.openapk.activities.SettingsActivity;
import com.dkanada.openapk.adapters.AppAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class UtilsUI {

  public static int darker(int color, double factor) {
    int a = Color.alpha(color);
    int r = Color.red(color);
    int g = Color.green(color);
    int b = Color.blue(color);
    return Color.argb(a, Math.max((int) (r * factor), 0), Math.max((int) (g * factor), 0), Math.max((int) (b * factor), 0));
  }

  public static Drawer setNavigationDrawer(final Activity activity, final Context context, Toolbar toolbar, final AppAdapter appAdapter, final AppAdapter appSystemAdapter, final AppAdapter appFavoriteAdapter, final AppAdapter appHiddenAdapter, final AppAdapter appDisabledAdapter, final RecyclerView recyclerView) {
    final String loadingLabel = "...";
    int header;
    AppPreferences appPreferences = MLManagerApplication.getAppPreferences();
    String apps, systemApps, favoriteApps, hiddenApps, disabledApps;
    header = R.drawable.header_day;

    if (appAdapter != null) {
      apps = Integer.toString(appAdapter.getItemCount());
    } else {
      apps = loadingLabel;
    }
    if (appSystemAdapter != null) {
      systemApps = Integer.toString(appSystemAdapter.getItemCount());
    } else {
      systemApps = loadingLabel;
    }
    if (appFavoriteAdapter != null) {
      favoriteApps = Integer.toString(appFavoriteAdapter.getItemCount());
    } else {
      favoriteApps = loadingLabel;
    }
    if (appHiddenAdapter != null) {
      hiddenApps = Integer.toString(appHiddenAdapter.getItemCount());
    } else {
      hiddenApps = loadingLabel;
    }
    if (appDisabledAdapter != null) {
      disabledApps = Integer.toString(appDisabledAdapter.getItemCount());
    } else {
      disabledApps = loadingLabel;
    }

    AccountHeader headerResult = new AccountHeaderBuilder()
        .withActivity(activity)
        .withHeaderBackground(header)
        .build();

    // drawer item badges
    Integer badgeColor = ContextCompat.getColor(context, R.color.divider);
    BadgeStyle badgeStyle = new BadgeStyle(badgeColor, badgeColor).withTextColor(Color.GRAY);

    DrawerBuilder drawerBuilder = new DrawerBuilder();
    drawerBuilder.withActivity(activity);
    drawerBuilder.withToolbar(toolbar);
    drawerBuilder.withAccountHeader(headerResult);
    drawerBuilder.withStatusBarColor(UtilsUI.darker(appPreferences.getPrimaryColorPref(), 0.8));

    drawerBuilder.addDrawerItems(
        new PrimaryDrawerItem().withName(context.getResources().getString(R.string.action_apps)).withIcon(GoogleMaterial.Icon.gmd_phone_android).withBadge(apps).withBadgeStyle(badgeStyle).withIdentifier(1),
        new PrimaryDrawerItem().withName(context.getResources().getString(R.string.action_system_apps)).withIcon(GoogleMaterial.Icon.gmd_android).withBadge(systemApps).withBadgeStyle(badgeStyle).withIdentifier(2),
        new PrimaryDrawerItem().withName(context.getResources().getString(R.string.action_favorite_apps)).withIcon(GoogleMaterial.Icon.gmd_star).withBadge(favoriteApps).withBadgeStyle(badgeStyle).withIdentifier(3),
        new PrimaryDrawerItem().withName(context.getResources().getString(R.string.action_hidden_apps)).withIcon(GoogleMaterial.Icon.gmd_visibility_off).withBadge(hiddenApps).withBadgeStyle(badgeStyle).withIdentifier(4),
        new PrimaryDrawerItem().withName(context.getResources().getString(R.string.action_disabled_apps)).withIcon(GoogleMaterial.Icon.gmd_remove_circle_outline).withBadge(disabledApps).withBadgeStyle(badgeStyle).withIdentifier(5),
        new DividerDrawerItem(),
        new PrimaryDrawerItem().withName(context.getResources().getString(R.string.action_settings)).withIcon(GoogleMaterial.Icon.gmd_settings).withSelectable(false).withIdentifier(6),
        new PrimaryDrawerItem().withName(context.getResources().getString(R.string.action_about)).withIcon(GoogleMaterial.Icon.gmd_info).withSelectable(false).withIdentifier(7));

    drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
      @Override
      public boolean onItemClick(View view, int position, IDrawerItem iDrawerItem) {
        switch (iDrawerItem.getIdentifier()) {
          case 1:
            recyclerView.setAdapter(appAdapter);
            setToolbarTitle(activity, context.getResources().getString(R.string.action_apps));
            break;
          case 2:
            recyclerView.setAdapter(appSystemAdapter);
            setToolbarTitle(activity, context.getResources().getString(R.string.action_system_apps));
            break;
          case 3:
            recyclerView.setAdapter(appFavoriteAdapter);
            setToolbarTitle(activity, context.getResources().getString(R.string.action_favorite_apps));
            break;
          case 4:
            recyclerView.setAdapter(appHiddenAdapter);
            setToolbarTitle(activity, context.getResources().getString(R.string.action_hidden_apps));
            break;
          case 5:
            recyclerView.setAdapter(appDisabledAdapter);
            setToolbarTitle(activity, context.getResources().getString(R.string.action_disabled_apps));
            break;
          case 6:
            context.startActivity(new Intent(context, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            break;
          case 7:
            context.startActivity(new Intent(context, AboutActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            break;
          default:
            break;
        }
        return false;
      }
    });
    return drawerBuilder.build();
  }

  public static void setToolbarTitle(Activity activity, String title) {
    Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
    toolbar.setTitle(title);
  }

  public static void updateAppFavoriteIcon(Context context, MenuItem menuItem, Boolean isFavorite) {
    if (isFavorite) {
      menuItem.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_star));
    } else {
      menuItem.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_star_border));
    }
  }

  public static void updateAppHiddenIcon(Context context, FloatingActionButton fabHide, Boolean isHidden) {
    if (isHidden) {
      fabHide.setTitle(context.getResources().getString(R.string.action_unhide));
      fabHide.setIcon(R.drawable.ic_visibility);
    } else {
      fabHide.setTitle(context.getResources().getString(R.string.action_hide));
      fabHide.setIcon(R.drawable.ic_visibility_off);
    }
  }

  public static void updateAppDisabledIcon(Context context, FloatingActionButton fabDisable, Boolean isDisabled) {
    if (isDisabled) {
      fabDisable.setTitle(context.getResources().getString(R.string.action_enable));
      fabDisable.setIcon(R.drawable.ic_remove_circle_outline);
    } else {
      fabDisable.setTitle(context.getResources().getString(R.string.action_disable));
      fabDisable.setIcon(R.drawable.ic_remove_circle);
    }
  }
}
