package routers

import (
	"github.com/kuun/shipwheel/controllers"
	"github.com/astaxie/beego"
)

func init() {
    beego.Router("/", &controllers.MainController{})
}
