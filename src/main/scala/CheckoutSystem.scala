import Store.*

case class CheckoutSystem(currentOffers: List[Offer.OfferApplier] = List.empty) {
  def checkout: List[ShopItem] => Money = calculateCost compose Offer.applyOffers(currentOffers)

  private def calculateCost(items: List[ShopItem]): Money = items
    .map(_.price)
    .fold(Money(0))(_ + _)
}

object CheckoutSystem {

  import Offer.OfferApplier

  val twoForOneApples: OfferApplier = Offer(Apple)(2, 1)
  val threeForTwoOranges: OfferApplier = Offer(Orange)(3, 2)
  val defaultOffers = List(twoForOneApples, threeForTwoOranges)
  val default: CheckoutSystem = CheckoutSystem(defaultOffers)
}
