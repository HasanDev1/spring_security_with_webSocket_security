package uz.agro.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputMessage extends MessageModel{
    private String time;

    public OutputMessage(String from, String message, String format) {
    }
}
