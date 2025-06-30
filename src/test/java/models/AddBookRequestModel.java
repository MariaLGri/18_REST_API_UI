package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequestModel {
    private String userId;
    private BookIsbnModel[] collectionOfIsbns;
}

