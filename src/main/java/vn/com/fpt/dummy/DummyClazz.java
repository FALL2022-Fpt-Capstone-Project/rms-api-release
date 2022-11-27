package vn.com.fpt.dummy;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class DummyClazz {
    private static final List<DummyObject> DATA_FROM_DB = List.of(
            new DummyObject(1, "A1B1", 1, true, false),
            new DummyObject(2, "A1B-1", 1, true, false),
            new DummyObject(3, "A105", 1, true, false),
            new DummyObject(4, "A106", 1, true, false),
            new DummyObject(5, "A201", 2, true, false),
            new DummyObject(6, "A203", 2, true, false),
            new DummyObject(7, "A205", 2, true, false),
            new DummyObject(8, "A206", 2, true, false),
            new DummyObject(9, "A401", 4, true, false),
            new DummyObject(10, "A402", 4, true, false),
            new DummyObject(11, "A403", 4, true, false),
            new DummyObject(12, "A305", 4, true, false)
    );

    public static List<DummyObject> getRoomFromFloor(int floor) {
        return DATA_FROM_DB.stream().filter(room -> room.getFloor() == floor).collect(toList());
    }

    public static boolean checkNoobRoomName(String roomName) {
        Pattern pattern1 = Pattern.compile("^[0-9]{3}$", Pattern.CASE_INSENSITIVE);
        Pattern pattern2 = Pattern.compile("^[0-9]{4}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(roomName);
        Matcher matcher2 = pattern2.matcher(roomName);

        if (matcher1.matches()) {
            return true;
        } else if (matcher2.matches()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
//        int totalRoomFloor = 3;
        int totalRoomToGen = 4;
        String nameConvention = "A";

        List<Integer> listFloorInGroup = List.of(1, 2, 4);
        Map<Integer, DummyObject[]> temp1 = new HashMap<>();

        for (Integer i : listFloorInGroup) {
            DummyObject[] temp2 = null;
            for (DummyObject dos : getRoomFromFloor(i)) {
                if (Objects.isNull(temp2)) temp2 = new DummyObject[99];

                String roomNumber = dos.getRoom().split("(?<=\\D)(?=\\d)")[1]; //A201 -> 201
                if (checkNoobRoomName(roomNumber)) {
                    var temp3 = roomNumber.split("");
                    int index = Integer.parseInt(temp3[roomNumber.length() - 2] + temp3[roomNumber.length() - 1]);
                    if (!Objects.isNull(temp2)) {
                        if (temp2[index] != null) {
                            int var1 = 1;
                            while (true) {
                                if (Objects.isNull(temp2[var1])) {
                                    temp2[var1] = dos;
                                    break;
                                }
                            }
                        } else {
                            temp2[index] = dos;
                        }
                    }
                }
            }

            temp1.put(i, temp2);
        }
        List<DummyObject> gen = new ArrayList<>(Collections.emptyList());
        for (Integer i : listFloorInGroup) {
            var room = temp1.get(i);
            if (temp1.isEmpty()) {
                for (int y = 1; y <= totalRoomToGen; y++) {
                    gen.add(new DummyObject(null, nameConvention + i + String.format("%02", y), i, false, false));
                }
            } else {
                boolean flag = false;
                if (room[1] == null) {
                    int var2 = 1;
                    for (int x = 2; x <= totalRoomToGen; x++) {
                        if (room[x] == null) {
                            var2++;
                        }
                    }
                    if (totalRoomToGen - var2 == 0 || (double) var2 / totalRoomToGen <= 0.5) {
                        // check duplicate
                        for (int y1 = 1; y1 <= totalRoomToGen; y1++) {
                            gen.add(new DummyObject(null, nameConvention + i + String.format("%02", y1), i, false, false));
                        }
                        flag = true;
                    }
                }
                if (room[1] != null || flag) {
                    int var3 = 2;
                    while (!Objects.isNull(room[var3])) {
                        var3++;
                    }
                    int var2 = 1;
                    for (int x = var3; x <= totalRoomToGen; x++) {
                        if (room[x] == null) {
                            var2++;
                        }
                    }
                    if (totalRoomToGen - var2 == 0 || (double) var2 / totalRoomToGen <= 0.5) {
                        // check duplicate
                        for (int y1 = 1; y1 <= totalRoomToGen; y1++) {
                            gen.add(new DummyObject(null, nameConvention + i + String.format("%02", y1), i, false, false));
                        }
                    } else {

                    }
                }
            }
        }
    }
}
