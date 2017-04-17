package models

import "github.com/astaxie/beego/orm"

type User struct {
	Id       uint32 `json:"id"`
	Name     string `json:"name"`
	Password string `json:"password,omitempty"`
	IsAdmin  bool   `json:"is_admin"`
}

func (u *User) Auth() bool {
	o := orm.NewOrm()
	return o.QueryTable(u).Filter("name", u.Name).Filter("password", u.Password).Exist()
}

func init() {
	orm.RegisterModelWithPrefix("ship_", new(User))
}
