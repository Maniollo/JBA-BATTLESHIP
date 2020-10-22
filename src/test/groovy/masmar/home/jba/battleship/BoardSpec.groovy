package masmar.home.jba.battleship

import spock.lang.Specification

class BoardSpec extends Specification {
    def "should update board after add a ship"() {
        when: "board object is created"
        def board = Board.create()

        then: "displayed board is empty"
        board.display() ==
                "  1 2 3 4 5 6 7 8 9 10\n" +
                "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~"

        when: "add new ship vertically"
        def carrier = new Ship(Type.AIRCRAFT_CARRIER)
        carrier.setCoordinates(new Coordinate("C2"), new Coordinate("G2"))
        board.addShip(carrier)

        then: "board after add Aircraft Carrier"
        board.display() ==
                "  1 2 3 4 5 6 7 8 9 10\n" +
                "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~"

        when: "add new ship horizontally"
        def submarine = new Ship(Type.SUBMARINE)
        submarine.setCoordinates(new Coordinate("J10"), new Coordinate("J8"))
        board.addShip(submarine)

        then: "board after add Submarine"
        board.display() ==
                "  1 2 3 4 5 6 7 8 9 10\n" +
                "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ ~ O O O"

        when: "new ship is going to be added to close"
        def cruiser = new Ship(Type.CRUISER)
        cruiser.setCoordinates(new Coordinate("C3"), new Coordinate("C5"))
        board.addShip(cruiser)

        then: "relevant exception is thrown"
        IllegalStateException ex = thrown()
        ex.message == "You placed it too close to another one."

        when:
        cruiser.setCoordinates(new Coordinate("C4"), new Coordinate("C6"))
        board.addShip(cruiser)

        then: "board after add Cruiser"
        board.display() ==
                "  1 2 3 4 5 6 7 8 9 10\n" +
                "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ O ~ O O O ~ ~ ~ ~\n" +
                "D ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ O ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ ~ O O O"
    }

    def "should return the shot result"() {
        given:
        def board = Board.create()
        def ship = new Ship(Type.AIRCRAFT_CARRIER)
        ship.setCoordinates(new Coordinate("A1"), new Coordinate("A5"))
        board.addShip(ship)

        when:
        def shotResult = board.shot(new Coordinate("A6"))

        then:
        shotResult == ShotResult.MISSED
        and:
        board.display() ==
                "  1 2 3 4 5 6 7 8 9 10\n" +
                "A O O O O O M ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~"

        when:
        def secondShotResult = board.shot(new Coordinate("A3"))

        then:
        secondShotResult == ShotResult.HIT
        and:
        board.display() ==
                "  1 2 3 4 5 6 7 8 9 10\n" +
                "A O O X O O M ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~"
    }
}

