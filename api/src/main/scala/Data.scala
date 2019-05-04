object Role extends Enumeration {
  val ADMIN, REGULAR, VIEWER = Value
}


case class User(
  id: String,
  username: String,
  active: Boolean,
  role: Role.Value,
  email: String)


class UserRepo {
  import UserRepo._

  def getUser(id: String): Option[User] = users.find(c ⇒ c.id == id)

  def getUsers(): List[User] = users

  def authenticate(email: String, password: String): Option[User] = null

  def updateUser(id: String, username: Option[String], role: Option[Role.Value], 
    email: Option[String], active: Option[Boolean] ) : Option[User] = null //do stuff

  def createUser(username: String, role: Role.Value,
    email: String, active: Option[Boolean]  ) : Option[User] = null //do stuff TODO: send user email to creat pass

  
}

object UserRepo {
  val users = List(
    User(
      id = "1",
      username = "admin",
      active = true,
      role = Role.ADMIN,
      email = "admin@admin.admin"),
    User(
      id = "2",
      username = "averageDude",
      active = true,
      role = Role.REGULAR,
      email = "dude@admin.admin"),
    User(
      id = "3",
      username = "lessDude",
      active = true,
      role = Role.VIEWER,
      email = "less@admin.admin"),

  )
}
