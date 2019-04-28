package Library.Admin;

import Library.controller.BaseCallBack;
import Library.controller.Controller;

public class AdminCallbacks extends BaseCallBack {
    private AdminController controller;

    public AdminCallbacks(AdminController adminController) {
        this.controller = adminController;
    }
}
