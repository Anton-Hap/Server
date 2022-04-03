package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Group {

    private ArrayList<String> group;
    private ArrayList<String> speciality;
}
