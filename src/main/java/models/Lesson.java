package models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    private String time;
    private ArrayList<String> objects;
    private ArrayList<String> types;
    private ArrayList<String> teachers;
    private ArrayList<String> address;
    private ArrayList<String> groups;
}
