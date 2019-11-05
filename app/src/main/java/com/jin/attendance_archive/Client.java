package com.jin.attendance_archive;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/**
 * Created by JIN on 1/19/2019.
 */

public class Client extends AsyncTask<Void, Void, Void> {

    SocketAddress[] socketAddress;
    Socket c_socket;
    PrintWriter outputWriter;
    BufferedReader inputReader;

    final private String IP_CHURCH_EXTERNAL = "112.223.117.138";
    final private String IP_HOME_EXTERNAL = "116.125.77.153";
    final private String IP_HOME_INTERNAL = "192.168.123.104";
    final private int PORT = 8888;

    AddressSharedPreference sp = new AddressSharedPreference();

    Activity activity;
    String TYPE, ID, KEY, STATUS_TYPE, CHECK_TYPE, INPUT, ADD_NAME, ADD_GROUP;
    String[] PEOPLE, ListValues, STATUS, LOGS, FpEtcATTENDANCE, FpSearchATTENDANCE, FPA_names, FPA_contents, CONSIDERATION;
    String[][] ATTENDANCE, FpATTENDANCE, CANDIDATES, Believer, WordMovement;
    int[] FPA_checks;
    ArrayList FPA_etc, FPA_search, FPB, FPWM;
    boolean DONE, ALIVE, DUPLICATION;
    int num;

    // TYPE: isAlive, getLogs
    Client(Activity act, String type) {
        activity = act;
        TYPE = type;
    }

    // TYPE: LogIn
    Client(Activity act, String type, String id, String[] people) {
        activity = act;
        TYPE = type;
        ID = id;
        PEOPLE = people;
    }

    // TYPE: AutoLogIn, LogOut, getList, getStatus, getAttendance, getFpAttendance, getSearchResult, getFpFruits, setStatus, getFpDuplication, getDuplication
    Client(Activity act, String type, String obj) {
        activity = act;
        TYPE = type;
        switch (type) {
            case "AutoLogIn":
                KEY = obj;
                break;
            case "LogOut":
                KEY = obj;
                break;
            case "getList":
                STATUS_TYPE = obj;
                break;
            case "getStatus":
                STATUS_TYPE = obj;
                break;
            case "getAttendance":
                CHECK_TYPE = obj;
                break;
            case "getFpAttendance":
                CHECK_TYPE = obj;
                break;
            case "getSearchResult":
                INPUT = obj;
                break;
            case "getFpFruits":
                CHECK_TYPE = obj;
                break;
            case "setStatus":
                CHECK_TYPE = obj;
                break;
            case "getFpDuplication":
                INPUT = obj;
                break;
            case "getDuplication":
                INPUT = obj;
                break;
        }
    }

    // TYPE: getSearchResultForFruits
    Client(Activity act, String type, String check_type, String obj) {
        activity = act;
        TYPE = type;
        CHECK_TYPE = check_type;
        INPUT = obj;
    }

    // TYPE: setAttendance
    Client(Activity act, String type, String key, String check_type, String[][] attendance) {
        activity = act;
        TYPE = type;
        KEY = key;
        CHECK_TYPE = check_type;
        ATTENDANCE = attendance;
    }

    // TYPE: setFpAttendance
    Client(Activity act, String type, String key, String check_type, String[] names, String[] contents, int[] checks, ArrayList etc, ArrayList search) {
        activity = act;
        TYPE = type;
        KEY = key;
        CHECK_TYPE = check_type;
        FPA_names = names;
        FPA_contents = contents;
        FPA_checks = checks;
        FPA_etc = etc;
        FPA_search = search;
    }

    // TYPE: setFpFruits
    Client(Activity act, String type, String key, String check_type, String[][] bo, String[][] wmo) {
        activity = act;
        TYPE = type;
        KEY = key;
        CHECK_TYPE = check_type;
        Believer = bo;
        WordMovement = wmo;
    }

    // TYPE: getConsideration
    Client(Activity act, String type, String check_type, ArrayList b, ArrayList wm) {
        activity = act;
        TYPE = type;
        CHECK_TYPE = check_type;
        FPB = b;
        FPWM = wm;
    }

    // TYPE: addFpAttendance, addAttendance
    Client(Activity act, String type, String key, String check_type, String name, String group) {
        activity = act;
        TYPE = type;
        KEY = key;
        CHECK_TYPE = check_type;
        ADD_NAME = name;
        ADD_GROUP = group;
    }

    public boolean isDone() {
        return DONE;
    }

    public boolean isAlive() {
        return ALIVE;
    }

    public String[] getListValues() {
        return ListValues;
    }

    public String[] getSTATUS() {
        return STATUS;
    }

    public String[] getLogs() {
        return LOGS;
    }

    public String[][] getAttendance() {
        return ATTENDANCE;
    }

    public String[][] getFpAttendance() {
        return FpATTENDANCE;
    }

    public String[] getFpEtcAttendance() {
        return FpEtcATTENDANCE;
    }

    public String[] getFpSearchATTENDANCE() {
        return FpSearchATTENDANCE;
    }

    public String[][] getCANDIDATES() {
        return CANDIDATES;
    }

    public String[][] getBeliever() {
        return Believer;
    }

    public String[][] getWordMovement() {
        return WordMovement;
    }

    public String[] getCONSIDERATION() {
        return CONSIDERATION;
    }

    public boolean getDUPLICATION() {
        return DUPLICATION;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DONE = false;
        ALIVE = false;

        socketAddress = new SocketAddress[3];
        socketAddress[0] = new InetSocketAddress(IP_CHURCH_EXTERNAL, PORT);
        socketAddress[1] = new InetSocketAddress(IP_HOME_INTERNAL, PORT);
        socketAddress[2] = new InetSocketAddress(IP_HOME_EXTERNAL, PORT);

        int count = 0;
        int index = sp.getAddress(activity);
        int c = index;
        while (count < socketAddress.length) {
            try {
                c_socket = new Socket();
                c_socket.connect(socketAddress[c], 2000);
                ALIVE = true;
                sp.setAddress(activity, c);
                break;
            } catch (IOException e) {
                if (count == 0) {
                    c = 0;
                } else {
                    c++;
                }
                if (c == index) {
                    c++;
                }
                count++;
            }
        }

        if (!ALIVE) {
            DONE = true;
        } else {
            try {
                outputWriter = new PrintWriter(c_socket.getOutputStream());
                inputReader = new BufferedReader(new InputStreamReader(c_socket.getInputStream(), "EUC-KR"));

                outputWriter.println(TYPE);
                outputWriter.flush();

                switch (TYPE) {
                    case "isAlive":
                        // do nothing
                        break;

                    case "LogIn":
                        outputWriter.println(ID);
                        outputWriter.flush();
                        num = Integer.valueOf(inputReader.readLine());
                        for (int i = 0; i < num; i++) {
                            PEOPLE[i] = inputReader.readLine();
                        }
                        break;

                    case "AutoLogIn":
                        outputWriter.println(KEY);
                        outputWriter.flush();
                        inputReader.readLine();
                        break;

                    case "LogOut":
                        outputWriter.println(KEY);
                        outputWriter.flush();
                        inputReader.readLine();
                        break;

                    case "getList":
                        outputWriter.println(STATUS_TYPE);
                        outputWriter.flush();
                        num = Integer.valueOf(inputReader.readLine());
                        ListValues = new String[num];
                        STATUS = new String[num];
                        for (int i = 0; i < num; i++) {
                            ListValues[i] = inputReader.readLine();
                            STATUS[i] = inputReader.readLine();
                        }
                        break;

                    case "getStatus":
                        outputWriter.println(STATUS_TYPE);
                        outputWriter.flush();
                        num = Integer.valueOf(inputReader.readLine());
                        STATUS = new String[num];
                        for (int i = 0; i < num; i++) {
                            STATUS[i] = inputReader.readLine();
                        }
                        break;

                    case "getLogs":
                        num = Integer.valueOf(inputReader.readLine());
                        LOGS = new String[num];
                        for (int i = 0; i < num; i++) {
                            LOGS[i] = inputReader.readLine();
                        }
                        break;

                    case "getAttendance":
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        num = Integer.valueOf(inputReader.readLine());
                        ATTENDANCE = new String[3][num];
                        for (int i = 0; i < num; i++) {
                            ATTENDANCE[0][i] = inputReader.readLine();
                            ATTENDANCE[1][i] = inputReader.readLine();
                            ATTENDANCE[2][i] = inputReader.readLine();
                        }
                        break;

                    case "setAttendance":
                        outputWriter.println(KEY);
                        outputWriter.flush();
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        outputWriter.println(ATTENDANCE[0].length);
                        outputWriter.flush();
                        for (int i = 0; i < ATTENDANCE[0].length; i++) {
                            outputWriter.println(ATTENDANCE[0][i]);
                            outputWriter.flush();
                            outputWriter.println(ATTENDANCE[1][i]);
                            outputWriter.flush();
                            outputWriter.println(ATTENDANCE[2][i]);
                            outputWriter.flush();
                        }
                        inputReader.readLine();
                        break;

                    case "getFpAttendance":
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        num = Integer.valueOf(inputReader.readLine());
                        FpATTENDANCE = new String[7][num];
                        for (int i = 0; i < num; i++) {
                            FpATTENDANCE[0][i] = inputReader.readLine();
                            FpATTENDANCE[1][i] = inputReader.readLine();
                            FpATTENDANCE[2][i] = inputReader.readLine();
                            FpATTENDANCE[3][i] = inputReader.readLine();
                            FpATTENDANCE[4][i] = inputReader.readLine();
                            FpATTENDANCE[5][i] = inputReader.readLine();
                            FpATTENDANCE[6][i] = inputReader.readLine();
                        }
                        num = Integer.valueOf(inputReader.readLine());
                        FpEtcATTENDANCE = new String[num];
                        for (int i = 0; i < num; i++) {
                            FpEtcATTENDANCE[i] = inputReader.readLine();
                        }
                        num = Integer.valueOf(inputReader.readLine());
                        FpSearchATTENDANCE = new String[num];
                        for (int i = 0; i < num; i++) {
                            FpSearchATTENDANCE[i] = inputReader.readLine();
                        }
                        break;

                    case "getSearchResult":
                        outputWriter.println(INPUT);
                        outputWriter.flush();
                        num = Integer.valueOf(inputReader.readLine());
                        CANDIDATES = new String[4][num];
                        for (int i = 0; i < num; i++) {
                            CANDIDATES[0][i] = inputReader.readLine();
                            CANDIDATES[1][i] = inputReader.readLine();
                            CANDIDATES[2][i] = inputReader.readLine();
                            CANDIDATES[3][i] = inputReader.readLine();
                        }
                        break;

                    case "setFpAttendance":
                        outputWriter.println(KEY);
                        outputWriter.flush();
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        outputWriter.println(FPA_names.length);
                        outputWriter.flush();
                        for (int i = 0; i < FPA_names.length; i++) {
                            outputWriter.println(FPA_names[i]);
                            outputWriter.flush();
                            outputWriter.println(FPA_contents[i]);
                            outputWriter.flush();
                            outputWriter.println(FPA_checks[i]);
                            outputWriter.flush();
                        }
                        outputWriter.println(FPA_etc.size());
                        outputWriter.flush();
                        for (int i = 0; i < FPA_etc.size(); i++) {
                            outputWriter.println(FPA_etc.get(i));
                            outputWriter.flush();
                        }
                        outputWriter.println(FPA_search.size());
                        outputWriter.flush();
                        for (int i = 0; i < FPA_search.size(); i++) {
                            outputWriter.println(FPA_search.get(i));
                            outputWriter.flush();
                        }
                        inputReader.readLine();
                        break;


                    case "getFpFruits":
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        num = Integer.valueOf(inputReader.readLine());
                        Believer = new String[6][num];
                        for (int i = 0; i < num; i++) {
                            Believer[0][i] = inputReader.readLine();
                            Believer[1][i] = inputReader.readLine();
                            Believer[2][i] = inputReader.readLine();
                            Believer[3][i] = inputReader.readLine();
                            Believer[4][i] = inputReader.readLine();
                            Believer[5][i] = inputReader.readLine();
                        }
                        num = Integer.valueOf(inputReader.readLine());
                        WordMovement = new String[4][num];
                        for (int i = 0; i < num; i++) {
                            WordMovement[0][i] = inputReader.readLine();
                            WordMovement[1][i] = inputReader.readLine();
                            WordMovement[2][i] = inputReader.readLine();
                            WordMovement[3][i] = inputReader.readLine();
                        }
                        break;

                    case "getSearchResultForFruits":
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        outputWriter.println(INPUT);
                        outputWriter.flush();
                        num = Integer.valueOf(inputReader.readLine());
                        CANDIDATES = new String[2][num];
                        for (int i = 0; i < num; i++) {
                            CANDIDATES[0][i] = inputReader.readLine();
                            CANDIDATES[1][i] = inputReader.readLine();
                        }
                        break;

                    case "setFpFruits":
                        outputWriter.println(KEY);
                        outputWriter.flush();
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        outputWriter.println(Believer[0].length);
                        outputWriter.flush();
                        for (int i = 0; i < Believer[0].length; i++) {
                            outputWriter.println(Believer[0][i]);
                            outputWriter.flush();
                            outputWriter.println(Believer[1][i]);
                            outputWriter.flush();
                            outputWriter.println(Believer[2][i]);
                            outputWriter.flush();
                            outputWriter.println(Believer[3][i]);
                            outputWriter.flush();
                            outputWriter.println(Believer[4][i]);
                            outputWriter.flush();
                            outputWriter.println(Believer[5][i]);
                            outputWriter.flush();
                        }
                        outputWriter.println(WordMovement[0].length);
                        outputWriter.flush();
                        for (int i = 0; i < WordMovement[0].length; i++) {
                            outputWriter.println(WordMovement[0][i]);
                            outputWriter.flush();
                            outputWriter.println(WordMovement[1][i]);
                            outputWriter.flush();
                            outputWriter.println(WordMovement[2][i]);
                            outputWriter.flush();
                            outputWriter.println(WordMovement[3][i]);
                            outputWriter.flush();
                        }
                        inputReader.readLine();
                        break;

                    case "setStatus":
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        inputReader.readLine();
                        break;

                    case "getConsideration":
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        outputWriter.println(FPB.size());
                        outputWriter.flush();
                        for (int i = 0; i < FPB.size(); i++) {
                            outputWriter.println(FPB.get(i));
                            outputWriter.flush();
                        }
                        outputWriter.println(FPWM.size());
                        outputWriter.flush();
                        for (int i = 0; i < FPWM.size(); i++) {
                            outputWriter.println(FPWM.get(i));
                            outputWriter.flush();
                        }
                        CONSIDERATION = new String[3];
                        CONSIDERATION[0] = inputReader.readLine();
                        CONSIDERATION[1] = inputReader.readLine();
                        CONSIDERATION[2] = inputReader.readLine();
                        break;

                    case "getFpDuplication":
                        outputWriter.println(INPUT);
                        outputWriter.flush();
                        String dup_fp = inputReader.readLine();
                        if (dup_fp.equals("TRUE")) {
                            DUPLICATION = true;
                        } else {
                            DUPLICATION = false;
                        }
                        break;

                    case "addFpAttendance":
                        outputWriter.println(KEY);
                        outputWriter.flush();
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        outputWriter.println(ADD_NAME);
                        outputWriter.flush();
                        outputWriter.println(ADD_GROUP);
                        outputWriter.flush();
                        inputReader.readLine();
                        break;

                    case "getDuplication":
                        outputWriter.println(INPUT);
                        outputWriter.flush();
                        String dup = inputReader.readLine();
                        if (dup.equals("TRUE")) {
                            DUPLICATION = true;
                        } else {
                            DUPLICATION = false;
                        }
                        break;

                    case "addAttendance":
                        outputWriter.println(KEY);
                        outputWriter.flush();
                        outputWriter.println(CHECK_TYPE);
                        outputWriter.flush();
                        outputWriter.println(ADD_NAME);
                        outputWriter.flush();
                        outputWriter.println(ADD_GROUP);
                        outputWriter.flush();
                        inputReader.readLine();
                        break;
                }

                c_socket.close();
                outputWriter.close();
                inputReader.close();
                DONE = true;

            } catch (IOException e) {
                DONE = true;
                ALIVE = false;
            }
        }

        return null;
    }
}
