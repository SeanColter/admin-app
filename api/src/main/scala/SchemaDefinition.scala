import sangria.execution.deferred.{Fetcher, HasId}
import sangria.schema._

import scala.concurrent.Future

/**
 * Defines a GraphQL schema for the current project
 */
object SchemaDefinition {
  /**
    * Resolves the lists of characters. These resolutions are batched and
    * cached for the duration of a query.
    */
  val users = Fetcher.caching(
    (ctx: UserRepo, ids: Seq[String]) ⇒
      Future.successful(ids.flatMap(id ⇒ ctx.getUser(id))))(HasId(_.id))

  val RoleEnum = EnumType(
    "Role",
    Some("The User Roles"),
    List(
      EnumValue("ADMIN",
        value = Role.ADMIN,
        description = Some("Full Permission")),
      EnumValue("REGULAR",
        value = Role.REGULAR,
        description = Some("Regular Permission")),
      EnumValue("VIEWER",
        value = Role.VIEWER,
        description = Some("Limited Permission"))))

  val User =
    ObjectType[Unit, User](
      "User",
      "A user for theis system",
      fields[Unit, User](
        Field("id", StringType,
          Some("The unique id of the user."),
          resolve = _.value.id),
        Field("username", StringType,
          Some("The username of the user."),
          resolve = _.value.username),
        Field("active", BooleanType,
          Some("Determines if active user."),
          resolve = _.value.active),
        Field("role", RoleEnum,
          Some("User Role."),
          resolve = _.value.role),
        Field("email", StringType,
          Some("The users email"),
          resolve = _.value.email)
      ))

  val id = Argument("id", StringType, description = "user id")

  val password = Argument("password", StringType, description = "password")

  val usernameOpt = Argument("username", OptionInputType(StringType), description = "optional new username")
  val roleOpt = Argument("role", OptionInputType(RoleEnum), description = "optional new user role")
  val activeOpt = Argument("active", OptionInputType(BooleanType), description = "optional set user active/inactive")
  val emailOpt = Argument("email", OptionInputType(StringType), description = "optional new user email")

  val usernameReq = Argument("username", StringType, description = "required new username")
  val roleReq = Argument("role", RoleEnum, description = "required new user role")
  val emailReq = Argument("email", StringType, description = "required new user email")

  val Query = ObjectType(
    "Query", fields[UserRepo, Unit](
      Field("user", OptionType(User),
        arguments = id :: Nil,
        resolve = ctx ⇒ ctx.ctx.getUser(ctx.arg(id))),
      Field("users", ListType(User),
        resolve = ctx ⇒ ctx.ctx.getUsers()),
      Field("authenticate", OptionType(User),
        arguments = emailReq :: password :: Nil,
        resolve = ctx ⇒ ctx.ctx.authenticate(ctx.arg(emailReq) , ctx.arg(password))
    )))


  val Mutation = ObjectType(
    "Mutation", fields[UserRepo, Unit](
      Field("updateUser", OptionType(User),
        arguments = id :: usernameOpt :: roleOpt :: emailOpt :: activeOpt ::  Nil,
        resolve = ctx => ctx.ctx.updateUser(ctx.arg(id), ctx.arg(usernameOpt), ctx.arg(roleOpt), ctx.arg(emailOpt), ctx.arg(activeOpt))
      ),
      Field("createUser", OptionType(User),
        arguments = usernameReq :: roleReq :: emailReq :: activeOpt :: Nil,
        resolve = ctx => ctx.ctx.createUser(ctx.arg(usernameReq), ctx.arg(roleReq), ctx.arg(emailReq), ctx.arg(activeOpt))
      )
    )
  )


  val AdminSchema = Schema(Query, Some(Mutation))
}
