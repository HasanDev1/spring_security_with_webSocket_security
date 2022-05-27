package uz.agro.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageModel {
    private String message;
    private String from;
    private String to;

    public MessageModel(String message, String from) {
        this.message = message;
        this.from = from;
    }

    public MessageModel(String message) {
        this.message = message;
    }
}
