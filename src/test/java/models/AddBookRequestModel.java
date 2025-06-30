package models;

import lombok.AllArgsConstructor;
import lombok.Data;

    @AllArgsConstructor
    @Data
    public class AddBookRequestModel {
        private String userId;
        BookIsbnModel[] collectionOfIsbns;
    }

