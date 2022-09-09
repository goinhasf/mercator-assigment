import org.scalatest.funspec.AnyFunSpec

class CheckoutSystemTest extends AnyFunSpec {

  import Store._
  import CheckoutSystem._

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

    describe("checkout") {
      List(
        (List.empty[ShopItem], Money(0)),
        (List(Orange), Orange.price),
        (List(Apple), Apple.price),
        (List(Apple, Apple), Apple.price + Apple.price),
        (List(Orange, Orange), Orange.price + Orange.price),
        (List(Orange, Apple), Orange.price + Apple.price),
      ).foreach { example =>
        val (cart, expected) = example
        it(s"should return the correct cost, items: $cart, expected: ${expected.toString}") {
          assertResult(expected) {
            CheckoutSystem().checkout(cart)
          }
        }
      }
      List(
        (List(), List.empty[ShopItem], Money(0)),
        (List(twoForOneApples), List.fill(2)(Apple), Apple.price),
        (List(twoForOneApples), List.fill(4)(Apple), Apple.price * 2),
        (List(threeForTwoOranges), List.fill(3)(Orange), Orange.price * 2),
        (List(threeForTwoOranges), List.fill(6)(Orange), Orange.price * 4),
        (
          List(twoForOneApples, threeForTwoOranges),
          List.fill(3)(Orange) ++ List.fill(2)(Apple),
          Orange.price * 2 + Apple.price
        ),
        (
          List(twoForOneApples, threeForTwoOranges),
          List.fill(4)(Orange) ++ List.fill(3)(Apple),
          Orange.price * 3 + Apple.price * 2
        ),
      ).foreach { example =>
        val (discounts, cart, expected) = example
        it(s"should apply discounts, discounts: $discounts, items: $cart, expected: ${expected.toString}") {

          assertResult(expected) {
            CheckoutSystem(discounts).checkout(cart)
          }
        }
      }
    }

    describe("default") {
      List(
        (List.fill(1)(Apple), Apple.price),
        (List.fill(2)(Apple), Apple.price),
        (List.fill(3)(Apple), Apple.price * 2),
        (List.fill(4)(Apple), Apple.price * 2),
        (List.fill(5)(Apple), Apple.price * 3),
      ).foreach { example =>
        val (input, expected) = example
        it(s"should correctly apply the two for one apples discount, input: $input, expected: $expected") {
          assertResult(expected) {
            CheckoutSystem.default.checkout(input)
          }
        }
      }


      List(
        (List.fill(1)(Orange), Orange.price),
        (List.fill(2)(Orange), Orange.price * 2),
        (List.fill(3)(Orange), Orange.price * 2),
        (List.fill(4)(Orange), Orange.price * 3),
        (List.fill(6)(Orange), Orange.price * 4),
      ).foreach { example =>
        val (input, expected) = example
        it(s"should correctly apply the three for two oranges discount, input: $input, expected: $expected") {
          assertResult(expected) {
            CheckoutSystem.default.checkout(input)
          }
        }
      }
    }
  }
}
