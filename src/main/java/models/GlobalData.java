package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalData {

    private ArrayList<String> faculty;
    private Speciality speciality;
    private Group group;
}
