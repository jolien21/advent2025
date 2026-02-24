package be.jolien.advent2025.services;

import be.jolien.advent2025.models.Connection;
import be.jolien.advent2025.models.Position3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectionService {

    /**
     * Berekent alle mogelijke unieke verbindingen tussen de gegeven posities
     * en sorteert deze direct van kortste naar langste afstand.
     */
    public List<Connection> getSortedConnections(List<Position3D> positions) {
        List<Connection> connections = new ArrayList<>();

        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                long distSq = positions.get(i).getStraigthLineDistance(positions.get(j));
                connections.add(new Connection(i, j, distSq));
            }
        }

        Collections.sort(connections);
        return connections;
    }

    /**
     * Voegt verbindingen toe totdat alle punten in één circuit zitten.
     * Geeft het product van de X-coördinaten van de allerlaatste benodigde verbinding terug.
     */
    public long findXProductOfFinalCircuitClosingConnection(List<Position3D> positions, List<Connection> sortedConnections) {
        int totalPoints = positions.size();
        CircuitService circuitManager = new CircuitService(totalPoints);
        int circuitsLeft = totalPoints;

        for (Connection conn : sortedConnections) {
            if (circuitManager.find(conn.indexA) != circuitManager.find(conn.indexB)) {

                circuitManager.union(conn.indexA, conn.indexB);
                circuitsLeft--;

                if (circuitsLeft == 1) {
                    Position3D p1 = positions.get(conn.indexA);
                    Position3D p2 = positions.get(conn.indexB);

                    return (long) p1.getX() * p2.getX();
                }
            }
        }

        throw new IllegalStateException("Er kon geen allesomvattend circuit gevormd worden met de gegeven verbindingen.");
    }
}