package masmar.home.jba.battleship

import spock.lang.Specification


class CoordinateSpec extends Specification {
    def "should create coordinate object"() {
        when:
        def coordinate = new Coordinate(userCoordinate)

        then:
        coordinate.getRowAsNumber() == rowAsNumber
        coordinate.getRow() == row as char
        coordinate.getColumn() == column

        where:
        userCoordinate || row | column | rowAsNumber
        "A1"           || 'A' | 1      | 0
        "J10"          || 'J' | 10     | 9
    }

    def "should Validate input coordinates"() {
        when:
        new Coordinate("K11")

        then:
        IllegalArgumentException ex = thrown()
        ex.message == "You entered the wrong coordinates!"
    }

    def "should return list of Neighbours"() {
        expect:
        new Coordinate(coordinate).getNeighbours() == neighbours

        where:
        coordinate || neighbours
        "A1"       || [new Coordinate("A2"), new Coordinate("B1"), new Coordinate("B2")]
        "I9"       || [new Coordinate("H9"), new Coordinate("H10"), new Coordinate("H8"), new Coordinate("I10"), new Coordinate("J9"),
                       new Coordinate("J10"), new Coordinate("J8"), new Coordinate("I8")]
    }
}
