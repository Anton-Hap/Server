package utils;


import constants.Data;
import models.*;
import pages.SchedulesPage;
import pages.StudentsPage;

import java.util.ArrayList;

public abstract class ScheduleUtils {

    public static LocalData getLocalData() {
        return (LocalData) JSONUtils.fromFileToObject(Data.LOCALDATA_PATH, new LocalData());
    }

    public static Week getWeek() {
        return (Week) JSONUtils.fromFileToObject(Data.SCHEDULE_PATH, new Week());
    }

    public static GlobalData getGlobalData() {
        return (GlobalData) JSONUtils.fromFileToObject(Data.GLOBALDATA_PATH, new GlobalData());
    }

    public static Day getDaySchedule(int numberDay) {
        Week week = (Week) JSONUtils.fromFileToObject(Data.SCHEDULE_PATH, new Week());

        return week.getDays().get(numberDay);
    }

    public static void updateSchedule(String faculty, String speciality, String group, String week) {
        LocalData localData = new LocalData(faculty, speciality, group, week);

        try {
            downloadSchedule(localData);
            JSONUtils.fromObjectToFile(Data.LOCALDATA_PATH, localData);
        } catch (Exception e) {
            BrowserUtils.quit();
            throw new RuntimeException();
        }
    }

    public static void updateSchedule(LocalData localData) {
        try {
            downloadSchedule(localData);
            JSONUtils.fromObjectToFile(Data.LOCALDATA_PATH, localData);
        } catch (Exception e) {
            BrowserUtils.quit();
            throw new RuntimeException();
        }
    }

    private static void downloadGlobalData() {
        GlobalData globalData = new GlobalData();

        StudentsPage studentsPage = new StudentsPage();
        globalData.setFaculty(studentsPage.getAllFaculty());
        globalData.setSpeciality(studentsPage.getAllSpeciality());
        globalData.setGroup(studentsPage.getAllGroup());

        JSONUtils.fromObjectToFile(Data.LOCALDATA_PATH, globalData);
    }

    private static void downloadSchedule(LocalData localdata) throws Exception {
        BrowserUtils.run();
        BrowserUtils.goTo(Data.URL);

        SchedulesPage schedulesPage = new SchedulesPage();
        schedulesPage.goToStudentsPage();

        StudentsPage studentsPage = new StudentsPage();

//        downloadGlobalData();

        studentsPage.selectFaculty(localdata.getFaculty());
        studentsPage.selectSpeciality(localdata.getSpeciality());
        studentsPage.selectGroup(localdata.getGroup());
        studentsPage.selectWeek(localdata.getWeek());
        studentsPage.showSchedule();

        Week week = new Week();
        for (int i = 0; i < 5; i++) {
            try {
                Day day = new Day();

                if (i == 0) {
                    day.setDate(studentsPage.getValue("1"));
                    day.setName(studentsPage.getNameDay("1"));

                    for (int j = 2; j < 10; j++) {

                        String value = studentsPage.getValue(String.valueOf(j));

                        ArrayList<String> objects = new ArrayList<>();
                        ArrayList<String> types = new ArrayList<>();
                        ArrayList<String> teachers = new ArrayList<>();
                        ArrayList<String> address = new ArrayList<>();
                        ArrayList<String> groups = new ArrayList<>();

                        while (!value.equals("")) {
                            objects.add(value.substring(0, value.indexOf(" ")));
                            value = value.substring(value.indexOf("-") + 1, value.length());

                            types.add(value.substring(1, value.indexOf("\n")));
                            value = value.substring(value.indexOf("\n") + 1, value.length());

                            if (value.indexOf("\n") == -1) {
                                teachers.add(value.substring(0, value.length()));
                                value = "";
                            } else {
                                teachers.add(value.substring(0, value.indexOf("\n")));
                                value = value.substring(value.indexOf("\n") + 1, value.length());
                            }

                            if (value.indexOf(":") == -1) {
                                groups.add("");

                                if (value.indexOf("\n") == -1) {
                                    if (value.substring(0, value.length()) == objects.get(objects.size() - 1)) {
                                        address.add("");
                                        value = "";
                                    } else {
                                        if (!value.equals("")) {
                                            address.add(value.substring(0, value.length()));
                                            value = "";
                                        } else {
                                            address.add(value);
                                        }
                                    }
                                } else {
                                    if (value.substring(0, value.indexOf("\n")) == objects.get(objects.size() - 1)) {
                                        address.add("");
                                        value = "";
                                    } else {
                                        address.add(value.substring(0, value.indexOf("\n")));
                                        value = value.substring(value.indexOf("\n") + 1, value.length());
                                    }
                                }
                            } else {
                                if (value.indexOf("\n") == -1) {
                                    address.add(value.substring(0, value.indexOf(":") - 3));
                                    value = value.substring(value.indexOf(":") + 2, value.length());

                                    groups.add(value);
                                    value = "";
                                } else {
                                    address.add(value.substring(0, value.indexOf(":") - 3));
                                    value = value.substring(value.indexOf(":") + 2, value.length());

                                    groups.add(value.substring(0, value.indexOf("\n")));
                                    value = value.substring(value.indexOf("\n") + 1, value.length());
                                }
                            }
                        }

                        day.getLessons().add(new Lesson(studentsPage.getTimeLesson(String.valueOf(j)), objects, types, teachers, address, groups));
                    }
                } else {
                    day.setDate(studentsPage.getValue(i + "1"));
                    day.setName(studentsPage.getNameDay(i + "1"));

                    for (int j = 2; j < 10; j++) {

                        String value = studentsPage.getValue(String.valueOf(i) + j);

                        ArrayList<String> objects = new ArrayList<>();
                        ArrayList<String> types = new ArrayList<>();
                        ArrayList<String> teachers = new ArrayList<>();
                        ArrayList<String> address = new ArrayList<>();
                        ArrayList<String> groups = new ArrayList<>();

                        while (!value.equals("")) {
                            objects.add(value.substring(0, value.indexOf(" ")));
                            value = value.substring(value.indexOf("-") + 1, value.length());

                            types.add(value.substring(1, value.indexOf("\n")));
                            value = value.substring(value.indexOf("\n") + 1, value.length());

                            if (value.indexOf("\n") == -1) {
                                teachers.add(value.substring(0, value.length()));
                                value = "";
                            } else {
                                teachers.add(value.substring(0, value.indexOf("\n")));
                                value = value.substring(value.indexOf("\n") + 1, value.length());
                            }

                            if (value.indexOf(":") == -1) {
                                groups.add("");

                                if (value.indexOf("\n") == -1) {
                                    if (value.substring(0, value.length()) == objects.get(objects.size() - 1)) {
                                        address.add("");
                                        value = "";
                                    } else {
                                        if (!value.equals("")) {
                                            address.add(value.substring(0, value.length()));
                                            value = "";
                                        } else {
                                            address.add(value);
                                        }
                                    }
                                } else {
                                    if (value.substring(0, value.indexOf("\n")) == objects.get(objects.size() - 1)) {
                                        address.add("");
                                        value = "";
                                    } else {
                                        address.add(value.substring(0, value.indexOf("\n")));
                                        value = value.substring(value.indexOf("\n") + 1, value.length());
                                    }
                                }
                            } else {
                                if (value.indexOf("\n") == -1) {
                                    address.add(value.substring(0, value.indexOf(":") - 3));
                                    value = value.substring(value.indexOf(":") + 2, value.length());

                                    groups.add(value);
                                    value = "";
                                } else {
                                    address.add(value.substring(0, value.indexOf(":") - 3));
                                    value = value.substring(value.indexOf(":") + 2, value.length());

                                    groups.add(value.substring(0, value.indexOf("\n")));
                                    value = value.substring(value.indexOf("\n") + 1, value.length());
                                }
                            }
                        }

                        day.getLessons().add(new Lesson(studentsPage.getTimeLesson(String.valueOf(i) + j), objects, types, teachers, address, groups));
                    }
                }

                week.getDays().add(day);
            } catch (Exception e) {
                BrowserUtils.quit();

                if (i == 0) {
                    throw new RuntimeException();
                }
            }
        }

        BrowserUtils.quit();

        JSONUtils.fromObjectToFile(Data.SCHEDULE_PATH, week);
    }
}
