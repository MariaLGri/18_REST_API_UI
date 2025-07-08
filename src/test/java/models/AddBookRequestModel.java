package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class AddBookRequestModel {
    private String userId;
    private List<IsbnModel> collectionOfIsbns;

}

