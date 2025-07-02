package models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddBookRequestModel {
    private String userId;
    private List<IsbnModel> collectionOfIsbns;

    // Конструктор
    public AddBookRequestModel(String userId, List<IsbnModel> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }
}

