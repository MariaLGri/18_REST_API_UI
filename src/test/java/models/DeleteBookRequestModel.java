package models;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DeleteBookRequestModel {
    String userId, isbn;
}

