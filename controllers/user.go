package controllers

import (
	"github.com/astaxie/beego"
	"github.com/astaxie/beego/orm"
	"github.com/kuun/shipwheel/models"
	"github.com/astaxie/beego/logs"
)

var userLog = logs.GetBeeLogger()

type UserController struct {
	beego.Controller
}

func (c *UserController) Get() {
	o := orm.NewOrm()
	usr := &models.User{Id: 1}
	if err := o.Read(usr); err != nil {
		userLog.Error("failed to read user: %#v", err)
	}
	usr.Password = ""
	c.Data["json"] = usr
	c.ServeJSON()
}
