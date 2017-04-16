package user

type UserType string

const (
	UserTypeAdmin    UserType = "ADMIN"
	UserTypeAudit    UserType = "AUDIT"
	UserTypeSecerity UserType = "SECERITY"
)

type User struct {
	Id      uint32
	Name    string
	Type    UserType
	IsAdmin bool
}
