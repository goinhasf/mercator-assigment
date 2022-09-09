import scala.annotation.targetName

object Store {
  case class Money(cents: Int) {
    @targetName("add")
    def +(other: Money): Money = Money(cents + other.cents)

    override def toString: String = {
      val centsString = cents.toString

      centsString.length match {
        case 1 => s"£0.0$centsString"
        case 2 => s"£0.$centsString"
        case _ =>
          val (integer, decimal) = centsString.splitAt(centsString.length - 2)
          s"£$integer.$decimal"
        
      }
    }
  }

  sealed trait ShopItem {
    val name: String
    val price: Money
  }

  case object Apple extends ShopItem {
    override val name: String = "Apple"
    override val price: Money = Money(60)
  }

  case object Orange extends ShopItem {
    override val name: String = "Orange"
    override val price: Money = Money(25)
  }
}
