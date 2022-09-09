import Store.ShopItem

object Offer {

  type OfferApplier = List[ShopItem] => List[ShopItem]

  /**
   * Applies a x for y offer to an item given a list of items.
   *
   * @param item  The item to apply the offer to
   * @param _take Represents x in the offer
   * @param _for  Represents y in the offer
   * @param items A list of shop items to apply the offer to
   * @tparam T The type of the ShopItem
   * @return Returns a list that is equal or smaller than the items list provided
   */
  def apply[T <: ShopItem](item: T)
                          (_take: Int, _for: Int): OfferApplier = items => {
    val ratio = _take.toDouble / _for.toDouble

    val targetItems = items.filter(_ == item)
    val restItems = items.filter(_ != item)

    val itemsToTake = Math.ceil(targetItems.length / ratio)

    targetItems.take(itemsToTake.toInt) ++ restItems
  }


  /**
   * Returns a new list of items with the combination offers applied.
   *
   * @param currentOffers a list of OfferAppliers
   * @param items the items to apply the offers to
   * @return a new list of items with the combination offers applied
   */
  def applyOffers(currentOffers: List[OfferApplier])(items: List[ShopItem]): List[ShopItem] =
    currentOffers.foldRight(items) { (offerApplier, discountedItems) => offerApplier(discountedItems) }

}