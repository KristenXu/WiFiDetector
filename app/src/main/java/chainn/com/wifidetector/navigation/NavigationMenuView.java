package chainn.com.wifidetector.navigation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import chainn.com.wifidetector.R;

public class NavigationMenuView {
    private final NavigationView navigationView;
    private NavigationMenu currentNavigationMenu;

    public NavigationMenuView(@NonNull Activity activity, @NonNull NavigationMenu currentNavigationMenu) {
        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);

        populateNavigationMenu();
        setCurrentNavigationMenu(currentNavigationMenu);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) activity);
    }

    private void populateNavigationMenu() {
        Menu menu = navigationView.getMenu();
        for (NavigationGroup navigationGroup : NavigationGroup.values()) {
            for (NavigationMenu navigationMenu : navigationGroup.navigationMenu()) {
                MenuItem menuItem = menu.add(navigationGroup.ordinal(), navigationMenu.ordinal(), navigationMenu.ordinal(), navigationMenu.getTitle());
                menuItem.setIcon(navigationMenu.getIcon());
            }
        }
    }

    public MenuItem getCurrentMenuItem() {
        return navigationView.getMenu().getItem(getCurrentNavigationMenu().ordinal());
    }

    public NavigationMenu getCurrentNavigationMenu() {
        return currentNavigationMenu;
    }

    public void setCurrentNavigationMenu(@NonNull NavigationMenu navigationMenu) {
        this.currentNavigationMenu = navigationMenu;
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setCheckable(navigationMenu.ordinal() == i);
            item.setChecked(navigationMenu.ordinal() == i);
        }
    }

    NavigationView getNavigationView() {
        return navigationView;
    }

}
