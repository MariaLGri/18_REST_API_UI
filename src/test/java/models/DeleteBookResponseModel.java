package models;

import lombok.Data;

@Data
public class DeleteBookResponseModel {
    String userId, isbn, message;
}

