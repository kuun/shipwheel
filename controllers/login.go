package controllers

import (
	"encoding/json"

	"github.com/astaxie/beego"
	"github.com/astaxie/beego/logs"
	"github.com/kuun/shipwheel/models"
)

var loginLog = logs.GetBeeLogger()

type LoginController struct {
	beego.Controller
}

func (c *LoginController) Post() {
	usr := &models.User{}
	json.Unmarshal(c.Ctx.Input.RequestBody, &usr)
	m := make(map[string]bool)
	authed := usr.Auth()
	if authed {
		c.SetSession("uid", usr)
	}
	m["success"] = authed
	c.Data["json"] = m
	c.ServeJSON()
}