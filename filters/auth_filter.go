package filters

import (
	"github.com/astaxie/beego/context"
	"github.com/astaxie/beego"
	"github.com/kuun/shipwheel/models"
	"github.com/astaxie/beego/logs"
)

var FilterAuth = func(ctx *context.Context) {
	if ctx.Request.RequestURI != "/api/login" {
		_, ok := ctx.Input.Session("uid").(*models.User)
		logs.Debug("session status: %v", ok)
		if !ok && ctx.Request.RequestURI != "/login.html" {
			ctx.Redirect(302, "/login.html")
		}
	}
}

func init()  {
	beego.InsertFilter("/*",beego.BeforeRouter, FilterAuth)
}

