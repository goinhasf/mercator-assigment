object CheckoutSystem {
  import Store._
  def checkout(items: List[ShopItem]): Money = items.map(_.price).fold(Money(0))(_ + _)
}
