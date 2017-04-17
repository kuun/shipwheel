package main

import (
	"fmt"

	"github.com/astaxie/beego"
	"github.com/astaxie/beego/logs"
	"github.com/astaxie/beego/orm"
	_ "github.com/go-sql-driver/mysql"
	_ "github.com/kuun/shipwheel/routers"
	_ "github.com/kuun/shipwheel/filters"
	"github.com/pkg/errors"
)

var log *logs.BeeLogger

func init()  {
	if err := initLog(); err != nil {
		panic(fmt.Sprintf("error: %#v", err))
	}
	log = logs.GetBeeLogger()
}

func main() {
	if err := initDB(); err != nil {
		log.Error("error: %#v", err)
		goto EXIT
	}
	beego.Run()
EXIT:
	fmt.Println("faield to start shipwheel!")
}

func initLog() error {
	logFile := beego.AppConfig.String("logfile")
	if err := logs.SetLogger(logs.AdapterFile, fmt.Sprintf(`{"filename":"%s"}`, logFile)); err != nil {
		return errors.Wrapf(err, "failed to init log")
	}
	return nil
}

func initDB() error {
	dbType := beego.AppConfig.String("dbtype")
	dbUrl := beego.AppConfig.String("dburl")
	if err := orm.RegisterDataBase("default", dbType, dbUrl); err != nil {
		return errors.Wrapf(err , "failed to register database, url: %s", dbUrl)
	}

	log.Debug("register database")
	return nil
}

