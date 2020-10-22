package masmar.home.jba.battleship

import spock.lang.Specification


class ShipSpec extends Specification {
    def "should fully setup the ship"() {
        given:
        def ship = new Ship(Type.AIRCRAFT_CARRIER)

        when:
        ship.setCoordinates(new Coordinate("A1"), new Coordinate("E1"))

        then:
        ship.getName() == "Aircraft Carrier"
        ship.getLength() == 5
        ship.getCoordinates() == [
                new Coordinate("A1"),
                new Coordinate("B1"),
                new Coordinate("C1"),
                new Coordinate("D1"),
                new Coordinate("E1")
        ]
    }

    def "should notify when the provided coordinates give incorrect length"() {
        given:
        def ship = new Ship(Type.AIRCRAFT_CARRIER)

        when:
        ship.setCoordinates(new Coordinate("A1"), new Coordinate("D1"))

        then:
        IllegalArgumentException ex = thrown()
        ex.message == "Wrong length of the Aircraft Carrier!"
    }
}
