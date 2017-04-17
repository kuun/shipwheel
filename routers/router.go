package routers

import (
	"github.com/astaxie/beego"
	"github.com/kuun/shipwheel/controllers"
)

func init() {
	beego.Router("/", &controllers.MainController{})
	//APIS
	ns :=
		beego.NewNamespace("/api",
			beego.NSRouter("/user", &controllers.UserController{}),
			beego.NSRouter("/login", &controllers.LoginController{}),
		)
	beego.AddNamespace(ns)

}
