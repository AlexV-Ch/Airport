package airport;

import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;
//import javafx.util.Duration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static long findCountAircraftWithModelAirbus(Airport airport, String model) {
        //TODO Метод должен вернуть количество самолетов указанной модели.
        // подходят те самолеты, у которых name начинается со строки model
        long kolvo = airport.getAllAircrafts().stream()
                .filter(str -> str.getModel().startsWith(model))
                .count();
        return kolvo;
    }

    public static Map<String, Integer> findMapCountParkedAircraftByTerminalName(Airport airport) {
        //TODO Метод должен вернуть словарь с количеством припаркованных самолетов в каждом терминале.
        Stream<Terminal> terminalStream = airport.getTerminals().stream();
        Map<String, Integer> map = terminalStream
                .collect(Collectors.toMap(Terminal::getName, kolvo -> kolvo.getParkedAircrafts().size()));
        return map;
    }

    public static List<Flight> findFlightsLeavingInTheNextHours(Airport airport, int hours) {
        //TODO Метод должен вернуть список отправляющихся рейсов в ближайшее количество часов.
        Instant currentDate = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
        Instant endTime = currentDate.plusSeconds(hours*3600L);

        List<Flight> flightList = airport.getTerminals().stream()
                .map(Terminal::getFlights).flatMap(Collection::stream)
                .filter(  tp -> tp.getType() == Flight.Type.valueOf("DEPARTURE"))
                .filter(dt -> dt.getDate().isAfter(currentDate))
                .filter(dt -> dt.getDate().isBefore(endTime))
                .toList();

        return flightList;
    }

    public static Optional<Flight> findFirstFlightArriveToTerminal(Airport airport, String terminalName) {
        //TODO Найти ближайший прилет в указанный терминал.
        List<Terminal> terminalStream = airport.getTerminals().stream()
                .filter(tl -> tl.getName().equals(terminalName))
                .toList();
        List<Flight> flightStream = terminalStream.stream()
                .map(Terminal::getFlights).flatMap(Collection::stream)
                .filter(tp -> tp.getType() == Flight.Type.valueOf("ARRIVAL"))
////                .sorted(Comparator.comparing())
                .toList()
                ;

        return Optional.empty();
    }
}