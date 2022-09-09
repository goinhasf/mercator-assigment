import org.scalatest.funspec.AnyFunSpec

class CheckoutSystemTest extends AnyFunSpec {

  import Store._

  describe("Money") {
    val examples = List(
      (Money(0), "£0.00"),
      (Money(10), "£0.10"),
      (Money(100), "£1.00"),
      (Money(1234), "£12.34"),
      (Money(12345), "£123.45"),
    )

    examples.foreach { example =>
      val (input, expected) = example
      it(s"should convert money in pence to pounds, input: $input, expected: $expected") {
        assertResult(expected) {
          input.toString
        }
      }
    }

  }

  describe("CheckoutSystem") {
    val examples = List(
      (List.empty[ShopItem], Money(0)),
      (List(Orange), Orange.price),
      (List(Apple), Apple.price),
      (List(Orange, Apple), Orange.price + Apple.price),
    )

    describe("checkout") {
      examples.foreach { example =>
        val (cart, expected) = example
        it(s"should return the correct cost, items: $cart, expected: ${expected.toString}") {
          assertResult(expected) {
            CheckoutSystem.checkout(cart)
          }
        }
      }
    }


  }
}
